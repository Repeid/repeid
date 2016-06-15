package org.repeid.models.cache.infinispan;

import org.jboss.logging.Logger;
import org.repeid.migration.MigrationModel;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidTransaction;
import org.repeid.models.cache.CacheOrganizationProvider;
import org.repeid.models.cache.infinispan.entities.CachedDocument;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;
import org.repeid.models.cache.infinispan.entities.DocumentListQuery;
import org.repeid.models.cache.infinispan.entities.OrganizationListQuery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrganizationCacheSession implements CacheOrganizationProvider {

	protected static final Logger logger = Logger.getLogger(OrganizationCacheSession.class);
	public static final String DOCUMENTS_QUERY_SUFFIX = ".documents";
	protected OrganizationCacheManager cache;
	protected RepeidSession session;
	protected OrganizationProvider delegate;
	protected boolean transactionActive;
	protected boolean setRollbackOnly;

	protected Map<String, OrganizationAdapter> managedOrganizations = new HashMap<>();
	protected Map<String, DocumentAdapter> managedDocuments = new HashMap<>();
	protected Map<String, LegalPersonAdapter> managedLegalPersons = new HashMap<>();
	protected Map<String, NaturalPersonAdapter> managedNaturalPersons = new HashMap<>();
	protected Set<String> listInvalidations = new HashSet<>();
	protected Set<String> invalidations = new HashSet<>();

	protected boolean clearAll;
	protected final long startupRevision;

	public OrganizationCacheSession(OrganizationCacheManager cache, RepeidSession session) {
		this.cache = cache;
		this.session = session;
		this.startupRevision = cache.getCurrentCounter();
		session.getTransaction().enlistPrepare(getPrepareTransaction());
		session.getTransaction().enlistAfterCompletion(getAfterTransaction());
	}

	@Override
	public MigrationModel getMigrationModel() {
		return getDelegate().getMigrationModel();
	}

	@Override
	public OrganizationProvider getDelegate() {
		if (!transactionActive)
			throw new IllegalStateException("Cannot access delegate without a transaction");
		if (delegate != null)
			return delegate;
		delegate = session.getProvider(OrganizationProvider.class);
		return delegate;
	}

	private String getDocumentsCacheKey(String container) {
		return container + DOCUMENTS_QUERY_SUFFIX;
	}

	private String getDocumentByNameCacheKey(String container, String name) {
		return container + "." + name + DOCUMENTS_QUERY_SUFFIX;
	}

	@Override
	public void registerOrganizationInvalidation(String id) {
		invalidateOrganization(id);
		cache.organizationInvalidation(id, invalidations);
	}

	private void invalidateOrganization(String id) {
		invalidations.add(id);
		OrganizationAdapter adapter = managedOrganizations.get(id);
		if (adapter != null)
			adapter.invalidate();
	}

	@Override
	public OrganizationModel createOrganization(String name) {
		OrganizationModel organization = getDelegate().createOrganization(name);
		registerOrganizationInvalidation(organization.getId());
		return organization;
	}

	@Override
	public OrganizationModel createOrganization(String id, String name) {
		OrganizationModel organization = getDelegate().createOrganization(id, name);
		registerOrganizationInvalidation(organization.getId());
		return organization;
	}

	@Override
	public OrganizationModel getOrganization(String id) {
		CachedOrganization cached = cache.get(id, CachedOrganization.class);
		if (cached != null) {
			logger.tracev("by id cache hit: {0}", cached.getName());
		}
		if (cached == null) {
			Long loaded = cache.getCurrentRevision(id);
			OrganizationModel model = getDelegate().getOrganization(id);
			if (model == null)
				return null;
			if (invalidations.contains(id))
				return model;
			cached = new CachedOrganization(loaded, model);
			cache.addRevisioned(cached, startupRevision);
		} else if (invalidations.contains(id)) {
			return getDelegate().getOrganization(id);
		} else if (managedOrganizations.containsKey(id)) {
			return managedOrganizations.get(id);
		}
		OrganizationAdapter adapter = new OrganizationAdapter(cached, this);
		managedOrganizations.put(id, adapter);
		return adapter;
	}

	@Override
	public OrganizationModel getOrganizationByName(String name) {
		String cacheKey = getOrganizationByNameCacheKey(name);
		OrganizationListQuery query = cache.get(cacheKey, OrganizationListQuery.class);
		if (query != null) {
			logger.tracev("organization by name cache hit: {0}", name);
		}
		if (query == null) {
			Long loaded = cache.getCurrentRevision(cacheKey);
			OrganizationModel model = getDelegate().getOrganizationByName(name);
			if (model == null)
				return null;
			if (invalidations.contains(model.getId()))
				return model;
			query = new OrganizationListQuery(loaded, cacheKey, model.getId());
			cache.addRevisioned(query, startupRevision);
			return model;
		} else if (invalidations.contains(cacheKey)) {
			return getDelegate().getOrganizationByName(name);
		} else {
			String organizationId = query.getOrganizations().iterator().next();
			if (invalidations.contains(organizationId)) {
				return getDelegate().getOrganizationByName(name);
			}
			return getOrganization(organizationId);
		}
	}

	public String getOrganizationByNameCacheKey(String name) {
		return "organization.query.by.name." + name;
	}

	@Override
	public DocumentModel addOrganizationDocument(OrganizationModel organization, String abbreviation) {
		return addOrganizationDocument(organization, null, abbreviation);
	}

	@Override
	public DocumentModel addOrganizationDocument(OrganizationModel organization, String id, String abbreviation) {
		invalidations.add(getDocumentsCacheKey(organization.getId()));
		// this is needed so that a new document that hasn't been committed
		// isn't cached in a query
		listInvalidations.add(organization.getId());
		DocumentModel document = getDelegate().addOrganizationDocument(organization, abbreviation);
		invalidations.add(document.getId());
		return document;
	}

	@Override
	public DocumentModel getOrganizationDocument(OrganizationModel organization, String abbreviation) {
		String cacheKey = getDocumentByNameCacheKey(organization.getId(), abbreviation);
		boolean queryDB = invalidations.contains(cacheKey) || listInvalidations.contains(organization.getId());
		if (queryDB) {
			return getDelegate().getOrganizationDocument(organization, abbreviation);
		}

		DocumentListQuery query = cache.get(cacheKey, DocumentListQuery.class);
		if (query != null) {
			logger.tracev("getOrganizationDocument cache hit: {0}.{1}", organization.getName(), abbreviation);
		}

		if (query == null) {
			Long loaded = cache.getCurrentRevision(cacheKey);
			DocumentModel model = getDelegate().getOrganizationDocument(organization, abbreviation);
			if (model == null)
				return null;
			query = new DocumentListQuery(loaded, cacheKey, organization, model.getId());
			logger.tracev("adding organization document cache miss: client {0} key {1}", organization.getName(),
					cacheKey);
			cache.addRevisioned(query, startupRevision);
			return model;
		}
		DocumentModel document = getDocumentById(query.getDocuments().iterator().next(), organization);
		if (document == null) {
			invalidations.add(cacheKey);
			return getDelegate().getOrganizationDocument(organization, abbreviation);
		}
		return document;
	}

	@Override
	public DocumentModel getDocumentById(String id, OrganizationModel organization) {
		CachedDocument cached = cache.get(id, CachedDocument.class);
		if (cached != null && !cached.getOrganization().equals(organization.getId())) {
			cached = null;
		}

		if (cached == null) {
			Long loaded = cache.getCurrentRevision(id);
			DocumentModel model = getDelegate().getDocumentById(id, organization);
			if (model == null)
				return null;
			if (invalidations.contains(id))
				return model;
			// if (model.isClientRole()) {
			// cached = new CachedClientRole(loaded, model.getContainerId(),
			// model, realm);
			// } else {
			cached = new CachedOrganizationDocument(loaded, model, organization);
			// }
			cache.addRevisioned(cached, startupRevision);

		} else if (invalidations.contains(id)) {
			return getDelegate().getDocumentById(id, organization);
		} else if (managedDocuments.containsKey(id)) {
			return managedDocuments.get(id);
		}
		DocumentAdapter adapter = new DocumentAdapter(cached, this, organization);
		managedDocuments.put(id, adapter);
		return adapter;
	}

	@Override
	public Set<DocumentModel> getOrganizationDocuments(OrganizationModel organization) {
		String cacheKey = getDocumentsCacheKey(organization.getId());
		boolean queryDB = invalidations.contains(cacheKey) || listInvalidations.contains(organization.getId());
		if (queryDB) {
			return getDelegate().getOrganizationDocuments(organization);
		}

		DocumentListQuery query = cache.get(cacheKey, DocumentListQuery.class);
		if (query != null) {
			logger.tracev("getOrganizationDocuments cache hit: {0}", organization.getName());
		}

		if (query == null) {
			Long loaded = cache.getCurrentRevision(cacheKey);
			Set<DocumentModel> model = getDelegate().getOrganizationDocuments(organization);
			if (model == null)
				return null;
			Set<String> ids = new HashSet<>();
			for (DocumentModel document : model)
				ids.add(document.getId());
			query = new DocumentListQuery(loaded, cacheKey, organization, ids);
			logger.tracev("adding organization documents cache miss: organization {0} key {1}", organization.getName(),
					cacheKey);
			cache.addRevisioned(query, startupRevision);
			return model;
		}
		Set<DocumentModel> list = new HashSet<>();
		for (String id : query.getDocuments()) {
			DocumentModel document = session.organizations().getDocumentById(id, organization);
			if (document == null) {
				invalidations.add(cacheKey);
				return getDelegate().getOrganizationDocuments(organization);
			}
			list.add(document);
		}
		return list;
	}

	@Override
	public boolean removeDocument(OrganizationModel organization, DocumentModel document) {
		invalidations.add(getDocumentsCacheKey(document.getId()));
		invalidations.add(getDocumentByNameCacheKey(document.getId(), document.getName()));
		listInvalidations.add(document.getId());
		registerDocumentInvalidation(document.getId());
		return getDelegate().removeDocument(organization, document);
	}

	@Override
	public List<OrganizationModel> getOrganizations() {
		// Retrieve organizations from backend
		List<OrganizationModel> backendOrganizations = getDelegate().getOrganizations();

		// Return cache delegates to ensure cache invalidated during write
		// operations
		List<OrganizationModel> cachedOrganizations = new LinkedList<>();
		for (OrganizationModel organization : backendOrganizations) {
			OrganizationModel cached = getOrganization(organization.getId());
			cachedOrganizations.add(cached);
		}
		return cachedOrganizations;
	}

	@Override
	public boolean removeOrganization(String id) {
		OrganizationModel organization = getOrganization(id);
		if (organization == null)
			return false;

		// invalidations.add(getRealmClientsQueryCacheKey(id));
		invalidations.add(getOrganizationByNameCacheKey(organization.getName()));
		cache.invalidateObject(id);
		cache.organizationRemoval(id, invalidations);
		return getDelegate().removeOrganization(id);
	}

	@Override
	public void close() {
		if (delegate != null)
			delegate.close();
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public void registerDocumentInvalidation(String id) {
		invalidateDocument(id);
		cache.documentInvalidation(id, invalidations);
	}

	private void invalidateDocument(String id) {
		invalidations.add(id);
		DocumentAdapter adapter = managedDocuments.get(id);
		if (adapter != null)
			adapter.invalidate();
	}

	@Override
	public void registerNaturalPersonInvalidation(String id) {
		invalidateNaturalPerson(id);
		cache.naturalPersonInvalidation(id, invalidations);
	}

	private void invalidateNaturalPerson(String id) {
		invalidations.add(id);
		NaturalPersonAdapter adapter = managedNaturalPersons.get(id);
		if (adapter != null)
			adapter.invalidate();
	}

	@Override
	public void registerLegalPersonInvalidation(String id) {
		invalidateLegalPerson(id);
		cache.legalPersonInvalidation(id, invalidations);
	}

	private void invalidateLegalPerson(String id) {
		invalidations.add(id);
		LegalPersonAdapter adapter = managedLegalPersons.get(id);
		if (adapter != null)
			adapter.invalidate();
	}

	protected void runInvalidations() {
		for (String id : invalidations) {
			cache.invalidateObject(id);
		}
	}

	private RepeidTransaction getPrepareTransaction() {
		return new RepeidTransaction() {
			@Override
			public void begin() {
				transactionActive = true;
			}

			@Override
			public void commit() {
				/*
				 * THIS WAS CAUSING DEADLOCK IN A CLUSTER if (delegate == null)
				 * return; List<String> locks = new LinkedList<>();
				 * locks.addAll(invalidations); Collections.sort(locks); // lock
				 * ordering cache.getRevisions().startBatch(); if
				 * (!locks.isEmpty())
				 * cache.getRevisions().getAdvancedCache().lock(locks);
				 */
			}

			@Override
			public void rollback() {
				setRollbackOnly = true;
				transactionActive = false;
			}

			@Override
			public void setRollbackOnly() {
				setRollbackOnly = true;
			}

			@Override
			public boolean getRollbackOnly() {
				return setRollbackOnly;
			}

			@Override
			public boolean isActive() {
				return transactionActive;
			}
		};
	}

	private RepeidTransaction getAfterTransaction() {
		return new RepeidTransaction() {
			@Override
			public void begin() {
				transactionActive = true;
			}

			@Override
			public void commit() {
				try {
					if (delegate == null)
						return;
					if (clearAll) {
						cache.clear();
					}
					runInvalidations();
					transactionActive = false;
				} finally {
					cache.endRevisionBatch();
				}
			}

			@Override
			public void rollback() {
				try {
					setRollbackOnly = true;
					runInvalidations();
					transactionActive = false;
				} finally {
					cache.endRevisionBatch();
				}
			}

			@Override
			public void setRollbackOnly() {
				setRollbackOnly = true;
			}

			@Override
			public boolean getRollbackOnly() {
				return setRollbackOnly;
			}

			@Override
			public boolean isActive() {
				return transactionActive;
			}
		};
	}

}

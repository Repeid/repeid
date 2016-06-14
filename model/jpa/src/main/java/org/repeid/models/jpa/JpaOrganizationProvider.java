package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.migration.MigrationModel;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.DocumentEntity;
import org.repeid.models.jpa.entities.OrganizationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class JpaOrganizationProvider implements OrganizationProvider {

	protected static final Logger logger = Logger.getLogger(JpaOrganizationProvider.class);
	private final RepeidSession session;
	protected EntityManager em;

	public JpaOrganizationProvider(RepeidSession session, EntityManager em) {
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public MigrationModel getMigrationModel() {
		return new MigrationModelAdapter(em);
	}

	@Override
	public OrganizationModel createOrganization(String name) {
		return createOrganization(null, name);
	}

	@Override
	public OrganizationModel createOrganization(String id, String name) {
		OrganizationEntity organization = new OrganizationEntity();
		organization.setName(name);
		organization.setId(id);
		em.persist(organization);
		em.flush();
		final OrganizationModel adapter = new OrganizationAdapter(session, em, organization);
		session.getRepeidSessionFactory().publish(new OrganizationModel.OrganizationCreationEvent() {
			@Override
			public OrganizationModel getCreatedOrganization() {
				return adapter;
			}
		});
		return adapter;
	}

	@Override
	public OrganizationModel getOrganization(String id) {
		OrganizationEntity organization = em.find(OrganizationEntity.class, id);
		if (organization == null)
			return null;
		OrganizationAdapter adapter = new OrganizationAdapter(session, em, organization);
		return adapter;
	}

	@Override
	public OrganizationModel getOrganizationByName(String name) {
		TypedQuery<String> query = em.createNamedQuery("getOrganizationIdByName", String.class);
		query.setParameter("name", name);
		List<String> entities = query.getResultList();
		if (entities.size() == 0)
			return null;
		if (entities.size() > 1)
			throw new IllegalStateException("Should not be more than one organization with same name");
		String id = query.getResultList().get(0);

		return session.organizations().getOrganization(id);
	}

	@Override
	public boolean removeOrganization(String id) {
		return false;
	}

	@Override
	public List<OrganizationModel> getOrganizations() {
		TypedQuery<String> query = em.createNamedQuery("getAllOrganizationIds", String.class);
		List<String> entities = query.getResultList();
		List<OrganizationModel> organizations = new ArrayList<>();
		for (String id : entities) {
			OrganizationModel organization = session.organizations().getOrganization(id);
			if (organization != null)
				organizations.add(organization);
		}
		return organizations;
	}

	@Override
	public DocumentModel addOrganizationDocument(OrganizationModel organization, String abbreviation) {
		return addOrganizationDocument(organization, null, abbreviation);
	}

	@Override
	public DocumentModel addOrganizationDocument(OrganizationModel organization, String id, String abbreviation) {
		DocumentEntity entity = new DocumentEntity();
		entity.setId(id);
		entity.setAbbreviation(abbreviation);
		OrganizationEntity ref = em.getReference(OrganizationEntity.class, organization.getId());
		entity.setOrganization(ref);	
		em.persist(entity);
		em.flush();
		DocumentAdapter adapter = new DocumentAdapter(session, organization, em, entity);
		return adapter;
	}

	@Override
	public DocumentModel getOrganizationDocument(OrganizationModel organization, String abbreviation) {
		TypedQuery<String> query = em.createNamedQuery("getOrganizationDocumentIdByAbbreviation", String.class);
		query.setParameter("abbreviation", abbreviation);
		query.setParameter("organization", organization.getId());
		List<String> documents = query.getResultList();
		if (documents.size() == 0)
			return null;
		return session.organizations().getDocumentById(documents.get(0), organization);
	}

	@Override
	public DocumentModel getDocumentById(String id, OrganizationModel organization) {
		DocumentEntity entity = em.find(DocumentEntity.class, id);
		if (entity == null)
			return null;
		if (!organization.getId().equals(entity.getOrganization().getId()))
			return null;
		DocumentAdapter adapter = new DocumentAdapter(session, organization, em, entity);
		return adapter;
	}

	@Override
	public Set<DocumentModel> getOrganizationDocuments(OrganizationModel organization) {
		TypedQuery<String> query = em.createNamedQuery("getOrganizationDocumentIds", String.class);
		query.setParameter("organization", organization.getId());
		List<String> documents = query.getResultList();

		if (documents.isEmpty())
			return Collections.EMPTY_SET;
		Set<DocumentModel> list = new HashSet<>();
		for (String id : documents) {
			list.add(session.organizations().getDocumentById(id, organization));
		}
		return Collections.unmodifiableSet(list);
	}

	@Override
	public boolean removeDocument(OrganizationModel organization, DocumentModel document) {
		/*
		 * session.users().preRemove(realm, role); RoleContainerModel container
		 * = role.getContainer(); if
		 * (container.getDefaultRoles().contains(role.getName())) {
		 * container.removeDefaultRoles(role.getName()); } RoleEntity roleEntity
		 * = em.getReference(RoleEntity.class, role.getId()); String
		 * compositeRoleTable =
		 * JpaUtils.getTableNameForNativeQuery("COMPOSITE_ROLE", em);
		 * em.createNativeQuery("delete from " + compositeRoleTable +
		 * " where CHILD_ROLE = :role") .setParameter("role",
		 * roleEntity).executeUpdate();
		 * em.createNamedQuery("deleteScopeMappingByRole").setParameter("role",
		 * roleEntity).executeUpdate();
		 * em.createNamedQuery("deleteTemplateScopeMappingByRole").setParameter(
		 * "role", roleEntity).executeUpdate(); int val =
		 * em.createNamedQuery("deleteGroupRoleMappingsByRole").setParameter(
		 * "roleId", roleEntity.getId()) .executeUpdate();
		 * 
		 * em.remove(roleEntity); em.flush(); return true;
		 */
		return false;
	}

}

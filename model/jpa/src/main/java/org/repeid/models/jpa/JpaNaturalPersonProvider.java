package org.repeid.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.NaturalPersonModel;
import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.DocumentEntity;
import org.repeid.models.jpa.entities.NaturalPersonEntity;
import org.repeid.models.jpa.entities.OrganizationEntity;

public class JpaNaturalPersonProvider implements NaturalPersonProvider {

	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String DOCUMENT_NUMBER = "documentNumber";
	
	protected static final Logger logger = Logger.getLogger(JpaNaturalPersonProvider.class);
	
	private final RepeidSession session;
	protected EntityManager em;

	public JpaNaturalPersonProvider(RepeidSession session, EntityManager em) {
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
	}

	@Override
	public NaturalPersonModel addNaturalPerson(OrganizationModel organization, DocumentModel document, String documentNumber) {
	    return addNaturalPerson(organization, null, document, documentNumber);
	}
	
	@Override
	public NaturalPersonModel addNaturalPerson(OrganizationModel organization, String id, DocumentModel document, String documentNumber) {
        NaturalPersonEntity entity = new NaturalPersonEntity();
        entity.setId(id);
        entity.setDocumentNumber(documentNumber);
        DocumentEntity documentRef = em.getReference(DocumentEntity.class, document.getId());
        OrganizationEntity organizationRef = em.getReference(OrganizationEntity.class, organization.getId());
        entity.setDocument(documentRef);
        entity.setOrganization(organizationRef);
        em.persist(entity);
        em.flush();
        NaturalPersonAdapter personModel = new NaturalPersonAdapter(session, organization, em, entity);
        return personModel;
	}	
	
	@Override
	public boolean removeNaturalPerson(OrganizationModel organization, NaturalPersonModel person) {
		NaturalPersonEntity personEntity = em.find(NaturalPersonEntity.class, person.getId());
        if (personEntity == null) return false;
        removeNaturalPerson(personEntity);
        return true;
	}

    private void removeNaturalPerson(NaturalPersonEntity person) {
        String id = person.getId();
        /*em.createNamedQuery("deleteUserRoleMappingsByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserGroupMembershipsByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteFederatedIdentityByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentRolesByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentProtMappersByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentsByUser").setParameter("user", person).executeUpdate();
        em.flush();*/
        // not sure why i have to do a clear() here.  I was getting some messed up errors that Hibernate couldn't
        // un-delete the NaturalPersonEntity.
        em.clear();
        person = em.find(NaturalPersonEntity.class, id);
        if (person != null) {
            em.remove(person);
        }
        em.flush();
    }
    
	@Override
	public NaturalPersonModel getNaturalPersonById(String id, OrganizationModel organization) {
		TypedQuery<NaturalPersonEntity> query = em.createNamedQuery("getOrganizationNaturalPersonById", NaturalPersonEntity.class);
        query.setParameter("id", id);
        query.setParameter("organizationId", organization.getId());
        List<NaturalPersonEntity> entities = query.getResultList();
        if (entities.size() == 0) return null;
        return new NaturalPersonAdapter(session, organization, em, entities.get(0));
	}

	@Override
	public NaturalPersonModel getNaturalPersonByDocument(DocumentModel document, String documentNumber, OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNaturalPersonCount(OrganizationModel organization) {
		Object count = em.createNamedQuery("getOrganizationNaturalPersonCount")
                .setParameter("organizationId", organization.getId())
                .getSingleResult();
        return ((Number)count).intValue();
	}

	@Override
	public List<NaturalPersonModel> getNaturalPersons(OrganizationModel organization, int firstResult, int maxResults) {
        TypedQuery<NaturalPersonEntity> query = em.createNamedQuery("getAllNaturalPersonsByOrganization", NaturalPersonEntity.class);
        query.setParameter("organizationId", organization.getId());
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<NaturalPersonEntity> results = query.getResultList();
        List<NaturalPersonModel> naturalPersons = new ArrayList<>();
        for (NaturalPersonEntity entity : results) naturalPersons.add(new NaturalPersonAdapter(session, organization, em, entity));
        return naturalPersons;
	}

	@Override
	public List<NaturalPersonModel> searchForNaturalPerson(String search, OrganizationModel organization) {
		return searchForNaturalPerson(search, organization, -1, -1);
	}

	@Override
	public List<NaturalPersonModel> searchForNaturalPerson(String search, OrganizationModel organization, int firstResult, int maxResults) {
		TypedQuery<NaturalPersonEntity> query = em.createNamedQuery("searchForNaturalPerson", NaturalPersonEntity.class);
		query.setParameter("organizationId", organization.getId());
		query.setParameter("search", "%" + search.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<NaturalPersonEntity> results = query.getResultList();
		List<NaturalPersonModel> naturalPersons = new ArrayList<>();
		for (NaturalPersonEntity entity : results)
			naturalPersons.add(new NaturalPersonAdapter(session, organization, em, entity));
		return naturalPersons;
	}

	@Override
	public List<NaturalPersonModel> searchForNaturalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization) {
		return searchForNaturalPersonByAttributes(attributes, organization, -1, -1);
	}

	@Override
	public List<NaturalPersonModel> searchForNaturalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization, int firstResult, int maxResults) {
		StringBuilder builder = new StringBuilder("select p from NaturalPersonEntity p where p.organizationId = :organizationId");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attribute = null;
            String parameterName = null;
            if (entry.getKey().equals(NaturalPersonModel.FIRST_NAME)) {
                attribute = "lower(p.firstName)";
                parameterName = JpaNaturalPersonProvider.FIRST_NAME;
            } else if (entry.getKey().equals(NaturalPersonModel.LAST_NAME)) {
                attribute = "lower(p.firstName)";
                parameterName = JpaNaturalPersonProvider.LAST_NAME;
            } else if (entry.getKey().equals(NaturalPersonModel.DOCUMENT_NUMBER)) {
                attribute = "lower(p.documentNumber)";
                parameterName = JpaNaturalPersonProvider.DOCUMENT_NUMBER;
            }
            if (attribute == null) continue;
            builder.append(" and ");
            builder.append(attribute).append(" like :").append(parameterName);
        }
        builder.append(" order by p.lastName");
        String q = builder.toString();
        TypedQuery<NaturalPersonEntity> query = em.createQuery(q, NaturalPersonEntity.class);
        query.setParameter("organizationId", organization.getId());
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String parameterName = null;
            if (entry.getKey().equals(NaturalPersonModel.FIRST_NAME)) {
                parameterName = JpaNaturalPersonProvider.FIRST_NAME;
            } else if (entry.getKey().equals(NaturalPersonModel.LAST_NAME)) {
                parameterName = JpaNaturalPersonProvider.LAST_NAME;
            } else if (entry.getKey().equals(NaturalPersonModel.DOCUMENT_NUMBER)) {
                parameterName = JpaNaturalPersonProvider.DOCUMENT_NUMBER;
            }
            if (parameterName == null) continue;
            query.setParameter(parameterName, "%" + entry.getValue().toLowerCase() + "%");
        }
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<NaturalPersonEntity> results = query.getResultList();
        List<NaturalPersonModel> users = new ArrayList<>();
        for (NaturalPersonEntity entity : results) users.add(new NaturalPersonAdapter(session, organization, em, entity));
        return users;
	}

	@Override
	public void preRemove(OrganizationModel organization) {
		// TODO Auto-generated method stub		
	}

}

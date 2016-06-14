package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.LegalPersonModel;
import org.repeid.models.LegalPersonProvider;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.LegalPersonEntity;
import org.repeid.models.jpa.entities.OrganizationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class JpaLegalPersonProvider implements LegalPersonProvider {

	protected static final Logger logger = Logger.getLogger(JpaLegalPersonProvider.class);
	
	private static final String NAME = "name";
	
	private final RepeidSession session;
	protected EntityManager em;

	public JpaLegalPersonProvider(RepeidSession session, EntityManager em) {
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
	}

	@Override
	public LegalPersonModel addLegalPerson(OrganizationModel organization, String id, String name) {
        LegalPersonEntity entity = new LegalPersonEntity();
        entity.setId(id);
        entity.setName(name);
        OrganizationEntity ref = em.getReference(OrganizationEntity.class, organization.getId());        
        entity.setOrganization(ref);
        em.persist(entity);
        em.flush();
        LegalPersonAdapter personModel = new LegalPersonAdapter(session, organization, em, entity);
        return personModel;
	}

	@Override
	public LegalPersonModel addLegalPerson(OrganizationModel organization, String name) {
	    return addLegalPerson(organization, null, name);
	}

	@Override
	public boolean removeLegalPerson(OrganizationModel organization, LegalPersonModel person) {
		LegalPersonEntity personEntity = em.find(LegalPersonEntity.class, person.getId());
        if (personEntity == null) return false;
        removeLegalPerson(personEntity);
        return true;
	}

    private void removeLegalPerson(LegalPersonEntity person) {
        String id = person.getId();
        /*em.createNamedQuery("deleteUserRoleMappingsByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserGroupMembershipsByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteFederatedIdentityByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentRolesByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentProtMappersByUser").setParameter("user", person).executeUpdate();
        em.createNamedQuery("deleteUserConsentsByUser").setParameter("user", person).executeUpdate();
        em.flush();*/
        // not sure why i have to do a clear() here.  I was getting some messed up errors that Hibernate couldn't
        // un-delete the LegalPersonEntity.
        em.clear();
        person = em.find(LegalPersonEntity.class, id);
        if (person != null) {
            em.remove(person);
        }
        em.flush();
    }
    
	@Override
	public LegalPersonModel getLegalPersonById(String id, OrganizationModel organization) {
		TypedQuery<LegalPersonEntity> query = em.createNamedQuery("getOrganizationLegalPersonById", LegalPersonEntity.class);
        query.setParameter("id", id);
        query.setParameter("organizationId", organization.getId());
        List<LegalPersonEntity> entities = query.getResultList();
        if (entities.size() == 0) return null;
        return new LegalPersonAdapter(session, organization, em, entities.get(0));
	}

	@Override
	public LegalPersonModel getLegalPersonByDocument(DocumentModel document, String documentNumber, OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLegalPersonCount(OrganizationModel organization) {
		Object count = em.createNamedQuery("getOrganizationLegalPersonCount")
                .setParameter("organizationId", organization.getId())
                .getSingleResult();
        return ((Number)count).intValue();
	}

	@Override
	public List<LegalPersonModel> getLegalPersons(OrganizationModel organization, int firstResult, int maxResults) {
        TypedQuery<LegalPersonEntity> query = em.createNamedQuery("getAllLegalPersonsByOrganization", LegalPersonEntity.class);
        query.setParameter("organizationId", organization.getId());
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<LegalPersonEntity> results = query.getResultList();
        List<LegalPersonModel> legalPersons = new ArrayList<>();
        for (LegalPersonEntity entity : results) legalPersons.add(new LegalPersonAdapter(session, organization, em, entity));
        return legalPersons;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization) {
		return searchForLegalPerson(search, organization, -1, -1);
	}

	@Override
	public List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization, int firstResult, int maxResults) {
		TypedQuery<LegalPersonEntity> query = em.createNamedQuery("searchForLegalPerson", LegalPersonEntity.class);
		query.setParameter("organizationId", organization.getId());
		query.setParameter("search", "%" + search.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<LegalPersonEntity> results = query.getResultList();
		List<LegalPersonModel> legalPersons = new ArrayList<>();
		for (LegalPersonEntity entity : results)
			legalPersons.add(new LegalPersonAdapter(session, organization, em, entity));
		return legalPersons;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization) {
		return searchForLegalPersonByAttributes(attributes, organization, -1, -1);
	}

	@Override
	public List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization, int firstResult, int maxResults) {
		StringBuilder builder = new StringBuilder("select p from LegalPersonEntity p where p.organizationId = :organizationId");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attribute = null;
            String parameterName = null;
            if (entry.getKey().equals(LegalPersonModel.NAME)) {
                attribute = "lower(p.name)";
                parameterName = JpaLegalPersonProvider.NAME;
            }
            if (attribute == null) continue;
            builder.append(" and ");
            builder.append(attribute).append(" like :").append(parameterName);
        }
        builder.append(" order by p.name");
        String q = builder.toString();
        TypedQuery<LegalPersonEntity> query = em.createQuery(q, LegalPersonEntity.class);
        query.setParameter("organizationId", organization.getId());
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String parameterName = null;
            if (entry.getKey().equals(LegalPersonModel.NAME)) {
                parameterName = JpaLegalPersonProvider.NAME;
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
        List<LegalPersonEntity> results = query.getResultList();
        List<LegalPersonModel> users = new ArrayList<>();
        for (LegalPersonEntity entity : results) users.add(new LegalPersonAdapter(session, organization, em, entity));
        return users;
	}

	@Override
	public void preRemove(OrganizationModel organization) {
		// TODO Auto-generated method stub		
	}

}

package org.repeid.manager.api.jpa.models.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.jpa.AbstractStorage;
import org.repeid.manager.api.jpa.entities.security.RoleMembershipEntity;
import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.security.UserProvider;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Named
@Stateless
@Local(UserProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaUserProvider extends AbstractStorage implements UserProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public UserModel create(String username, String fullName, String email) throws StorageException {
        if (findByUsername(username) != null) {
            throw new ModelDuplicateException(
                    "UserEntity username debe ser unico, se encontro otra entidad con username:" + username);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setFullName(fullName);
        userEntity.setEmail(email);
        userEntity.setJoinedOn(Calendar.getInstance().getTime());
        getEntityManager().persist(userEntity);
        return new UserAdapter(getEntityManager(), userEntity);
    }

    @Override
    public UserModel findById(String id) throws StorageException {
        UserEntity userEntity = getEntityManager().find(UserEntity.class, id);
        return userEntity != null ? new UserAdapter(getEntityManager(), userEntity) : null;
    }

    @Override
    public UserModel findByUsername(String username) throws StorageException {
        TypedQuery<UserEntity> query = getEntityManager().createNamedQuery("UserEntity.findByUsername",
                UserEntity.class);
        query.setParameter("username", username);
        List<UserEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException(
                    "Mas de un UserEntity con usermane=" + username + ", results=" + results);
        } else {
            return new UserAdapter(getEntityManager(), results.get(0));
        }
    }

    @Override
    public boolean remove(UserModel user) {
        UserEntity userEntity = getEntityManager().find(UserEntity.class, user.getId());

        TypedQuery<RoleMembershipEntity> query = getEntityManager()
                .createNamedQuery("RoleMembershipEntity.findByUserId", RoleMembershipEntity.class);
        query.setParameter("userId", user.getId());
        List<RoleMembershipEntity> roleMemberships = query.getResultList();

        getEntityManager().remove(userEntity);
        for (RoleMembershipEntity roleMembership : roleMemberships) {
            getEntityManager().remove(roleMembership);
        }
        return true;
    }

    @Override
    public Set<PermissionType> getPermissions(String userId) throws StorageException {
        TypedQuery<RoleMembershipEntity> query = getEntityManager()
                .createNamedQuery("RoleMembershipEntity.findByUserId", RoleMembershipEntity.class);
        query.setParameter("userId", userId);
        List<RoleMembershipEntity> roleMemberships = query.getResultList();

        Set<PermissionType> result = new HashSet<>();
        for (RoleMembershipEntity membership : roleMemberships) {
            result.addAll(membership.getRole().getPermissions());
        }
        return result;
    }

    @Override
    public List<UserModel> getAll() {
        return getAll(-1, -1);
    }

    @Override
    public List<UserModel> getAll(int firstResult, int maxResults) {
        TypedQuery<UserEntity> query = getEntityManager().createNamedQuery("UserEntity.findAll",
                UserEntity.class);
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<UserEntity> entities = query.getResultList();
        List<UserModel> models = new ArrayList<>();
        for (UserEntity entity : entities) {
            models.add(new UserAdapter(getEntityManager(), entity));
        }
        return models;
    }

    /*
     * @Override public List<TipoDocumentoModel> search(String filterText) {
     * return search(filterText, -1, -1); }
     * 
     * @Override public List<TipoDocumentoModel> search(String filterText, int
     * firstResult, int maxResults) { TypedQuery<TipoDocumentoEntity> query =
     * getEntityManager()
     * .createNamedQuery("TipoDocumentoEntity.findByFilterText",
     * TipoDocumentoEntity.class); query.setParameter("filterText", "%" +
     * filterText.toLowerCase() + "%"); if (firstResult != -1) {
     * query.setFirstResult(firstResult); } if (maxResults != -1) {
     * query.setMaxResults(maxResults); } List<TipoDocumentoEntity> entities =
     * query.getResultList(); List<TipoDocumentoModel> models = new
     * ArrayList<TipoDocumentoModel>(); for (TipoDocumentoEntity
     * tipoDocumentoEntity : entities) { models.add(new
     * TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity)); }
     * 
     * return models; }
     */

    /*
     * @Override public List<TipoDocumentoModel> searchByAttributes(Map<String,
     * Object> attributes) { return searchByAttributes(attributes, -1, -1); }
     * 
     * @Override public List<TipoDocumentoModel> searchByAttributes(Map<String,
     * Object> attributes, int firstResult, int maxResults) { StringBuilder
     * builder = new StringBuilder("SELECT t FROM TipoDocumentoEntity"); for
     * (Map.Entry<String, Object> entry : attributes.entrySet()) { String
     * attribute = null; String parameterName = null; if
     * (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) { attribute =
     * "lower(t.abreviatura)"; parameterName = JpaUserProvider.ABREVIATURA; }
     * else if
     * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
     * attribute = "lower(t.denominacion)"; parameterName =
     * JpaUserProvider.DENOMINACION; }
     * 
     * if (attribute != null) { builder.append(" and ");
     * builder.append(attribute).append(" like :").append(parameterName); } else
     * { if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
     * attribute = "lower(t.tipoPersona)"; parameterName =
     * JpaUserProvider.TIPO_PERSONA; } else if
     * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) { attribute
     * = "t.estado"; parameterName = JpaUserProvider.ESTADO; }
     * 
     * if (attribute == null) { continue; } builder.append(" and ");
     * builder.append(attribute).append(" = :").append(parameterName); } }
     * builder.append(" order by t.abreviatura"); String q = builder.toString();
     * TypedQuery<TipoDocumentoEntity> query = getEntityManager().createQuery(q,
     * TipoDocumentoEntity.class); for (Map.Entry<String, Object> entry :
     * attributes.entrySet()) { String parameterName = null; if
     * (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) { parameterName =
     * JpaUserProvider.ABREVIATURA; } else if
     * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
     * parameterName = JpaUserProvider.DENOMINACION; }
     * 
     * if (parameterName != null) { query.setParameter(parameterName, "%" +
     * String.valueOf(entry.getValue()).toLowerCase() + "%"); } else { if
     * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
     * parameterName = JpaUserProvider.TIPO_PERSONA; } else if
     * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
     * parameterName = JpaUserProvider.ESTADO; }
     * 
     * if (parameterName == null) { continue; }
     * query.setParameter(parameterName, entry.getValue()); } } if (firstResult
     * != -1) { query.setFirstResult(firstResult); } if (maxResults != -1) {
     * query.setMaxResults(maxResults); } List<TipoDocumentoEntity> results =
     * query.getResultList(); List<TipoDocumentoModel> tipoDocumentos = new
     * ArrayList<TipoDocumentoModel>(); for (TipoDocumentoEntity entity :
     * results) tipoDocumentos.add(new TipoDocumentoAdapter(getEntityManager(),
     * entity)); return tipoDocumentos; }
     */

    @Override
    public SearchResultsModel<UserModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<UserEntity> entityResult = find(criteria, UserEntity.class);

        SearchResultsModel<UserModel> modelResult = new SearchResultsModel<>();
        List<UserModel> list = new ArrayList<>();
        for (UserEntity entity : entityResult.getModels()) {
            list.add(new UserAdapter(getEntityManager(), entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<UserModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<UserEntity> entityResult = findFullText(criteria, UserEntity.class, filterText,
                "username");

        SearchResultsModel<UserModel> modelResult = new SearchResultsModel<>();
        List<UserModel> list = new ArrayList<>();
        for (UserEntity entity : entityResult.getModels()) {
            list.add(new UserAdapter(getEntityManager(), entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}

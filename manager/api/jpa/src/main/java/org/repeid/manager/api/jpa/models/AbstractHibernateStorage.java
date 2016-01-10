package org.repeid.manager.api.jpa.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.search.OrderByModel;
import org.repeid.manager.api.model.search.PagingModel;
import org.repeid.manager.api.model.search.SearchCriteriaFilterModel;
import org.repeid.manager.api.model.search.SearchCriteriaFilterOperator;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base class that Hibernate storage impls can extend.
 *
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public abstract class AbstractHibernateStorage {

    private static Logger logger = LoggerFactory.getLogger(AbstractHibernateStorage.class);

    /**
     * Constructor.
     */
    public AbstractHibernateStorage() {

    }

    protected abstract EntityManager getEntityManager();

    protected Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    /**
     * @param bean
     *            the bean to create
     * @throws StorageException
     *             if a storage problem occurs while storing a bean
     */
    public <T> void create(T bean) throws StorageException {
        if (bean == null) {
            return;
        }
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.persist(bean);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
    }

    /**
     * @param bean
     *            the bean to update
     * @throws StorageException
     *             if a storage problem occurs while storing a bean
     */
    public <T> void update(T bean) throws StorageException {
        EntityManager entityManager = getEntityManager();
        try {
            if (!entityManager.contains(bean)) {
                entityManager.merge(bean);
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
    }

    /**
     * Delete using bean
     *
     * @param bean
     *            the bean to delete
     * @throws StorageException
     *             if a storage problem occurs while storing a bean
     */
    public <T> void delete(T bean) throws StorageException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.remove(bean);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
    }

    /**
     * Get object of type T
     *
     * @param id
     *            identity key
     * @param type
     *            class of type T
     * @return Instance of type T
     * @throws StorageException
     *             if a storage problem occurs while storing a bean
     */
    public <T> T get(Long id, Class<T> type) throws StorageException {
        T rval = null;
        EntityManager entityManager = getEntityManager();
        try {
            rval = entityManager.find(type, id);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
        return rval;
    }

    /**
     * Get object of type T
     *
     * @param id
     *            identity key
     * @param type
     *            class of type T
     * @return Instance of type T
     * @throws StorageException
     *             if a storage problem occurs while storing a bean
     */
    public <T> T get(String id, Class<T> type) throws StorageException {
        T rval = null;
        EntityManager entityManager = getEntityManager();
        try {
            rval = entityManager.find(type, id);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
        return rval;
    }    

    protected <T> SearchResultsModel<T> findFullText(SearchCriteriaModel criteria, Class<T> type,
            String filterText, String... field) {

        SearchResultsModel<T> results = new SearchResultsModel<>();
        Session session = getSession();

        // Set some default in the case that paging information was not
        // included in the request.
        PagingModel paging = criteria.getPaging();
        if (paging == null) {
            paging = new PagingModel();
            paging.setPage(1);
            paging.setPageSize(20);
        }
        int page = paging.getPage();
        int pageSize = paging.getPageSize();
        int start = (page - 1) * pageSize;

        Criteria criteriaQuery = session.createCriteria(type);
        applySearchCriteriaToQuery(criteria, type, criteriaQuery, false);

        // filter text
        List<Criterion> disjuntions = new ArrayList<>();
        for (String fieldName : field) {
            Criterion criterion = Restrictions.ilike(fieldName, filterText, MatchMode.ANYWHERE);
            disjuntions.add(criterion);
        }
        criteriaQuery.add(Restrictions.or(disjuntions.toArray(new Criterion[disjuntions.size()])));

        // paging
        criteriaQuery.setFirstResult(start);
        criteriaQuery.setMaxResults(pageSize + 1);
        boolean hasMore = false;

        // Now query for the actual results
        @SuppressWarnings("unchecked")
        List<T> resultList = criteriaQuery.list();

        // Check if we got back more than we actually needed.
        if (resultList.size() > pageSize) {
            resultList.remove(resultList.size() - 1);
            hasMore = true;
        }

        // If there are more results than we needed, then we will need to do
        // another
        // query to determine how many rows there are in total
        int totalSize = start + resultList.size();
        if (hasMore) {
            totalSize = executeCountQuery(criteria, session, type, filterText, field);
        }
        results.setTotalSize(totalSize);
        results.setModels(resultList);
        return results;
    }

    protected <T> SearchResultsModel<T> find(SearchCriteriaModel criteria, Class<T> type) {
        SearchResultsModel<T> results = new SearchResultsModel<>();
        Session session = getSession();

        // Set some default in the case that paging information was not
        // included in the request.
        PagingModel paging = criteria.getPaging();
        if (paging == null) {
            paging = new PagingModel();
            paging.setPage(1);
            paging.setPageSize(20);
        }
        int page = paging.getPage();
        int pageSize = paging.getPageSize();
        int start = (page - 1) * pageSize;

        Criteria criteriaQuery = session.createCriteria(type);
        applySearchCriteriaToQuery(criteria, type, criteriaQuery, false);
        criteriaQuery.setFirstResult(start);
        criteriaQuery.setMaxResults(pageSize + 1);
        boolean hasMore = false;

        // Now query for the actual results
        @SuppressWarnings("unchecked")
        List<T> resultList = criteriaQuery.list();

        // Check if we got back more than we actually needed.
        if (resultList.size() > pageSize) {
            resultList.remove(resultList.size() - 1);
            hasMore = true;
        }

        // If there are more results than we needed, then we will need to do
        // another
        // query to determine how many rows there are in total
        int totalSize = start + resultList.size();
        if (hasMore) {
            totalSize = executeCountQuery(criteria, session, type);
        }
        results.setTotalSize(totalSize);
        results.setModels(resultList);
        return results;
    }

    protected <T> int executeCountQuery(SearchCriteriaModel criteria, Session session, Class<T> type) {
        Criteria criteriaQuery = session.createCriteria(type);
        applySearchCriteriaToQuery(criteria, type, criteriaQuery, true);
        criteriaQuery.setProjection(Projections.rowCount());
        return ((Long) criteriaQuery.uniqueResult()).intValue();
    }

    protected <T> int executeCountQuery(SearchCriteriaModel criteria, Session session, Class<T> type,
            String filterText, String... field) {
        Criteria criteriaQuery = session.createCriteria(type);
        applySearchCriteriaToQuery(criteria, type, criteriaQuery, true);
        List<Criterion> disjuntionsCount = new ArrayList<>();
        for (String fieldName : field) {
            Criterion criterion = Restrictions.ilike(fieldName, filterText, MatchMode.ANYWHERE);
            disjuntionsCount.add(criterion);
        }
        criteriaQuery.add(Restrictions.or(disjuntionsCount.toArray(new Criterion[disjuntionsCount.size()])));
        criteriaQuery.setProjection(Projections.rowCount());
        return ((Long) criteriaQuery.uniqueResult()).intValue();
    }

    protected <T> void applySearchCriteriaToQuery(SearchCriteriaModel criteria, Class<T> type,
            Criteria criteriaQuery, boolean countOnly) {
        List<SearchCriteriaFilterModel> filters = criteria.getFilters();
        if (filters != null && !filters.isEmpty()) {
            for (SearchCriteriaFilterModel filter : filters) {
                if (filter.getOperator() == SearchCriteriaFilterOperator.eq) {
                    criteriaQuery.add(Restrictions.eq(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.bool_eq) {
                    criteriaQuery.add(Restrictions.eq(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.gt) {
                    criteriaQuery.add(Restrictions.gt(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.gte) {
                    criteriaQuery.add(Restrictions.ge(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.lt) {
                    criteriaQuery.add(Restrictions.lt(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.lte) {
                    criteriaQuery.add(Restrictions.le(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.neq) {
                    criteriaQuery.add(Restrictions.ne(filter.getName(), filter.getValue()));
                } else if (filter.getOperator() == SearchCriteriaFilterOperator.like) {
                    criteriaQuery.add(Restrictions.like(filter.getName(), (String) filter.getValue(),
                            MatchMode.ANYWHERE));
                }
            }
        }
        List<OrderByModel> orders = criteria.getOrders();
        if (orders != null && !orders.isEmpty() && !countOnly) {
            for (OrderByModel orderBy : orders) {
                if (orderBy.isAscending()) {
                    criteriaQuery.addOrder(Order.asc(orderBy.getName()));
                } else {
                    criteriaQuery.addOrder(Order.desc(orderBy.getName()));
                }
            }
        }
    }
}
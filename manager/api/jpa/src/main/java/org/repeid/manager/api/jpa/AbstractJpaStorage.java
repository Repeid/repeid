/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

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
public abstract class AbstractJpaStorage {

    @Inject
    private JpaConnectionProvider connectionProvider;

    private static Logger logger = LoggerFactory.getLogger(AbstractJpaStorage.class);

    public EntityManager getEntityManager() {
        return connectionProvider.getEntityManager();
    }

    public Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    public <T> void create(T bean) throws StorageException {
        if (bean == null) {
            return;
        }
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.persist(bean);
        } catch (EntityExistsException e) {
            throw new StorageException(e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (TransactionRequiredException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
    }

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

    public <T> void delete(T bean) throws StorageException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.remove(bean);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
    }

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

    public <T> List<T> executeTypedQuery(TypedQuery<T> typedQuery) throws StorageException {
        try {
            return typedQuery.getResultList();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (QueryTimeoutException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (TransactionRequiredException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (PessimisticLockException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (LockTimeoutException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (PersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException(e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new StorageException(t);
        }
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

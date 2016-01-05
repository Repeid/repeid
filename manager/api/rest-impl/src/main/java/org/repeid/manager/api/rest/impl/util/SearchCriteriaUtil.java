/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.manager.api.rest.impl.util;

import java.util.HashSet;
import java.util.Set;

import org.repeid.manager.api.core.representations.idm.search.OrderByRepresentation;
import org.repeid.manager.api.core.representations.idm.search.PagingRepresentation;
import org.repeid.manager.api.core.representations.idm.search.SearchCriteriaFilterRepresentation;
import org.repeid.manager.api.core.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.model.search.SearchCriteriaFilterOperator;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.impl.i18n.Messages;

/**
 * Some utility methods related to searches and search criteria.
 *
 * @author eric.wittmann@redhat.com
 */
public final class SearchCriteriaUtil {

    public static final Set<SearchCriteriaFilterOperator> validOperators = new HashSet<>();

    static {
        validOperators.add(SearchCriteriaFilterOperator.eq);
        validOperators.add(SearchCriteriaFilterOperator.gt);
        validOperators.add(SearchCriteriaFilterOperator.gte);
        validOperators.add(SearchCriteriaFilterOperator.lt);
        validOperators.add(SearchCriteriaFilterOperator.lte);
        validOperators.add(SearchCriteriaFilterOperator.neq);
        validOperators.add(SearchCriteriaFilterOperator.like);
    }

    /**
     * Validates that the search criteria bean is complete and makes sense.
     * 
     * @param criteria
     *            the search criteria
     * @throws InvalidSearchCriteriaException
     *             when the search criteria is not valid
     */
    public static final void validateSearchCriteria(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException {
        if (criteria.getPaging() != null) {
            if (criteria.getPaging().getPage() < 1) {
                throw new InvalidSearchCriteriaException(
                        Messages.i18n.format("SearchCriteriaUtil.MissingPage")); //$NON-NLS-1$
            }
            if (criteria.getPaging().getPageSize() < 1) {
                throw new InvalidSearchCriteriaException(
                        Messages.i18n.format("SearchCriteriaUtil.MissingPageSize")); //$NON-NLS-1$
            }
        }
        int count = 1;
        for (SearchCriteriaFilterRepresentation filter : criteria.getFilters()) {
            if (filter.getName() == null || filter.getName().trim().length() == 0) {
                throw new InvalidSearchCriteriaException(
                        Messages.i18n.format("SearchCriteriaUtil.MissingSearchFilterName", count)); //$NON-NLS-1$
            }
            if (filter.getValue() == null) {
                throw new InvalidSearchCriteriaException(
                        Messages.i18n.format("SearchCriteriaUtil.MissingSearchFilterValue", count)); //$NON-NLS-1$
            }
            if (filter.getOperator() == null || !validOperators.contains(filter.getOperator())) {
                throw new InvalidSearchCriteriaException(
                        Messages.i18n.format("SearchCriteriaUtil.MissingSearchFilterOperator", count)); //$NON-NLS-1$
            }
            count++;
        }
        if (criteria.getOrders() != null) {
            throw new InvalidSearchCriteriaException(
                    Messages.i18n.format("SearchCriteriaUtil.MissingOrderByName")); //$NON-NLS-1$
        }
    }

    public static SearchCriteriaModel getSearchCriteriaModel(SearchCriteriaRepresentation criteria) {
        SearchCriteriaModel criteriaModel = new SearchCriteriaModel();

        // set filter and order
        for (SearchCriteriaFilterRepresentation filter : criteria.getFilters()) {
            criteriaModel.addFilter(filter.getName(), filter.getValue(),
                    SearchCriteriaFilterOperator.valueOf(filter.getOperator().toString()));
        }
        for (OrderByRepresentation order : criteria.getOrders()) {
            criteriaModel.addOrder(order.getName(), order.isAscending());
        }

        // set paging
        PagingRepresentation paging = criteria.getPaging();
        if (paging == null) {
            paging = new PagingRepresentation();
            paging.setPage(1);
            paging.setPageSize(20);
        }
        criteriaModel.setPageSize(paging.getPageSize());
        criteriaModel.setPage(paging.getPage());

        return criteriaModel;
    }

}

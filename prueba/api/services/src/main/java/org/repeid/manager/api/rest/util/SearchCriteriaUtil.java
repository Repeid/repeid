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
package org.repeid.manager.api.rest.util;

import java.util.HashSet;
import java.util.Set;

import org.repeid.manager.api.beans.representations.search.OrderByRepresentation;
import org.repeid.manager.api.beans.representations.search.PagingRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaFilterRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.model.search.SearchCriteriaFilterOperator;
import org.repeid.manager.api.model.search.SearchCriteriaModel;

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
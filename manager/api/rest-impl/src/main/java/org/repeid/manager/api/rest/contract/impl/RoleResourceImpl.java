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

package org.repeid.manager.api.rest.contract.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.rest.contract.IRoleResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;

/**
 * Implementation of the Role API.
 * 
 * @author eric.wittmann@redhat.com
 */
@RequestScoped
public class RoleResourceImpl implements IRoleResource {

	@Override
	public RoleRepresentation create(RoleRepresentation bean)
			throws RoleAlreadyExistsException, NotAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleRepresentation> list() throws NotAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleRepresentation get(String roleId) throws RoleNotFoundException, NotAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String roleId, RoleRepresentation bean) throws RoleNotFoundException, NotAuthorizedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String roleId) throws RoleNotFoundException, NotAuthorizedException {
		// TODO Auto-generated method stub

	}

	@Override
	public SearchResultsRepresentation<RoleRepresentation> search(SearchCriteriaRepresentation criteria)
			throws InvalidSearchCriteriaException, NotAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

}

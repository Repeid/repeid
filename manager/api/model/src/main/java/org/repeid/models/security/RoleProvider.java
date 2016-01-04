package org.repeid.models.security;

import java.util.List;
import java.util.Set;

import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;

import io.apiman.manager.api.beans.idm.PermissionType;
import io.apiman.manager.api.core.exceptions.StorageException;

public interface RoleProvider {

    RoleModel create(String name, String description, boolean autogrant, Set<PermissionType> set)
            throws StorageException;

    RoleModel findById(String rolId) throws StorageException;

    boolean delete(RoleModel rol) throws StorageException;

    List<RoleModel> getAll() throws StorageException;

    SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

}

package org.repeid.manager.api.model.security;

import java.util.List;
import java.util.Set;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.security.PermissionType;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

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

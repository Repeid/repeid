package org.repeid.manager.api.model.security;

import java.util.List;
import java.util.Set;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

public interface RoleProvider extends Provider {

    RoleModel create(String name, String description, boolean autogrant, Set<PermissionType> set,
            String createdBy) throws StorageException;

    RoleModel findById(String rolId) throws StorageException;

    RoleModel findByName(String rolName) throws StorageException;

    boolean remove(RoleModel rol) throws StorageException;

    List<RoleModel> getAll() throws StorageException;

    List<RoleModel> getAll(int firstResult, int maxResults);

    SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

}

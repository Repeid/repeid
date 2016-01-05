package org.repeid.manager.api.model.security;

import java.util.Set;

import javax.ejb.Local;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.security.PermissionType;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

@Local
public interface UserProvider extends Provider {

    UserModel create(String username, String fullName, String email) throws StorageException;

    UserModel findById(String id) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

    Set<PermissionType> getPermissions(String userId) throws StorageException;

}

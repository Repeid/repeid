package org.repeid.models.security;

import java.util.Set;

import javax.ejb.Local;

import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.provider.Provider;
import org.repeid.representations.idm.security.PermissionType;

import io.apiman.manager.api.core.exceptions.StorageException;

@Local
public interface UserProvider extends Provider {

    UserModel create(String username, String fullName, String email) throws StorageException;

    UserModel findById(String id) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

    Set<PermissionType> getPermissions(String userId) throws StorageException;

}

package org.repeid.models.security;

import javax.ejb.Local;

import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.provider.Provider;

import io.apiman.manager.api.core.exceptions.StorageException;

@Local
public interface UserProvider extends Provider {

    UserModel findById(String id) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

}

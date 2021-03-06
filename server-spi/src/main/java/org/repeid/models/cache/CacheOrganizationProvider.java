package org.repeid.models.cache;

import org.repeid.models.OrganizationProvider;

public interface CacheOrganizationProvider extends OrganizationProvider {

	void clear();

	OrganizationProvider getDelegate();

	void registerOrganizationInvalidation(String id);

	void registerDocumentInvalidation(String id);

	void registerNaturalPersonInvalidation(String id);

	void registerLegalPersonInvalidation(String id);

}

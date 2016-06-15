
package org.repeid.models.cache.infinispan;

import org.repeid.models.cache.infinispan.entities.CachedDocument;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;

public interface OrganizationCache {

	void clear();

	CachedOrganization getOrganization(String id);

	void invalidateOrganization(CachedOrganization organization);

	void addOrganization(CachedOrganization organization);

	CachedOrganization getOrganizationByName(String name);

	void invalidateOrganizationById(String id);

	CachedDocument getDocument(String id);

	void invalidateDocument(CachedDocument document);

	void evictDocumentById(String id);

	void addDocument(CachedDocument document);

	void invalidateDocumentById(String id);

}

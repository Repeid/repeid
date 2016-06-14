package org.repeid.models.cache.infinispan.entities;

import java.util.Set;

public interface DocumentQuery extends InOrganization {
	Set<String> getDocuments();
}

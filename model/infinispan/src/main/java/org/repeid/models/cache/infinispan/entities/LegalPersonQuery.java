package org.repeid.models.cache.infinispan.entities;

import java.util.Set;

public interface LegalPersonQuery extends InOrganization {
	Set<String> getLegalPersons();
}

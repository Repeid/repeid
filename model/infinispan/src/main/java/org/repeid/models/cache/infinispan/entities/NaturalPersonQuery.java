package org.repeid.models.cache.infinispan.entities;

import java.util.Set;

public interface NaturalPersonQuery extends InOrganization {
	Set<String> getNaturalPersons();
}

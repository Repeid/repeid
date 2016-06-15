package org.repeid.models.cache.infinispan.stream;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

import org.repeid.models.cache.infinispan.entities.InOrganization;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class InOrganizationPredicate implements Predicate<Map.Entry<String, Revisioned>>, Serializable {

	private String organization;

	public static InOrganizationPredicate create() {
		return new InOrganizationPredicate();
	}

	public InOrganizationPredicate realm(String id) {
		organization = id;
		return this;
	}

	@Override
	public boolean test(Map.Entry<String, Revisioned> entry) {
		Object value = entry.getValue();
		if (value == null)
			return false;
		if (!(value instanceof InOrganization))
			return false;

		return organization.equals(((InOrganization) value).getOrganization());
	}
}

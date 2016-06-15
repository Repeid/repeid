package org.repeid.models.cache.infinispan.stream;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

import org.repeid.models.cache.infinispan.entities.OrganizationQuery;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class OrganizationQueryPredicate implements Predicate<Map.Entry<String, Revisioned>>, Serializable {

	private String organization;

	public static OrganizationQueryPredicate create() {
		return new OrganizationQueryPredicate();
	}

	public OrganizationQueryPredicate realm(String organization) {
		this.organization = organization;
		return this;
	}

	@Override
	public boolean test(Map.Entry<String, Revisioned> entry) {
		Object value = entry.getValue();
		if (value == null)
			return false;
		if (!(value instanceof OrganizationQuery))
			return false;
		OrganizationQuery query = (OrganizationQuery) value;

		return query.getOrganizations().contains(organization);
	}

}

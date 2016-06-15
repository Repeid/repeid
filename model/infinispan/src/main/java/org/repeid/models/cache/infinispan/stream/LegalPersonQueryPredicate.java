package org.repeid.models.cache.infinispan.stream;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

import org.repeid.models.cache.infinispan.entities.LegalPersonQuery;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class LegalPersonQueryPredicate implements Predicate<Map.Entry<String, Revisioned>>, Serializable {

	private String legalPerson;

	public static LegalPersonQueryPredicate create() {
		return new LegalPersonQueryPredicate();
	}

	public LegalPersonQueryPredicate role(String legalPerson) {
		this.legalPerson = legalPerson;
		return this;
	}

	@Override
	public boolean test(Map.Entry<String, Revisioned> entry) {
		Object value = entry.getValue();
		if (value == null)
			return false;
		if (!(value instanceof LegalPersonQuery))
			return false;
		LegalPersonQuery query = (LegalPersonQuery) value;

		return query.getLegalPersons().contains(legalPerson);
	}
}

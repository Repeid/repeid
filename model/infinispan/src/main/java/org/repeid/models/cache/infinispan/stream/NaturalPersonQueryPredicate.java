package org.repeid.models.cache.infinispan.stream;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

import org.repeid.models.cache.infinispan.entities.NaturalPersonQuery;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class NaturalPersonQueryPredicate implements Predicate<Map.Entry<String, Revisioned>>, Serializable {

	private String naturalPerson;

	public static NaturalPersonQueryPredicate create() {
		return new NaturalPersonQueryPredicate();
	}

	public NaturalPersonQueryPredicate role(String naturalPerson) {
		this.naturalPerson = naturalPerson;
		return this;
	}

	@Override
	public boolean test(Map.Entry<String, Revisioned> entry) {
		Object value = entry.getValue();
		if (value == null)
			return false;
		if (!(value instanceof NaturalPersonQuery))
			return false;
		NaturalPersonQuery query = (NaturalPersonQuery) value;

		return query.getNaturalPersons().contains(naturalPerson);
	}
}

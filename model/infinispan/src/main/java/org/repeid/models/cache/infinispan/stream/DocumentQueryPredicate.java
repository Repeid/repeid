package org.repeid.models.cache.infinispan.stream;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

import org.repeid.models.cache.infinispan.entities.DocumentQuery;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class DocumentQueryPredicate implements Predicate<Map.Entry<String, Revisioned>>, Serializable {

	private String document;

	public static DocumentQueryPredicate create() {
		return new DocumentQueryPredicate();
	}

	public DocumentQueryPredicate role(String document) {
		this.document = document;
		return this;
	}

	@Override
	public boolean test(Map.Entry<String, Revisioned> entry) {
		Object value = entry.getValue();
		if (value == null)
			return false;
		if (!(value instanceof DocumentQuery))
			return false;
		DocumentQuery query = (DocumentQuery) value;

		return query.getDocuments().contains(document);
	}
}

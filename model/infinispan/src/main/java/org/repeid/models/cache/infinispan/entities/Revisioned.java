package org.repeid.models.cache.infinispan.entities;

public interface Revisioned {

	String getId();

	Long getRevision();

	void setRevision(Long revision);

}

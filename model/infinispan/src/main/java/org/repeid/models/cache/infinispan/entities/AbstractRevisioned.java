package org.repeid.models.cache.infinispan.entities;

import java.io.Serializable;

public class AbstractRevisioned implements Revisioned, Serializable {
    private String id;
    private Long revision;

    public AbstractRevisioned(Long revision, String id) {
        this.revision = revision;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getRevision() {
        return revision;
    }

    @Override
    public void setRevision(Long revision) {
        this.revision = revision;
    }

}

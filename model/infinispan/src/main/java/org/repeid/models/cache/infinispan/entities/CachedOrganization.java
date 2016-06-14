package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.OrganizationModel;

public class CachedOrganization extends AbstractRevisioned {

	protected String name;
	protected boolean enabled;

	public CachedOrganization(Long revision, OrganizationModel model) {
		super(revision, model.getId());
		name = model.getName();
		enabled = model.isEnabled();
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}

}

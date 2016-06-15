package org.repeid.models.sessions.infinispan.entities;

import java.io.Serializable;

public class SessionEntity implements Serializable {

	private String id;

	private String organization;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SessionEntity))
			return false;

		SessionEntity that = (SessionEntity) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}

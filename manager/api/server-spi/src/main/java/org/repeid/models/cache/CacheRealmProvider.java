package org.repeid.models.cache;

import org.repeid.models.RealmProvider;

public interface CacheRealmProvider extends RealmProvider {
	RealmProvider getDelegate();

	boolean isEnabled();

	void setEnabled(boolean enabled);

	void registerRealmInvalidation(String id);

	void registerApplicationInvalidation(String id);

	void registerRoleInvalidation(String id);

	void registerOAuthClientInvalidation(String id);

	void registerUserInvalidation(String id);
}

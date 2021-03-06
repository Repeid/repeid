package org.repeid.services.managers;

import org.repeid.models.OrganizationProvider;
import org.repeid.models.OrganizationProviderFactory;
import org.repeid.models.RepeidSession;
import org.repeid.models.dblock.DBLockProvider;
import org.repeid.models.dblock.DBLockProviderFactory;
import org.repeid.services.ServicesLogger;

public class DBLockManager {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	private final RepeidSession session;

	public DBLockManager(RepeidSession session) {
		this.session = session;
	}

	public void checkForcedUnlock() {
		if (Boolean.getBoolean("repeid.dblock.forceUnlock")) {
			DBLockProvider lock = getDBLock();
			if (lock.supportsForcedUnlock()) {
				logger.forcedReleaseDBLock();
				lock.releaseLock();
			} else {
				throw new IllegalStateException("Forced unlock requested, but provider " + lock + " doesn't support it");
			}
		}
	}

	// Try to detect ID from organizationProvider
	public DBLockProvider getDBLock() {
		String organizationProviderId = getOrganizationProviderId();
		return session.getProvider(DBLockProvider.class, organizationProviderId);
	}

	public DBLockProviderFactory getDBLockFactory() {
		String organizationProviderId = getOrganizationProviderId();
		return (DBLockProviderFactory) session.getRepeidSessionFactory().getProviderFactory(DBLockProvider.class, organizationProviderId);
	}

	private String getOrganizationProviderId() {
		OrganizationProviderFactory organizationProviderFactory = (OrganizationProviderFactory) session.getRepeidSessionFactory().getProviderFactory(OrganizationProvider.class);
		return organizationProviderFactory.getId();
	}

}

package org.repeid.models.dblock;

import org.repeid.provider.ProviderFactory;

public interface DBLockProviderFactory extends ProviderFactory<DBLockProvider> {

    /**
     * Useful for testing to override provided configuration
     */
    void setTimeouts(long lockRecheckTimeMillis, long lockWaitTimeoutMillis);
}

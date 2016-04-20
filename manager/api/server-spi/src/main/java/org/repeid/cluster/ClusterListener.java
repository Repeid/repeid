package org.repeid.cluster;

/**
 * Task to be executed on all cluster nodes once it's notified.
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public interface ClusterListener {

    /**
     * Registered task to be executed on all cluster nodes once it's notified from cache.
     *
     * @param event value of notification (Object added into the cache)
     */
    void run(ClusterEvent event);

}

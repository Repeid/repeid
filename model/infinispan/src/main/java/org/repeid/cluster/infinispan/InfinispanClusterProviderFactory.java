package org.repeid.cluster.infinispan;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;
import org.infinispan.remoting.transport.Address;
import org.infinispan.remoting.transport.Transport;
import org.jboss.logging.Logger;
import org.repeid.Config;
import org.repeid.cluster.ClusterEvent;
import org.repeid.cluster.ClusterListener;
import org.repeid.cluster.ClusterProvider;
import org.repeid.cluster.ClusterProviderFactory;
import org.repeid.connections.infinispan.InfinispanConnectionProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;

public class InfinispanClusterProviderFactory implements ClusterProviderFactory {

    public static final String PROVIDER_ID = "infinispan";

    protected static final Logger logger = Logger.getLogger(InfinispanClusterProviderFactory.class);

    private volatile Cache<String, Serializable> workCache;

    private Map<String, ClusterListener> listeners = new HashMap<>();

    @Override
    public ClusterProvider create(RepeidSession session) {
        lazyInit(session);
        return new InfinispanClusterProvider(this, session, workCache);
    }

    private void lazyInit(RepeidSession session) {
        if (workCache == null) {
            synchronized (this) {
                if (workCache == null) {
                    workCache = session.getProvider(InfinispanConnectionProvider.class).getCache(InfinispanConnectionProvider.WORK_CACHE_NAME);
                    workCache.getCacheManager().addListener(new ViewChangeListener());
                    workCache.addListener(new CacheEntryListener());
                }
            }
        }
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(RepeidSessionFactory factory) {
    }


    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }


    @Listener
    public class ViewChangeListener {

        @ViewChanged
        public void viewChanged(ViewChangedEvent event) {
            EmbeddedCacheManager cacheManager = event.getCacheManager();
            Transport transport = cacheManager.getTransport();

            // Coordinator makes sure that entries for outdated nodes are cleaned up
            if (transport != null && transport.isCoordinator()) {

                Set<String> newAddresses = convertAddresses(event.getNewMembers());
                Set<String> removedNodesAddresses = convertAddresses(event.getOldMembers());
                removedNodesAddresses.removeAll(newAddresses);

                if (removedNodesAddresses.isEmpty()) {
                    return;
                }

                logger.debugf("Nodes %s removed from cluster. Removing tasks locked by this nodes", removedNodesAddresses.toString());

                Cache<String, Serializable> cache = cacheManager.getCache(InfinispanConnectionProvider.WORK_CACHE_NAME);

                Iterator<String> toRemove = cache.entrySet().stream().filter(new Predicate<Map.Entry<String, Serializable>>() {

                    @Override
                    public boolean test(Map.Entry<String, Serializable> entry) {
                        if (!(entry.getValue() instanceof LockEntry)) {
                            return false;
                        }

                        LockEntry lock = (LockEntry) entry.getValue();
                        return removedNodesAddresses.contains(lock.getNode());
                    }

                }).map(new Function<Map.Entry<String, Serializable>, String>() {

                    @Override
                    public String apply(Map.Entry<String, Serializable> entry) {
                        return entry.getKey();
                    }

                }).iterator();

                while (toRemove.hasNext()) {
                    String rem = toRemove.next();
                    if (logger.isTraceEnabled()) {
                        logger.tracef("Removing task %s due it's node left cluster", rem);
                    }
                    cache.remove(rem);
                }
            }
        }

        private Set<String> convertAddresses(Collection<Address> addresses) {
            return addresses.stream().map(new Function<Address, String>() {

                @Override
                public String apply(Address address) {
                    return address.toString();
                }

            }).collect(Collectors.toSet());
        }

    }


    <T> void registerListener(String taskKey, ClusterListener task) {
        listeners.put(taskKey, task);
    }

    @Listener
    public class CacheEntryListener {

        @CacheEntryCreated
        public void cacheEntryCreated(CacheEntryCreatedEvent<String, Object> event) {
            if (!event.isPre()) {
                trigger(event.getKey(), event.getValue());
            }
        }

        @CacheEntryModified
        public void cacheEntryModified(CacheEntryModifiedEvent<String, Object> event) {
            if (!event.isPre()) {
                trigger(event.getKey(), event.getValue());
            }
        }

        private void trigger(String key, Object value) {
            ClusterListener task = listeners.get(key);
            if (task != null) {
                ClusterEvent event = (ClusterEvent) value;
                task.run(event);
            }
        }
    }

}

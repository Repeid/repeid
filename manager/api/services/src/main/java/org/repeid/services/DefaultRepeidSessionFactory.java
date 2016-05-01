package org.repeid.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.provider.Provider;
import org.repeid.provider.ProviderEvent;
import org.repeid.provider.ProviderEventListener;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.ProviderManager;
import org.repeid.provider.Spi;

public class DefaultRepeidSessionFactory implements RepeidSessionFactory {

    private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    private Set<Spi> spis = new HashSet<>();
    private Map<Class<? extends Provider>, String> provider = new HashMap<Class<? extends Provider>, String>();
    private Map<Class<? extends Provider>, Map<String, ProviderFactory>> factoriesMap = new HashMap<Class<? extends Provider>, Map<String, ProviderFactory>>();
    protected CopyOnWriteArrayList<ProviderEventListener> listeners = new CopyOnWriteArrayList<ProviderEventListener>();

    public void init() {
        // TODO Auto-generated method stub
    }

    <T extends Provider> Set<String> getAllProviderIds(Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void register(ProviderEventListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregister(ProviderEventListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void publish(ProviderEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public RepeidSession create() {
        return new DefaultRepeidSession(this);
    }

    @Override
    public Set<Spi> getSpis() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProviderFactory> getProviderFactories(Class<? extends Provider> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getServerStartupTimestamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

}

package org.repeid.models;

import java.util.List;
import java.util.Set;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderEventManager;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public interface RepeidSessionFactory extends ProviderEventManager {

    RepeidSession create();

    Set<Spi> getSpis();

    <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz);

    <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id);

    List<ProviderFactory> getProviderFactories(Class<? extends Provider> clazz);

    long getServerStartupTimestamp();

    void close();

}
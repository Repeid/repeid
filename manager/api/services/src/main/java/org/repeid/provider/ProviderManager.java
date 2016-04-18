package org.repeid.provider;

import org.repeid.services.ServicesLogger;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class ProviderManager {

    private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    private List<ProviderLoader> providerLoaders = new LinkedList<ProviderLoader>();
    private Map<String, List<ProviderFactory>> cache = new HashMap<String, List<ProviderFactory>>();

    public ProviderManager(ClassLoader baseClassLoader, String... resources) {
        List<ProviderLoaderFactory> providerLoaderFactories = new LinkedList<ProviderLoaderFactory>();
        for (ProviderLoaderFactory providerLoaderFactory : ServiceLoader.load(ProviderLoaderFactory.class, getClass().getClassLoader())) {
            providerLoaderFactories.add(providerLoaderFactory);
        }

        logger.debugv("Provider loaders {0}", providerLoaderFactories);

        providerLoaders.add(new DefaultProviderLoader(baseClassLoader));

        if (resources != null) {
            for (String r : resources) {
                String type = r.substring(0, r.indexOf(':'));
                String resource = r.substring(r.indexOf(':') + 1, r.length());

                boolean found = false;
                for (ProviderLoaderFactory providerLoaderFactory : providerLoaderFactories) {
                    if (providerLoaderFactory.supports(type)) {
                        providerLoaders.add(providerLoaderFactory.create(baseClassLoader, resource));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new RuntimeException("Provider loader for " + r + " not found");
                }
            }
        }
    }

    public synchronized List<ProviderFactory> load(Spi spi) {
        List<ProviderFactory> factories = cache.get(spi.getName());
        if (factories == null) {
            factories = new LinkedList<ProviderFactory>();
            IdentityHashMap factoryClasses = new IdentityHashMap();
            for (ProviderLoader loader : providerLoaders) {
                List<ProviderFactory> f = loader.load(spi);
                if (f != null) {
                    for (ProviderFactory pf: f) {
                        // make sure there are no duplicates
                        if (!factoryClasses.containsKey(pf.getClass())) {
                            factories.add(pf);
                            factoryClasses.put(pf.getClass(), pf);
                        }
                    }
                }
            }
        }
        return factories;
    }

    public synchronized ProviderFactory load(Spi spi, String providerId) {
        for (ProviderFactory f : load(spi)) {
            if (f.getId().equals(providerId)) {
                return f;
            }
        }
        return null;
    }

}

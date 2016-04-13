package org.repeid.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.keycloak.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class DefaultRepeidSessionFactory implements RepeidSessionFactory {

    private Map<Class<? extends Provider>, String> provider = new HashMap<Class<? extends Provider>, String>();
    private Map<Class<? extends Provider>, Map<String, ProviderFactory>> factoriesMap = new HashMap<Class<? extends Provider>, Map<String, ProviderFactory>>();

    /**
	 * Load providers factory classes and init them
	 */
	public void init() {
		for (Spi spi : ServiceLoader.load(Spi.class)) {		    
			Map<String, ProviderFactory> factories = new HashMap<String, ProviderFactory>();
			factoriesMap.put(spi.getProviderClass(), factories);			

			String provider = Config.getProvider(spi.getName());
			if (provider != null) {
				this.provider.put(spi.getProviderClass(), provider);

				ProviderFactory factory = loadProviderFactory(spi, provider);
				Config.Scope scope = Config.scope(spi.getName(), provider);
				factory.init(scope);

				factories.put(factory.getId(), factory);
				
				System.out.println("****************************Map<Class<? extends Provider>, String> provider**********************");
				for (Map.Entry<Class<? extends Provider>, String> entry : this.provider.entrySet()) {
				    System.out.println(entry.getKey() + " / " + entry.getValue());
				}
				System.out.println("********************END****************************");
				
				System.out.println("***************************Map<Class<? extends Provider>, Map<String, ProviderFactory>> factoriesMap***********");
				for (Map.Entry<Class<? extends Provider>, Map<String, ProviderFactory>> entry : factoriesMap.entrySet()) {
	                System.out.println("<" + entry.getKey() + ">");
	               
	                for (Map.Entry<String, ProviderFactory> entry2 :  entry.getValue().entrySet()) {
	                    System.out.println("     <" + entry2.getKey() + " / " + entry2.getValue() + ">");
	                }
	                System.out.println("");
	            }
				System.out.println("********************END****************************");
			} else {
				for (ProviderFactory factory : ServiceLoader.load(spi.getProviderFactoryClass())) {
					Config.Scope scope = Config.scope(spi.getName(), factory.getId());
					factory.init(scope);

					factories.put(factory.getId(), factory);
				}

				if (factories.size() == 1) {
					provider = factories.values().iterator().next().getId();
					this.provider.put(spi.getProviderClass(), provider);
				}
			}
						
		}
	}

    private ProviderFactory loadProviderFactory(Spi spi, String id) {
        for (ProviderFactory factory : ServiceLoader.load(spi.getProviderFactoryClass())) {
            if (factory.getId().equals(id)) {
                return factory;
            }
        }
        throw new RuntimeException("Failed to find provider " + id + " for " + spi.getName());
    }

    public RepeidSession create() {
        return new DefaultRepeidSession(this);
    }

    <T extends Provider> String getDefaultProvider(Class<T> clazz) {
        return provider.get(clazz);
    }

    @Override
    public <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz) {
        return getProviderFactory(clazz, provider.get(clazz));
    }

    @Override
    public <T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id) {
        return factoriesMap.get(clazz).get(id);
    }

    @Override
    public List<ProviderFactory> getProviderFactories(Class<? extends Provider> clazz) {
        List<ProviderFactory> list = new LinkedList<ProviderFactory>();
        if (factoriesMap == null)
            return list;
        Map<String, ProviderFactory> providerFactoryMap = factoriesMap.get(clazz);
        if (providerFactoryMap == null)
            return list;
        list.addAll(providerFactoryMap.values());
        return list;
    }

    <T extends Provider> Set<String> getAllProviderIds(Class<T> clazz) {
        Set<String> ids = new HashSet<String>();
        for (ProviderFactory f : factoriesMap.get(clazz).values()) {
            ids.add(f.getId());
        }
        return ids;
    }

    public void close() {
        for (Map<String, ProviderFactory> factories : factoriesMap.values()) {
            for (ProviderFactory factory : factories.values()) {
                factory.close();
            }
        }
    }

}

package org.repeid.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.repeid.models.LegalPersonProvider;
import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidContext;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.RepeidTransactionManager;
import org.repeid.models.cache.CacheLegalPersonProvider;
import org.repeid.models.cache.CacheNaturalPersonProvider;
import org.repeid.models.cache.CacheOrganizationProvider;
import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;

public class DefaultRepeidSession implements RepeidSession {

    private final DefaultRepeidSessionFactory factory;
    private final Map<Integer, Provider> providers = new HashMap<Integer, Provider>();
    private final List<Provider> closable = new LinkedList<Provider>();
    private final DefaultRepeidTransactionManager transactionManager;

    private OrganizationProvider organizationProvider;
    private NaturalPersonProvider naturalPersonProvider;
    private LegalPersonProvider legalPersonProvider;

    private RepeidContext context;

    public DefaultRepeidSession(DefaultRepeidSessionFactory factory) {
        this.factory = factory;
        this.transactionManager = new DefaultRepeidTransactionManager();
        context = new DefaultRepeidContext(this);
    }

    @Override
    public RepeidContext getContext() {
        return context;
    }

    /**
     * @return RepeidTransactionManager return transaction manager associate to
     * the session.
     */
    @Override
    public RepeidTransactionManager getTransaction() {
        return transactionManager;
    }

    /**
     * @param provider added to invoke close method of the provider on
     *                 DefaultRepeidSession close.
     */
    @Override
    public void enlistForClose(Provider provider) {
        closable.add(provider);
    }

    /**
     * @param clazz return provider for the given class. If the provider don't
     *              exists then this method create it and save on local variable.
     */
    @Override
    public <T extends Provider> T getProvider(Class<T> clazz) {
        Integer hash = clazz.hashCode();
        T provider = (T) providers.get(hash);
        if (provider == null) {
            ProviderFactory<T> providerFactory = factory.getProviderFactory(clazz);
            if (providerFactory != null) {
                provider = providerFactory.create(this);
                providers.put(hash, provider);
            }
        }
        return provider;
    }

    /**
     * @param clazz
     * @param id    return provider for the given class. If the provider don't
     *              exists then this method create it and save on local variable.
     */
    @Override
    public <T extends Provider> T getProvider(Class<T> clazz, String id) {
        Integer hash = clazz.hashCode() + id.hashCode();
        T provider = (T) providers.get(hash);
        if (provider == null) {
            ProviderFactory<T> providerFactory = factory.getProviderFactory(clazz, id);
            if (providerFactory != null) {
                provider = providerFactory.create(this);
                providers.put(hash, provider);
            }
        }
        return provider;
    }

    /**
     * @param clazz return all the provider's id for the given class.
     */
    @Override
    public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
        return factory.getAllProviderIds(clazz);
    }

    /**
     * @param clazz return all the provider's class for the given class.
     */
    @Override
    public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
        Set<T> providers = new HashSet<T>();
        for (String id : listProviderIds(clazz)) {
            providers.add(getProvider(clazz, id));
        }
        return providers;
    }

    /**
     * @return the current RepeidSessionFactory.
     */
    @Override
    public RepeidSessionFactory getRepeidSessionFactory() {
        return factory;
    }

    /**
     * @return OrganizationProvider
     */
    @Override
    public OrganizationProvider organizations() {
        if (organizationProvider == null) {
            organizationProvider = getOrganizationProvider();
        }
        return organizationProvider;
    }

    private OrganizationProvider getOrganizationProvider() {
        CacheOrganizationProvider cache = getProvider(CacheOrganizationProvider.class);
        if (cache != null) {
            return cache;
        } else {
            return getProvider(OrganizationProvider.class);
        }
    }   

    @Override
    public NaturalPersonProvider naturalPersons() {
        if (naturalPersonProvider == null) {
            naturalPersonProvider = getNaturalPersonProvider();
        }
        return naturalPersonProvider;
    }

    private NaturalPersonProvider getNaturalPersonProvider() {
        CacheNaturalPersonProvider cache = getProvider(CacheNaturalPersonProvider.class);
        if (cache != null) {
            return cache;
        } else {
            return getProvider(NaturalPersonProvider.class);
        }
    }

    @Override
    public LegalPersonProvider legalPersons() {
        if (legalPersonProvider == null) {
            legalPersonProvider = getLegalPersonProvider();
        }
        return legalPersonProvider;
    }

    private LegalPersonProvider getLegalPersonProvider() {
        CacheLegalPersonProvider cache = getProvider(CacheLegalPersonProvider.class);
        if (cache != null) {
            return cache;
        } else {
            return getProvider(LegalPersonProvider.class);
        }
    }

    /**
     * This method is invoked on destroy this method.
     */
    @Override
    public void close() {
        for (Provider p : providers.values()) {
            try {
                p.close();
            } catch (Exception e) {
            }
        }
        for (Provider p : closable) {
            try {
                p.close();
            } catch (Exception e) {
            }
        }
    }

}
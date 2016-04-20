package org.repeid.provider;

public class DefaultProviderLoaderFactory implements ProviderLoaderFactory {

    @Override
    public boolean supports(String type) {
        return false;
    }

    @Override
    public ProviderLoader create(ClassLoader baseClassLoader, String resource) {
        return new DefaultProviderLoader(baseClassLoader);
    }

}

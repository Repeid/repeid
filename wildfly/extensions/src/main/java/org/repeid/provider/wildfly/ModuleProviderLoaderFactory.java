package org.repeid.provider.wildfly;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.repeid.provider.DefaultProviderLoader;
import org.repeid.provider.ProviderLoader;
import org.repeid.provider.ProviderLoaderFactory;

public class ModuleProviderLoaderFactory implements ProviderLoaderFactory {

    @Override
    public boolean supports(String type) {
        return "module".equals(type);
    }

    @Override
    public ProviderLoader create(ClassLoader baseClassLoader, String resource) {
        try {
            Module module = Module.getContextModuleLoader().loadModule(ModuleIdentifier.fromString(resource));
            ModuleClassLoader classLoader = module.getClassLoader();
            return new DefaultProviderLoader(classLoader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

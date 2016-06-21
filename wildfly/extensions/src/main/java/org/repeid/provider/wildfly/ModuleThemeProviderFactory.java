package org.repeid.provider.wildfly;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.repeid.Config;
import org.repeid.theme.JarThemeProviderFactory;

public class ModuleThemeProviderFactory extends JarThemeProviderFactory {

    @Override
    public void init(Config.Scope config) {
        String[] modules = config.getArray("modules");
        if (modules != null) {
            try {
                for (String moduleSpec : modules) {
                    Module module = Module.getContextModuleLoader().loadModule(ModuleIdentifier.fromString(moduleSpec));
                    ModuleClassLoader classLoader = module.getClassLoader();
                    loadThemes(classLoader, classLoader.getResourceAsStream(REPEID_THEMES_JSON));
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load themes", e);
            }
        }
    }

    @Override
    public String getId() {
        return "module";
    }

}

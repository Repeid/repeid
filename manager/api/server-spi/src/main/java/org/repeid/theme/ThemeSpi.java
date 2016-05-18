package org.repeid.theme;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class ThemeSpi implements Spi {

	@Override
	public boolean isInternal() {
		return true;
	}

	@Override
	public String getName() {
		return "theme";
	}

	@Override
	public Class<? extends Provider> getProviderClass() {
		return ThemeProvider.class;
	}

	@Override
	public Class<? extends ProviderFactory> getProviderFactoryClass() {
		return ThemeProviderFactory.class;
	}
}

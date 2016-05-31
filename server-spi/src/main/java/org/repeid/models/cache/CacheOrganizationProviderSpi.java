package org.repeid.models.cache;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class CacheOrganizationProviderSpi implements Spi {

	@Override
	public boolean isInternal() {
		return true;
	}

	@Override
	public String getName() {
		return "organizationCache";
	}

	@Override
	public Class<? extends Provider> getProviderClass() {
		return CacheOrganizationProvider.class;
	}

	@Override
	public Class<? extends ProviderFactory> getProviderFactoryClass() {
		return CacheOrganizationProviderFactory.class;
	}
}

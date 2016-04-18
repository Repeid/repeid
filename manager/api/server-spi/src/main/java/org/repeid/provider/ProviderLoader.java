package org.repeid.provider;

import java.util.List;

public interface ProviderLoader {

	/**
	 * @return all ProviderFactory from a Spi object
	 */
	List<ProviderFactory> load(Spi spi);

}

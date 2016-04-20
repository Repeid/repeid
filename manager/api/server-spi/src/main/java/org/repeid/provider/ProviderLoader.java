package org.repeid.provider;

import java.util.List;

public interface ProviderLoader {

    List<ProviderFactory> load(Spi spi);

}

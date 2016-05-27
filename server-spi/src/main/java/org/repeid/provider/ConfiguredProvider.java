package org.repeid.provider;

import java.util.List;

public interface ConfiguredProvider {

    String getHelpText();

    List<ProviderConfigProperty> getConfigProperties();

}

package org.repeid.timer;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class TimerSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "timer";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return TimerProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return TimerProviderFactory.class;
    }

}

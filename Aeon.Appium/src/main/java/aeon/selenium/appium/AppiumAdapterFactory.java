package aeon.selenium.appium;

import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.SeleniumAdapterFactory;
import ro.fortsoft.pf4j.Extension;

import java.io.IOException;

/**
 * The driver factory for Web.
 */
@Extension
public final class AppiumAdapterFactory extends SeleniumAdapterFactory {

    @Override
    public IAdapter createAdapter(Configuration configuration) {
        return create((AppiumConfiguration) configuration);
    }

    @Override
    public Configuration getConfiguration() throws IOException, IllegalAccessException {
        Configuration configuration = new AppiumConfiguration();
        configuration.loadConfiguration();
        return configuration;
    }

    @Override
    public Capability getProvidedCapability() {
        return Capability.MOBILE;
    }
}


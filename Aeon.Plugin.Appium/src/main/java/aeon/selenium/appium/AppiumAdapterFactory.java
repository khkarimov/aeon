package aeon.selenium.appium;

import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.SeleniumAdapterFactory;
import org.pf4j.Extension;

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

    /**
     * Factory method that creates an Appium adapter for Aeon.Core.Mobile.
     * @param configuration The configuration of the adapter.
     * @return The created Appium adapter is returned.
     */
    public IAdapter create(AppiumConfiguration configuration) {
        prepare(configuration);

        return new AppiumAdapter(driver, javaScriptFlowExecutor, moveMouseToOrigin, browserType, fallbackBrowserSize, isRemote, seleniumHubUrl, seleniumLogsDirectory, loggingPreferences);
    }
}


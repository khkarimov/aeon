package echo.core.framework_abstraction.adapters;

import echo.core.common.Capability;
import echo.core.test_abstraction.product.Configuration;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.net.MalformedURLException;

public interface IAdapterExtension extends ExtensionPoint {

    IAdapter createAdapter(Configuration configuration);

    Configuration getConfiguration() throws MalformedURLException;

    Capability getProvidedCapability();
}

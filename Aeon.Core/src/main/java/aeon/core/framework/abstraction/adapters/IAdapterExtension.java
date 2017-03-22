package aeon.core.framework.abstraction.adapters;

import aeon.core.common.Capability;
import aeon.core.testabstraction.product.configuration;
import ro.fortsoft.pf4j.ExtensionPoint;

public interface IAdapterExtension extends ExtensionPoint {

    IAdapter createAdapter(configuration configuration);

    configuration getConfiguration();

    Capability getProvidedCapability();
}

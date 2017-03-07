package aeon.core.framework_abstraction.adapters;

import aeon.core.common.Capability;
import aeon.core.test_abstraction.product.Configuration;
import ro.fortsoft.pf4j.ExtensionPoint;

public interface IAdapterExtension extends ExtensionPoint {

    IAdapter createAdapter(Configuration configuration);

    Configuration getConfiguration();

    Capability getProvidedCapability();
}

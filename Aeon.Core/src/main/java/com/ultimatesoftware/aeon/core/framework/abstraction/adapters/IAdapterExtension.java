package com.ultimatesoftware.aeon.core.framework.abstraction.adapters;

import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.pf4j.ExtensionPoint;

import java.io.IOException;

/**
 * The interface for the Adapter Extension class.
 */
public interface IAdapterExtension extends ExtensionPoint {

    /**
     * Creates a new adapter using the indicated {@link Configuration}.
     *
     * @param configuration The Configuration file to be used.
     * @return The new Adapter object;
     */
    IAdapter createAdapter(Configuration configuration);

    /**
     * Gets the {@link Configuration} object for the adapter.
     *
     * @return The configuration object for the adapter.
     * @throws IOException            In case of error reading the .properties files.
     * @throws IllegalAccessException If case of error getting the Keys
     */
    Configuration getConfiguration() throws IOException, IllegalAccessException;

    /**
     * Gets the {@link Capability} provided.
     *
     * @return The capability.
     */
    Capability getProvidedCapability();
}

package aeon.platform;

import aeon.core.extensions.IProductTypeExtension;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Aeon;
import dagger.Module;
import dagger.Provides;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.util.List;
import java.util.function.Supplier;

/**
 * Module to provide Adapter Extensions Supplier.
 */
@Module
public class AeonPlatformModule {

    /**
     * Provides the Adapter Extensions Supplier.
     * @return Adapter Extensions Supplier
     */
    @Provides
    public Supplier<List<IAdapterExtension>> provideAdapterExtensionsSupplier() {
        return () -> Aeon.getExtensions(IAdapterExtension.class);
    }

    /**
     * Provides the Product Type Extensions Supplier.
     * @return Product Type Extensions Supplier
     */
    @Provides
    public Supplier<List<IProductTypeExtension>> provideProductTypeExtensionsSupplier() {
        PluginManager pluginManager = new DefaultPluginManager();
        return () -> pluginManager.getExtensions(IProductTypeExtension.class);
    }
}

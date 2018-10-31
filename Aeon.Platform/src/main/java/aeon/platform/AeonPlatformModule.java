package aeon.platform;

import aeon.core.extensions.IProductTypeExtension;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Aeon;
import dagger.Module;
import dagger.Provides;

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
        return () -> Aeon.getExtensions(IProductTypeExtension.class);
    }


}

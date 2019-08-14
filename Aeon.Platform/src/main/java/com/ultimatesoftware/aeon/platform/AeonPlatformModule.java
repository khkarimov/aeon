package com.ultimatesoftware.aeon.platform;

import com.ultimatesoftware.aeon.core.extensions.IProductTypeExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import dagger.Module;
import dagger.Provides;

import java.util.List;
import java.util.function.Supplier;

/**
 * Module to provide Adapter Extensions Supplier.
 */
@Module
class AeonPlatformModule {

    /**
     * Provides the Adapter Extensions Supplier.
     *
     * @return Adapter Extensions Supplier
     */
    @Provides
    Supplier<List<IAdapterExtension>> provideAdapterExtensionsSupplier() {
        return () -> Aeon.getExtensions(IAdapterExtension.class);
    }

    /**
     * Provides the Product Type Extensions Supplier.
     *
     * @return Product Type Extensions Supplier
     */
    @Provides
    Supplier<List<IProductTypeExtension>> provideProductTypeExtensionsSupplier() {
        return () -> Aeon.getExtensions(IProductTypeExtension.class);
    }
}

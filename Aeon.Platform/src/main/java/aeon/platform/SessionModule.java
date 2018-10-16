package aeon.platform;

import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Aeon;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import java.util.List;
import java.util.function.Supplier;

@Module
public class SessionModule {

    @Provides
    public Supplier<List<IAdapterExtension>> provideAdapterExtensionsSupplier() {
//    public Supplier<List<IAdapterExtension>> getAdapterExtensionsSupplier() {
        return () -> Aeon.getExtensions(IAdapterExtension.class);
    }
}

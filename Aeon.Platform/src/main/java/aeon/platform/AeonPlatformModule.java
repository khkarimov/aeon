package aeon.platform;

import aeon.core.extensions.IProductTypeExtension;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Aeon;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import java.util.List;
import java.util.function.Supplier;

/**
 * Module to provide Adapter Extensions Supplier.
 */
@Module
class AeonPlatformModule {

    /**
     * Provides the Adapter Extensions Supplier.
     * @return Adapter Extensions Supplier
     */
    @Provides
    Supplier<List<IAdapterExtension>> provideAdapterExtensionsSupplier() {
        return () -> Aeon.getExtensions(IAdapterExtension.class);
    }

    /**
     * Provides the Product Type Extensions Supplier.
     * @return Product Type Extensions Supplier
     */
    @Provides
    Supplier<List<IProductTypeExtension>> provideProductTypeExtensionsSupplier() {
        return () -> Aeon.getExtensions(IProductTypeExtension.class);
    }

    /**
     * Provides the Channel.
     * @return Channel
     */
    @Provides
    @Singleton
    Channel provideChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();

            return connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

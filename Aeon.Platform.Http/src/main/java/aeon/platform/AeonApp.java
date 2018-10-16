package aeon.platform;

import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.testabstraction.product.Aeon;
import aeon.platform.services.SessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Supplier;

/**
 * Launches browser.
 */
@SpringBootApplication
public class AeonApp {

    @Bean
    public SessionService getSessionService() {
        SessionComponent sessionComponent = DaggerSessionComponent.create();
        return sessionComponent.buildSessionService();
    }

//    @Bean
//    public SessionService getSessionService() {
//        return new SessionService(getAdapterExtensionsSupplier());
//    }

//    /**
//     * Returns a supplier for IAdapterExtensions.
//     * @return Adapter extensions supplier
//     */
//    @Bean
//    public Supplier<List<IAdapterExtension>> getAdapterExtensionsSupplier() {
//        return () -> Aeon.getExtensions(IAdapterExtension.class);
//    }

    /**
     * Main method of the application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AeonApp.class, args);
    }
}

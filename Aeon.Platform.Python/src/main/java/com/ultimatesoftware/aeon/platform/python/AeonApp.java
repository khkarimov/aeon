package com.ultimatesoftware.aeon.platform.python;

import com.ultimatesoftware.aeon.platform.DaggerAeonPlatformComponent;
import com.ultimatesoftware.aeon.platform.factories.SessionFactory;
import py4j.GatewayServer;

/**
 * Aeon Platform application.
 */
public class AeonApp {

    /**
     * Main method of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        SessionFactory sessionFactory = DaggerAeonPlatformComponent.create().buildSessionFactory();

        GatewayServer gatewayServer = new GatewayServer(sessionFactory);
        gatewayServer.start();

        System.out.println("\n\nGateway server started.\n");
    }
}

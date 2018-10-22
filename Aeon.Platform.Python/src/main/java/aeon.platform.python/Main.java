package aeon.platform.python;

import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.factories.SessionFactory;
import py4j.GatewayServer;

public class Main {

    public static void main(String args[]) {

        SessionFactory sessionFactory = DaggerAeonPlatformComponent.create().buildSessionFactory();

        GatewayServer gatewayServer = new GatewayServer(sessionFactory);
        gatewayServer.start();

    }
}

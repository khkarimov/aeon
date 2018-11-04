package aeon.cloud.services;

import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CloudFoundryOperationsFactoryTests {

    private CloudFoundryOperationsFactory cloudFoundryOperationsFactory;

    @Before
    public void setUp() {
        cloudFoundryOperationsFactory = new CloudFoundryOperationsFactory();
    }

    @Test
    public void getCloudFoundryOperationsTest() {
        DefaultCloudFoundryOperations cloudFoundryOperations = (DefaultCloudFoundryOperations) cloudFoundryOperationsFactory
                .getCloudFoundryOperations(
                        "apiHost",
                        "username",
                        "password",
                        "organization",
                        "space");

        assertNotNull(cloudFoundryOperations);
        assertEquals("organization", cloudFoundryOperations.getOrganization());
        assertEquals("space", cloudFoundryOperations.getSpace());
    }
}

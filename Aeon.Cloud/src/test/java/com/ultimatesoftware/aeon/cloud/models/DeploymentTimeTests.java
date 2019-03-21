package com.ultimatesoftware.aeon.cloud.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeploymentTimeTests {

    @Test
    public void deploymentTimeTest() {
        DeploymentTime deploymentTime = new DeploymentTime(4);

        assertEquals(4, deploymentTime.getTime());
    }
}

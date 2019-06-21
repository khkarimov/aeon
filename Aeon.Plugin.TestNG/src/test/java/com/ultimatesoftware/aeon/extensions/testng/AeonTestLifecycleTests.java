package com.ultimatesoftware.aeon.extensions.testng;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AeonTestLifecycleTests {

    private TestNgListener testNgListener;

    @BeforeEach
    public void setup() {
        testNgListener = new TestNgListener();
    }

    @Test
    public void test() {

        // Arrange

        // Act

        // Assert
        assertNotNull(testNgListener);
    }
}

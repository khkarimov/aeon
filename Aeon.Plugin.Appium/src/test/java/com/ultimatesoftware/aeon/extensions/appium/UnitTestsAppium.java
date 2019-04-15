package com.ultimatesoftware.aeon.extensions.appium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestsAppium {
    @Test
    public void dummyTestPass() {
        boolean dummyVariable = true;
        assert (dummyVariable);
        assertEquals(true, dummyVariable);
    }
}

package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OsCheckTests {

    private String actualOsProperty;

    @BeforeEach
    void setup() {
        actualOsProperty = System.getProperty("os.name");
        OsCheck.detectedOS = null;
    }

    @AfterEach
    void teardown() {
        OsCheck.detectedOS = null;
        System.setProperty("os.name", this.actualOsProperty);
    }

    @Test
    void getOperatingSystemType_mac_returnsMacOs() {

        // Arrange
        System.setProperty("os.name", "something mac something");

        // Act
        OsCheck.OSType os = OsCheck.getOperatingSystemType();

        // Assert
        assertEquals(OsCheck.OSType.MAC_OS, os);
    }

    @Test
    void getOperatingSystemType_darwin_returnsMacOs() {

        // Arrange
        System.setProperty("os.name", "something darwin something");

        // Act
        OsCheck.OSType os = OsCheck.getOperatingSystemType();

        // Assert
        assertEquals(OsCheck.OSType.MAC_OS, os);
    }

    @Test
    void getOperatingSystemType_win_returnsWindows() {

        // Arrange
        System.setProperty("os.name", "something win something");

        // Act
        OsCheck.OSType os = OsCheck.getOperatingSystemType();

        // Assert
        assertEquals(OsCheck.OSType.WINDOWS, os);
    }

    @Test
    void getOperatingSystemType_nux_returnsLinux() {

        // Arrange
        System.setProperty("os.name", "something nux something");

        // Act
        OsCheck.OSType os = OsCheck.getOperatingSystemType();

        // Assert
        assertEquals(OsCheck.OSType.LINUX, os);
    }

    @Test
    void getOperatingSystemType_unknown_returnsOther() {

        // Arrange
        System.setProperty("os.name", "something else");

        // Act
        OsCheck.OSType os = OsCheck.getOperatingSystemType();

        // Assert
        assertEquals(OsCheck.OSType.OTHER, os);
    }
}

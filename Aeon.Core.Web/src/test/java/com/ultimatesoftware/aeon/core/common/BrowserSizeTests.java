package com.ultimatesoftware.aeon.core.common;

import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrowserSizeTests {


    @Test
    void findBrowserSize_whenGivenValidString_returnsBrowserSize() {
        // Arrange

        // Act
        BrowserSize browserSize = BrowserSize.findBrowserSize("TabletLandscape");

        // Assert
        assertEquals(BrowserSize.TABLET_LANDSCAPE, browserSize);
    }

    @Test
    void findBrowserSize_whenGivenInvalidString_returnsDefaultBrowserSize() {
        // Arrange

        // Act
        BrowserSize browserSize = BrowserSize.findBrowserSize("NotAValidBrowserSize");

        // Assert
        assertEquals(BrowserSize.FULL_HD, browserSize);
    }
}

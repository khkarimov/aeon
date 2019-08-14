package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.UnableToCreateURLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URLUtilTests {

    @Test
    void createURL_happyPath_returnsURL() {

        //Arrange

        // Act
        URL result = URLUtil.createURL("http://example.com/");

        //Assert
        assertEquals("http://example.com/", result.toString());
    }

    @Test
    void createURL_badURL_throwsError() {

        //Arrange

        // Act
        Executable action = () -> URLUtil.createURL("this is a malformed url");

        //Assert
        assertThrows(UnableToCreateURLException.class, action);
    }
}

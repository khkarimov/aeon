package com.ultimatesoftware.aeon.extensions.selenium.jquery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InjectJQueryScripterTests {

    @Mock
    private IJavaScriptFinalizer successor;

    @Test
    void constructor_nullSuccessor_throwsException() {

        // Arrange
        this.successor = null;

        // Act
        Executable action = () -> new InjectJQueryScripter(this.successor);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("successor", exception.getMessage());
    }

    @Test
    void prepare_javaScriptIsNull_throwsException() {

        // Arrange
        InjectJQueryScripter injectJQueryScripter = new InjectJQueryScripter(this.successor);

        // Act
        Executable action = () -> injectJQueryScripter.prepare(null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("javaScript", exception.getMessage());
    }

    @Test
    void prepare_javaScriptDoesNotRequireJQuery_preparesJavaScript() {

        // Arrange
        when(this.successor.prepare("my-javascript")).thenReturn("prepared-my-javascript");
        InjectJQueryScripter injectJQueryScripter = new InjectJQueryScripter(this.successor);

        // Act
        String javascript = injectJQueryScripter.prepare("my-javascript");

        // Assert
        assertEquals("var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function aeonFunction(){prepared-my-javascript}aeonCallback(aeonFunction(aeonNonCallbackArguments));", javascript);
    }

    @Test
    void prepare_javaScriptDoesRequiresJQuery_preparesJavaScriptWithJQuery() {

        // Arrange
        when(this.successor.prepare("$('my-javascript')")).thenReturn("prepared-my-javascript");
        InjectJQueryScripter injectJQueryScripter = new InjectJQueryScripter(this.successor);

        // Act
        String javascript = injectJQueryScripter.prepare("$('my-javascript')");

        // Assert
        assertEquals("var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);" +
                "function aeonFunction(){prepared-my-javascript}var aeonJQueryLoaded=false;function aeonOnFinishJQueryLoading(){if(aeonJQueryLoaded)return;aeonJQueryLoaded=true;var $=window.jquery;aeonCallback(aeonFunction(aeonNonCallbackArguments))}" +
                "if(!window.jquery){var script=document.createElement('script');script.type='text/javascript';script.src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js';script.onreadystatechange=function(){if(this.readyState=='loaded'||this.readyState=='complete')aeonOnFinishJQueryLoading()};" +
                "script.onload=aeonOnFinishJQueryLoading;document.getElementsByTagName('head')[0].appendChild(script)}else aeonCallback(aeonFunction(aeonNonCallbackArguments));", javascript);
    }
}

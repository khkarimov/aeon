package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by uriel k on 5/23/17.
 */
public class GetAllCookiesCommandTests {
    
        private GetAllCookiesCommand getAllCookiesCommand;

        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();

        @Mock
        private IWebDriver driver;

        @Before
        public void setup() { getAllCookiesCommand = new GetAllCookiesCommand(); }




        @Test
        public void GetAllCookiesCommand(){

            // Act
            getAllCookiesCommand.commandDelegate(driver);

            // Verify
            verify(driver, times(1)).getAllCookies();
        }
    }


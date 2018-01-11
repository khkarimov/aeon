package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DeleteAllCookiesCommandTests {
    private DeleteAllCookiesCommand deleteAllCookies;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        deleteAllCookies = new DeleteAllCookiesCommand();
    }

    @Test
    public void commandDelegateDeleteAllCookiesCommand() {
        //Arrange

        //Act
        deleteAllCookies.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).deleteAllCookies();
    }


}

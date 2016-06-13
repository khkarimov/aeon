package test_abstraction.models;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.CommandExecutionFacade;
import echo.core.command_execution.ICommandExecutionFacade;
import echo.core.command_execution.consumers.interfaces.IDelegateRunnerFactory;
import echo.core.common.logging.ILog;
import echo.core.common.logging.Log;
import echo.core.framework_abstraction.adapters.IWebAdapter;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.test_abstraction.models.Browser;
import echo.core.test_abstraction.product.Parameters;
import org.junit.*;
import org.mockito.Mockito;

//import static org.mockito.Mockito.*;
/**
 * Created by Administrator on 6/10/2016.
 */

 //region  SR - This object may not need unit tests, but instead only integration tests. Will remove if unnecessary
public class BrowserObjectTest {
    private ICommandExecutionFacade commandExecutionFacade;
    private Parameters parameters;
    private AutomationInfo info;
    private IDriver driver;
    private Browser browser;
    private IWebAdapter webAdapter;
    private ILog log;
    private IDelegateRunnerFactory delegateRunnerFactory;

    @Before
    public void SetUp(){

        /*
        delegateRunnerFactory = Mockito.mock(IDelegateRunnerFactory.class);
        parameters = Mockito.mock(Parameters.class);
        webAdapter = Mockito.mock(IWebAdapter.class);
        driver = Mockito.mock(IDriver.class);
        log = new Log();
        commandExecutionFacade = new CommandExecutionFacade(delegateRunnerFactory);
        info = new AutomationInfo(parameters, driver, webAdapter, log);
        info.setCommandExecutionFacade(commandExecutionFacade);
        browser = new Browser(info);
        */
        delegateRunnerFactory = Mockito.mock(IDelegateRunnerFactory.class);
        commandExecutionFacade = new CommandExecutionFacade(Mockito.mock(IDelegateRunnerFactory.class));
        info = Mockito.mock(AutomationInfo.class);
        // Setup the method to mock
        Mockito.when(info.getCommandExecutionFacade()).thenReturn(commandExecutionFacade);
    }

    @Test
    public void ScrollToTop_ExecutesScrollToTopCommand(){
        // Arrange.


        // Act.
        browser.ScrollToTop();

        // Assert.
    }
//endregion
}

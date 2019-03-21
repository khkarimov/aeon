package com.ultimatesoftware.aeon.core.command.execution;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlCommandWithReturn;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import com.ultimatesoftware.aeon.core.common.helpers.AjaxWaiter;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebCommandExecutionFacadeTests {

    private WebCommandExecutionFacade webCommandExecutionFacade;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private IDelegateRunnerFactory delegateRunnerFactory;

    @Mock
    private AjaxWaiter ajaxWaiter;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AutomationInfo automationInfo;

    @BeforeEach
    void setUp() {
        webCommandExecutionFacade = new WebCommandExecutionFacade(delegateRunnerFactory, ajaxWaiter);
    }

    @Test
    void getAjaxWaiterTimeoutMillis() {

        //Arrange
        when(ajaxWaiter.getTimeout()).thenReturn(100L);

        //Act

        //Assert
        assertEquals(webCommandExecutionFacade.getAjaxWaiterTimeoutMillis(), 100L);
        verify(ajaxWaiter, times(1)).getTimeout();
    }

    @Test
    void setAjaxWaiterTimeout() {

        //Arrange

        //Act
        webCommandExecutionFacade.setAjaxWaiterTimeout(100L);

        //Assert
        verify(ajaxWaiter, times(1)).setTimeout(100L);
    }

    @Test
    void execute_CommandIsNull_throwsException() {

        //Arrange
        Command command = null;

        //Act

        //Assert
        assertThrows(IllegalArgumentException.class, () -> webCommandExecutionFacade.execute(automationInfo, command));
    }

    @Test
    void execute_CommandWithReturnIsNull_throwsException() {

        //Arrange
        CommandWithReturn commandWithReturn = null;

        //Act

        //Assert
        assertThrows(IllegalArgumentException.class, () -> webCommandExecutionFacade.execute(automationInfo, commandWithReturn));
    }

    @Test
    void execute_Command_waitForAsyncSkipped() {

        //Arrange
        Command command = mock(Command.class);

        //Act
        webCommandExecutionFacade.execute(automationInfo, command);

        //Assert
        verify(ajaxWaiter, times(0)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

    @Test
    void execute_CommandWithReturn_waitForAsyncSkipped() {

        //Arrange
        CommandWithReturn commandWithReturn = mock(CommandWithReturn.class);

        //Act
        webCommandExecutionFacade.execute(automationInfo, commandWithReturn);

        //Assert
        verify(ajaxWaiter, times(0)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

    @Test
    void execute_CommandInstanceOfWebControlCommand_waitForAsyncCalled() {

        //Arrange
        WebControlCommand command = mock(WebControlCommand.class);
        when(automationInfo.getConfiguration().getBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true)).thenReturn(true);

        //Act
        webCommandExecutionFacade.execute(automationInfo, command);

        //Assert
        verify(ajaxWaiter, times(1)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

    @Test
    void execute_CommandInstanceOfWebControlCommandWithReturn_waitForAsyncCalled() {

        //Arrange
        WebControlCommandWithReturn commandWithReturn = mock(WebControlCommandWithReturn.class);
        when(automationInfo.getConfiguration().getBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true)).thenReturn(true);

        //Act
        webCommandExecutionFacade.execute(automationInfo, commandWithReturn);

        //Assert
        verify(ajaxWaiter, times(1)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

    @Test
    void execute_WaitForAjaxFalse_waitForAsyncSkipped() {

        //Arrange
        WebControlCommand command = mock(WebControlCommand.class);
        when(automationInfo.getConfiguration().getBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true)).thenReturn(false);

        //Act
        webCommandExecutionFacade.execute(automationInfo, command);

        //Assert
        verify(ajaxWaiter, times(0)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

    @Test
    void execute_WaitForAjaxFalseAndWebControlCommandWithReturn_waitForAsyncSkipped() {

        //Arrange
        WebControlCommandWithReturn commandWithReturn = mock(WebControlCommandWithReturn.class);
        when(automationInfo.getConfiguration().getBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true)).thenReturn(false);

        //Act
        webCommandExecutionFacade.execute(automationInfo, commandWithReturn);

        //Assert
        verify(ajaxWaiter, times(0)).waitForAsync();
        verify(delegateRunnerFactory, times(1)).createInstance(automationInfo);
    }

}

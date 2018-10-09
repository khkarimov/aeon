package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BrowserTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private BrowserController browserController;

    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;
    @Mock private Map<ObjectId, AutomationInfo> sessionTableMock;

    @Mock private CreateSessionBody bodyMock;
    @Mock private ObjectId sessionIdMock;

    @Mock private BrowserHelper browserHelperMock;
    @Mock private CommandHelper commandHelperMock;

    @Before
    public void setUp() {
        browserController = new BrowserController(browserHelperMock, commandHelperMock);
        browserController.setSessionTable(sessionTableMock);
    }

    @Test
    public void launchBrowser() throws Exception {
        when(browserHelperMock.createSessionId()).thenReturn(sessionIdMock);
        when(browserHelperMock.setUpAutomationInfo(bodyMock)).thenReturn(automationInfoMock);
        when(browserHelperMock.setUpCommandExecutionFacade(automationInfoMock)).thenReturn(commandExecutionFacadeMock);
        when(sessionTableMock.put(sessionIdMock, automationInfoMock)).thenReturn(automationInfoMock);

        ResponseEntity response = browserController.createSession(bodyMock);

        verify(browserHelperMock, times(1)).setUpAutomationInfo(bodyMock);
        verify(browserHelperMock, times(1)).setUpCommandExecutionFacade(automationInfoMock);
        verify(sessionTableMock, times(1)).put(sessionIdMock, automationInfoMock);

        Assert.assertEquals(sessionIdMock.toString(), response.getBody());
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}

package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.mobile.NativeClickCommand;
import aeon.core.command.execution.commands.mobile.NativeSelectCommand;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class NativeSelectTest {
    @Mock
    AutomationInfo automationInfoMock;
    @Mock
    IByWeb iByWebMock;
    @Mock
    IByWeb iByWebSwitchMechanismMock;
    @Mock
    ICommandExecutionFacade iCommandExecutionFacadeMock;
    @Captor
    ArgumentCaptor<NativeSelectCommand> selectCommandCaptor;

    private NativeSelect nativeSelect;

    @BeforeEach
    void setUp() {
        nativeSelect = new NativeSelect(automationInfoMock, iByWebMock, iByWebSwitchMechanismMock);

        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(iCommandExecutionFacadeMock);
    }

    @Test
    void set_happyPath_setsValue() {
        //Arrange

        //Act
        nativeSelect.set("TEXT", "value");

        //Assert
        verify(iCommandExecutionFacadeMock, times(1)).execute(eq(automationInfoMock), any(NativeClickCommand.class));

        verify(iCommandExecutionFacadeMock, times(2)).execute(eq(automationInfoMock), selectCommandCaptor.capture());
        assertEquals(MobileSelectOption.TEXT, selectCommandCaptor.getValue().getSelectOption());
        assertEquals("value", selectCommandCaptor.getValue().getValue());
    }

    @Test
    void select_happyPath_setsValue() {
        //Arrange

        //Act
        nativeSelect.select("TEXT", "value");

        //Assert
        verify(iCommandExecutionFacadeMock, times(1)).execute(eq(automationInfoMock), selectCommandCaptor.capture());
        assertEquals(MobileSelectOption.TEXT, selectCommandCaptor.getValue().getSelectOption());
        assertEquals("value", selectCommandCaptor.getValue().getValue());
    }
}
package com.ultimatesoftware.aeon.core.testabstraction.elements.mobile;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeClickCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.NativeSelectCommand;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.MobileSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
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
    @Captor
    ArgumentCaptor<NativeClickCommand> clickCommancCaptor;

    private NativeSelect nativeSelect;

    @Test
    void set_happyPath_setsValue() {
        //Arrange
        nativeSelect = new NativeSelect(automationInfoMock, iByWebMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(iCommandExecutionFacadeMock);

        //Act
        nativeSelect.set("TEXT", "value");

        //Assert
        verify(iCommandExecutionFacadeMock, times(2)).execute(eq(automationInfoMock), selectCommandCaptor.capture());
        assertEquals(MobileSelectOption.TEXT, selectCommandCaptor.getValue().getSelectOption());
        assertEquals("value", selectCommandCaptor.getValue().getValue());

        verify(iCommandExecutionFacadeMock, times(2)).execute(eq(automationInfoMock), clickCommancCaptor.capture());
        assertNull(((WebCommandInitializer) clickCommancCaptor.getAllValues().get(0).getCommandInitializer()).getSwitchMechanism());
    }

    @Test
    void select_happyPath_setsValue() {
        //Arrange
        nativeSelect = new NativeSelect(automationInfoMock, iByWebMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(iCommandExecutionFacadeMock);

        //Act
        nativeSelect.select("TEXT", "value");

        //Assert
        verify(iCommandExecutionFacadeMock, times(1)).execute(eq(automationInfoMock), selectCommandCaptor.capture());
        assertEquals(MobileSelectOption.TEXT, selectCommandCaptor.getValue().getSelectOption());
        assertEquals("value", selectCommandCaptor.getValue().getValue());
    }

    @Test
    void nativeSelect_complexConstructor_switchMechanismIsSet() {
        //Arrange
        nativeSelect = new NativeSelect(automationInfoMock, iByWebMock, iByWebSwitchMechanismMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(iCommandExecutionFacadeMock);

        //Act
        nativeSelect.set("TEXT", "value");

        //Assert
        verify(iCommandExecutionFacadeMock, times(2)).execute(eq(automationInfoMock), clickCommancCaptor.capture());
        assertNotNull(((WebCommandInitializer) clickCommancCaptor.getAllValues().get(0).getCommandInitializer()).getSwitchMechanism());
        assertEquals(IByWeb[].class, ((WebCommandInitializer) clickCommancCaptor.getAllValues().get(0).getCommandInitializer()).getSwitchMechanism().getClass());
    }
}

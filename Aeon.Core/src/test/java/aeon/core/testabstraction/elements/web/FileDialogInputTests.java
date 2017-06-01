package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FileDialogInputTests {

    // FileDialogInput with two argument constructor
    private FileDialogInput fileDialogInput1;
    // FileDialogInput three argument constructor
    private FileDialogInput fileDialogInput2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AutomationInfo info1;
    @Mock
    private AutomationInfo info2;

    @Mock
    private IBy selector;

    @Mock
    private Iterable<IBy> switchMechanism;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Before
    public void setup(){
        info1 = new AutomationInfo(configuration, driver, adapter);
        info1.setCommandExecutionFacade(commandExecutionFacade);
        fileDialogInput1 = new FileDialogInput(info1, selector);

        info2 = new AutomationInfo(configuration, driver, adapter);
        info2.setCommandExecutionFacade(commandExecutionFacade);
        fileDialogInput2 = new FileDialogInput(info2, selector, switchMechanism);
    }

    @Test
    public void openFileDialogueExecute() {
        //Act
        fileDialogInput1.openFileDialog();
        fileDialogInput2.openFileDialog();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(OpenFileDialogCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(OpenFileDialogCommand.class));
    }

    @Test
    public void selectFileDialogueExecute() {
        //Act
        fileDialogInput1.selectFileDialog(Mockito.eq(any(String.class)));
        fileDialogInput2.selectFileDialog(Mockito.eq(any(String.class)));

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(SelectFileDialogCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(SelectFileDialogCommand.class));
    }

    @Test
    public void uploadFileExecute() {
        //Act
        fileDialogInput1.uploadFileDialog(Mockito.eq(any(String.class)));
        fileDialogInput2.uploadFileDialog(Mockito.eq(any(String.class)));

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(UploadFileDialogCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(UploadFileDialogCommand.class));
    }
}
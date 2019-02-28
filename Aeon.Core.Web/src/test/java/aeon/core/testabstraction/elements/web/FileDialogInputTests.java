package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.SelectFileCommand;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class FileDialogInputTests {

    // FileDialogInput with two argument constructor
    private FileDialogInput fileDialogInput1;
    // FileDialogInput three argument constructor
    private FileDialogInput fileDialogInput2;
    private String filePath;

    @Mock
    private AutomationInfo info1;
    @Mock
    private AutomationInfo info2;

    @Mock
    private IByWeb selector;

    @Mock
    private IByWeb switchMechanism;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @BeforeEach
    public void setup() {
        info1 = new AutomationInfo(configuration, driver, adapter);
        info1.setCommandExecutionFacade(commandExecutionFacade);
        fileDialogInput1 = new FileDialogInput(info1, selector);
        filePath = "newFile.txt";

        info2 = new AutomationInfo(configuration, driver, adapter);
        info2.setCommandExecutionFacade(commandExecutionFacade);
        fileDialogInput2 = new FileDialogInput(info2, selector, switchMechanism);
    }

    @Test
    public void selectFileExecute() {
        //Act
        fileDialogInput1.selectFile(filePath);
        fileDialogInput2.selectFile(filePath);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(SelectFileCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(SelectFileCommand.class));
    }
}

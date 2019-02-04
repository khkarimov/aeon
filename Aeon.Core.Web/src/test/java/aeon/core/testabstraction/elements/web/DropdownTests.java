package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.CompareType;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class DropdownTests {

    private Dropdown dropdown1;
    private Dropdown dropdown2;

    private AutomationInfo info1;
    private AutomationInfo info2;
    private WebSelectOption webSelectOption = WebSelectOption.Text;
    private String[] messages = {"test", "message"};
    private String dummy = "dummy";
    private int dummyInt = 3;
    private CompareType compareType = CompareType.DescendingByText;

    @Mock
    private IByWeb selector;
    @Mock
    private IByWeb switchMechanism0;
    @Mock
    private IDriver driver;
    @Mock
    private IAdapter adapter;
    @Mock
    private Configuration configuration;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade1;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade2;

    @BeforeEach
    public void setup() {

        info1 = new AutomationInfo(configuration, driver, adapter);
        info2 = new AutomationInfo(configuration, driver, adapter);

        info1.setCommandExecutionFacade(commandExecutionFacade1);
        info2.setCommandExecutionFacade(commandExecutionFacade2);

        dropdown1 = new Dropdown(info1, selector);
        dropdown2 = new Dropdown(info2, selector, switchMechanism0);
    }

    @Test
    public void testHasOptions_ThreeParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasOptions(messages, dummy, webSelectOption);
        dropdown2.hasOptions(messages, dummy, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasOptionsCommand.class));
    }

    @Test
    public void testHasOptions_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasOptions(messages, webSelectOption);
        dropdown2.hasOptions(messages, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasOptionsCommand.class));
    }

    @Test
    public void testHasOptionsInOrder_ThreeParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasOptionsInOrder(messages, dummy, webSelectOption);
        dropdown2.hasOptionsInOrder(messages, dummy, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasOptionsInOrderCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasOptionsInOrderCommand.class));
    }

    @Test
    public void testHasOptionsInOrder_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasOptionsInOrder(messages, webSelectOption);
        dropdown2.hasOptionsInOrder(messages, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasOptionsInOrderCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasOptionsInOrderCommand.class));
    }

    @Test
    public void testDoesNotHaveOptions_ThreeParams_VerifyExecute() {
        //Tests each select object
        dropdown1.doesNotHaveOptions(messages, dummy, webSelectOption);
        dropdown2.doesNotHaveOptions(messages, dummy, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(DoesNotHaveOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(DoesNotHaveOptionsCommand.class));
    }

    @Test
    public void testDoesNotHaveOptions_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.doesNotHaveOptions(messages, webSelectOption);
        dropdown2.doesNotHaveOptions(messages, webSelectOption);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(DoesNotHaveOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(DoesNotHaveOptionsCommand.class));
    }

    @Test
    public void testHasNumberOfOptions_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasNumberOfOptions(dummyInt, dummy);
        dropdown2.hasNumberOfOptions(dummyInt, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasNumberOfOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasNumberOfOptionsCommand.class));
    }

    @Test
    public void testHasNumberOfOptions_OneParam_VerifyExecute() {
        //Tests each select object
        dropdown1.hasNumberOfOptions(dummyInt);
        dropdown2.hasNumberOfOptions(dummyInt);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasNumberOfOptionsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasNumberOfOptionsCommand.class));
    }

    @Test
    public void testHasAllOptionsInOrder_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.hasAllOptionsInOrder(compareType, dummy);
        dropdown2.hasAllOptionsInOrder(compareType, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasAllOptionsInOrderCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasAllOptionsInOrderCommand.class));
    }

    @Test
    public void testHasAllOptionsInOrder_OneParam_VerifyExecute() {
        //Tests each select object
        dropdown1.hasAllOptionsInOrder(compareType);
        dropdown2.hasAllOptionsInOrder(compareType);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(HasAllOptionsInOrderCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(HasAllOptionsInOrderCommand.class));
    }

    @Test
    public void testSet_VerifyExecute() {
        //Tests each select object
        dropdown1.set(webSelectOption, dummy);
        dropdown2.set(webSelectOption, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(SetCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(SetCommand.class));
    }

    @Test
    public void testIsLike_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.isLike(dummy, dummy);
        dropdown2.isLike(dummy, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsLikeCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsLikeCommand.class));
    }

    @Test
    public void testIsLike_OneParam_VerifyExecute() {
        //Tests each select object
        dropdown1.isLike(dummy);
        dropdown2.isLike(dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsLikeCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsLikeCommand.class));
    }

    @Test
    public void testIsNotLike_OneParam_VerifyExecute() {
        //Tests each select object
        dropdown1.isNotLike(dummy);
        dropdown2.isNotLike(dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void testIsNotLike_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.isNotLike(dummy, dummy);
        dropdown2.isNotLike(dummy, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void testIs_OneParam_VerifyExecute() {
        //Tests each select object
        dropdown1.is(dummy);
        dropdown2.is(dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsCommand.class));
    }

    @Test
    public void testIs_TwoParams_VerifyExecute() {
        //Tests each select object
        dropdown1.is(dummy, dummy);
        dropdown2.is(dummy, dummy);

        verify(commandExecutionFacade1, times(1)).execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade2, times(1)).execute(eq(info2), any(IsCommand.class));
    }
}
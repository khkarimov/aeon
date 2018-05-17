package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebElementTests {

    // WebElement with one argument constructor
    private WebElement webElement1;
    // WebElement with three argument constructor
    private WebElement webElement2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AutomationInfo info1;
    @Mock
    private AutomationInfo info2;

    @Mock
    private IByWeb selector;

    @Mock
    private Iterable<IByWeb> switchMechanism;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    private DateTime dateTime = new DateTime();

    private Period period = new Period();

    private String dummy = "dummy";

    private String[] messages = new String[]{"test", "message"};


    @BeforeEach
    public void setup(){
        info1 = new AutomationInfo(configuration, driver, adapter);
        info1.setCommandExecutionFacade(commandExecutionFacade);
        webElement1 = new FileDialogInput(info1, selector);

        info2 = new AutomationInfo(configuration, driver, adapter);
        info2.setCommandExecutionFacade(commandExecutionFacade);
        webElement2 = new FileDialogInput(info2, selector, switchMechanism);
    }

    @Test
    public void blurExecute() {
        //Act
        webElement1.blur();
        webElement2.blur();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(BlurCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(BlurCommand.class));
    }

    @Test
    public void clickAndHoldExecute() {
        //Act
        webElement1.clickAndHold(1);
        webElement2.clickAndHold(1);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(ClickAndHoldCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(ClickAndHoldCommand.class));
    }

    @Test
    public void clickExecute() {
        //Act
        webElement1.click();
        webElement2.click();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(ClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(ClickCommand.class));
    }

    @Test
    public void clickAllElementsExecute() {
        //Act
        webElement1.clickAllElements();
        webElement2.clickAllElements();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(ClickAllElementsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(ClickAllElementsCommand.class));
    }

    @Test
    public void doubleClickExecute() {
        //Act
        webElement1.doubleClick();
        webElement2.doubleClick();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DoubleClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DoubleClickCommand.class));
    }

    @Test
    public void isDisabledExecute() {
        //Act
        webElement1.isDisabled();
        webElement2.isDisabled();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DisabledCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DisabledCommand.class));
    }

    @Test
    public void dragAndDropExecute_NullArgument() {
        //Act
        webElement1.dragAndDrop(null);
        webElement2.dragAndDrop(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DragAndDropCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DragAndDropCommand.class));
    }

    @Test
    public void dragAndDropExecute_NonNullArgument() {
        //Act
        webElement1.dragAndDrop(dummy);
        webElement2.dragAndDrop(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DragAndDropCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DragAndDropCommand.class));
    }

    @Test
    public void isEnabled() {
        //Act
        webElement1.isEnabled();
        webElement2.isEnabled();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(EnabledCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(EnabledCommand.class));
    }

    @Test
    public void existsExecute() {
        //Act
        webElement1.exists();
        webElement2.exists();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(ExistsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(ExistsCommand.class));
    }

    @Test
    public void notExistsExecute() {
        //Act
        webElement1.notExists();
        webElement2.notExists();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(NotExistsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(NotExistsCommand.class));
    }

    @Test
    public void getElementAttributeExecute_NullArgument() {
        //Act
        webElement1.getElementAttribute(null);
        webElement2.getElementAttribute(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(GetElementAttributeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(GetElementAttributeCommand.class));
    }

    @Test
    public void getElementAttributeExecute_NonNullArgument() {
        //Act
        webElement1.getElementAttribute(dummy);
        webElement2.getElementAttribute(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(GetElementAttributeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(GetElementAttributeCommand.class));
    }

    @Test
    public void rightClickExecute() {
        //Act
        webElement1.rightClick();
        webElement2.rightClick();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(RightClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(RightClickCommand.class));
    }

    @Test
    public void mouseOutExecute() {
        //Act
        webElement1.mouseOut();
        webElement2.mouseOut();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(MouseOutCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(MouseOutCommand.class));
    }

    @Test
    public void mouseOverExecute() {
        //Act
        webElement1.mouseOut();
        webElement2.mouseOut();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(MouseOutCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(MouseOutCommand.class));
    }


    @Test
    public void isExecute_ValueArgNull() {
        //Act
        webElement1.is(null);
        webElement2.is(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsCommand.class));
    }

    @Test
    public void isExecute_ValueArgNonNull() {
        //Act
        webElement1.is(dummy);
        webElement2.is(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsCommand.class));
    }

    @Test
    public void isExecute_ValueArgNull_AttributeArgNull() {
        //Act
        webElement1.is(null, null);
        webElement2.is(null, null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsCommand.class));
    }

    @Test
    public void isExecute_ValueArgNotNull_AttributeArgNotNull() {
        //Act
        webElement1.is(dummy, dummy);
        webElement2.is(dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsCommand.class));
    }

    @Test
    public void isNotLike_ValueArgNull() {
        //Act
        webElement1.isNotLike(null);
        webElement2.isNotLike(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void isNotLikeExecute_ValueArgNonNull() {
        //Act
        webElement1.isNotLike(dummy);
        webElement2.isNotLike(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void isNotLikeExecute_ValueArgNull_AttributeArgNull() {
        //Act
        webElement1.isNotLike(null, null);
        webElement2.isNotLike(null, null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void isNotLikeExecute_ValueArgNotNull_AttributeArgNotNull() {
        //Act
        webElement1.isNotLike(dummy, dummy);
        webElement2.isNotLike(dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    public void hasExecute_TwoArgs() {
        //Act
        webElement1.has(messages, dummy);
        webElement2.has(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasCommand.class));
    }

    @Test
    public void hasExecute_ThreeArgs() {
        //Act
        webElement1.has(messages, dummy, dummy);
        webElement2.has(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasCommand.class));
    }

    @Test
    public void hasLikeExecute_TwoArgs() {
        //Act
        webElement1.hasLike(messages, dummy);
        webElement2.hasLike(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasLikeCommand.class));
    }

    @Test
    public void hasLikeExecute_ThreeArgs() {
        //Act
        webElement1.hasLike(messages, dummy, dummy);
        webElement2.hasLike(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasLikeCommand.class));
    }

    @Test
    public void doesNotHaveExecute_TwoArgs() {
        //Act
        webElement1.doesNotHave(messages, dummy);
        webElement2.doesNotHave(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DoesNotHaveCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DoesNotHaveCommand.class));
    }

    @Test
    public void doesNotHaveExecute_ThreeArgs() {
        //Act
        webElement1.doesNotHave(messages, dummy, dummy);
        webElement2.doesNotHave(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DoesNotHaveCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DoesNotHaveCommand.class));
    }

    @Test
    public void doesNotHaveLikeExecute_TwoArgs() {
        //Act
        webElement1.doesNotHaveLike(messages, dummy);
        webElement2.doesNotHaveLike(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DoesNotHaveLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DoesNotHaveLikeCommand.class));
    }

    @Test
    public void doesNotHaveLikeExecute_ThreeArgs() {
        //Act
        webElement1.doesNotHaveLike(messages, dummy, dummy);
        webElement2.doesNotHaveLike(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DoesNotHaveLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DoesNotHaveLikeCommand.class));
    }

    @Test
    public void hasOnlyExecute_TwoArgs() {
        //Act
        webElement1.hasOnly(messages, dummy);
        webElement2.hasOnly(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasOnlyCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasOnlyCommand.class));
    }

    @Test
    public void hasOnlyExecute_ThreeArgs() {
        //Act
        webElement1.hasOnly(messages, dummy, dummy);
        webElement2.hasOnly(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(HasOnlyCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(HasOnlyCommand.class));
    }

    @Test
    public void setBodyValueByJavaScriptExecute() {
        //Act
        webElement1.setBodyValueByJavaScript(dummy);
        webElement2.setBodyValueByJavaScript(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(SetBodyValueByJavaScriptCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(SetBodyValueByJavaScriptCommand.class));
    }

    @Test
    public void setTextByJavaScriptExecute() {
        //Act
        webElement1.setTextByJavaScript(dummy);
        webElement2.setTextByJavaScript(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(SetTextByJavaScriptCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(SetTextByJavaScriptCommand.class));
    }

    @Test
    public void visibleExecute() {
        //Act
        webElement1.visible();
        webElement2.visible();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(VisibleCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(VisibleCommand.class));
    }

    @Test
    public void notVisibleExecute() {
        //Act
        webElement1.notVisible();
        webElement2.notVisible();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(NotVisibleCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(NotVisibleCommand.class));
    }

    @Test
    public void datesApproximatelyEqualExecute() {
        //Act
        webElement1.datesApproximatelyEqual(dummy, dateTime, period);
        webElement2.datesApproximatelyEqual(dummy, dateTime, period);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DatesApproximatelyEqualCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DatesApproximatelyEqualCommand.class));
    }

    @Test
    public void getClientRectsExecute() {
        //Act
        webElement1.getClientRects();
        webElement2.getClientRects();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(GetClientRectsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(GetClientRectsCommand.class));
    }

    @Test
    public void pressKeyboardKeyExecution() {
        //Act
        webElement1.datesApproximatelyEqual(dummy, dateTime, period);
        webElement2.datesApproximatelyEqual(dummy, dateTime, period);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(DatesApproximatelyEqualCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(DatesApproximatelyEqualCommand.class));
    }
}

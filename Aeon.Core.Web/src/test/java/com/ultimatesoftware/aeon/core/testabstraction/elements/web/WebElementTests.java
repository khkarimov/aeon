package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.*;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.Period;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebElementTests {

    // WebElement with one argument constructor
    private WebElement webElement1;
    // WebElement with three argument constructor
    private WebElement webElement2;

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

    @Captor
    private ArgumentCaptor<HasAttributeCommand> hasAttributeCommandArgumentCaptor;

    @Captor
    private ArgumentCaptor<DoesNotHaveAttributeCommand> doesNotHaveAttributeCommandArgumentCaptor;

    private LocalDate dateTime = LocalDate.now();

    private Period period = Period.of(1, 1, 1);

    private String dummy = "dummy";

    private KeyboardKey key = KeyboardKey.META;

    private String[] messages = new String[]{"test", "message"};


    @BeforeEach
    void setup() {
        info1 = new AutomationInfo(configuration, driver, adapter);
        info1.setCommandExecutionFacade(commandExecutionFacade);
        webElement1 = new FileDialogInput(info1, selector);

        info2 = new AutomationInfo(configuration, driver, adapter);
        info2.setCommandExecutionFacade(commandExecutionFacade);
        webElement2 = new FileDialogInput(info2, selector, switchMechanism);
    }

    @Test
    void blur_executesCommand() {
        //Act
        webElement1.blur();
        webElement2.blur();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(BlurCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(BlurCommand.class));
    }

    @Test
    void clickAndHold_executesCommand() {
        //Act
        webElement1.clickAndHold(1);
        webElement2.clickAndHold(1);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(ClickAndHoldCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(ClickAndHoldCommand.class));
    }

    @Test
    void click_executesCommand() {
        //Act
        webElement1.click();
        webElement2.click();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(ClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(ClickCommand.class));
    }

    @Test
    void clickAllElements_executesCommand() {
        //Act
        webElement1.clickAllElements();
        webElement2.clickAllElements();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(ClickAllElementsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(ClickAllElementsCommand.class));
    }

    @Test
    void doubleClick_executesCommand() {
        //Act
        webElement1.doubleClick();
        webElement2.doubleClick();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DoubleClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DoubleClickCommand.class));
    }

    @Test
    void isDisabled_executesCommand() {
        //Act
        webElement1.isDisabled();
        webElement2.isDisabled();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DisabledCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DisabledCommand.class));
    }

    @Test
    void dragAndDrop_whenDropTargetIsNotNull_executesCommand() {
        //Act
        webElement1.dragAndDrop(webElement2);
        webElement2.dragAndDrop(webElement1);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DragAndDropCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DragAndDropCommand.class));
    }

    @Test
    void isEnabled_executesCommand() {
        //Act
        webElement1.isEnabled();
        webElement2.isEnabled();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(EnabledCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(EnabledCommand.class));
    }

    @Test
    void exists_executesCommand() {
        //Act
        webElement1.exists();
        webElement2.exists();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(ExistsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(ExistsCommand.class));
    }

    @Test
    void notExists_executesCommand() {
        //Act
        webElement1.notExists();
        webElement2.notExists();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(NotExistsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(NotExistsCommand.class));
    }

    @Test
    void rightClick_executesCommand() {
        //Act
        webElement1.rightClick();
        webElement2.rightClick();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(RightClickCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(RightClickCommand.class));
    }

    @Test
    void mouseOut_executesCommand() {
        //Act
        webElement1.mouseOut();
        webElement2.mouseOut();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(MouseOutCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(MouseOutCommand.class));
    }

    @Test
    void mouseOver_executesCommand() {
        //Act
        webElement1.mouseOver();
        webElement2.mouseOver();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(MouseOverCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(MouseOverCommand.class));
    }


    @Test
    void is_whenValueIsNull_executesCommand() {
        //Act
        webElement1.is(null);
        webElement2.is(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsCommand.class));
    }

    @Test
    void is_whenValueIsNotNull_executesCommand() {
        //Act
        webElement1.is(dummy);
        webElement2.is(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsCommand.class));
    }

    @Test
    void is_executesCommand_ValueArgNull_AttributeArgNull() {
        //Act
        webElement1.is(null, null);
        webElement2.is(null, null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsCommand.class));
    }

    @Test
    void is_whenValueIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.is(dummy, dummy);
        webElement2.is(dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsCommand.class));
    }

    @Test
    void isLike_whenValueIsNull_executesCommand() {
        //Act
        webElement1.isLike(null);
        webElement2.isLike(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsLikeCommand.class));
    }

    @Test
    void isLike_whenValueIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.isLike(dummy, dummy);
        webElement2.isLike(dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsLikeCommand.class));
    }

    @Test
    void isLike_executesCommand() {
        //Act
        webElement1.isLike(dummy);
        webElement2.isLike(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsLikeCommand.class));
    }

    @Test
    void isNotLike_whenValueIsNull_executesCommand() {
        //Act
        webElement1.isNotLike(null);
        webElement2.isNotLike(null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    void isNotLike_whenValueIsNotNull_executesCommand() {
        //Act
        webElement1.isNotLike(dummy);
        webElement2.isNotLike(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    void isNotLike_whenValueIsNull_andAttributeIsNull_executesCommand() {
        //Act
        webElement1.isNotLike(null, null);
        webElement2.isNotLike(null, null);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    void isNotLike_whenValueIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.isNotLike(dummy, dummy);
        webElement2.isNotLike(dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(IsNotLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(IsNotLikeCommand.class));
    }

    @Test
    void has_whenMessagesIsNotNull_andChildSelectorIsNotNull_executesCommand() {
        //Act
        webElement1.has(messages, dummy);
        webElement2.has(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasCommand.class));
    }

    @Test
    void has_whenMessagesIsNotNull_andChildSelectorIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.has(messages, dummy, dummy);
        webElement2.has(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasCommand.class));
    }

    @Test
    void hasLike_whenMessagesIsNotNull_andChildSelectorIsNotNull_executesCommand() {
        //Act
        webElement1.hasLike(messages, dummy);
        webElement2.hasLike(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasLikeCommand.class));
    }

    @Test
    void hasLike_whenMessagesIsNotNull_andChildSelectorIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.hasLike(messages, dummy, dummy);
        webElement2.hasLike(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasLikeCommand.class));
    }

    @Test
    void doesNotHave_whenMessagesIsNotNull_andChildSelectorIsNotNull_executesCommand() {
        //Act
        webElement1.doesNotHave(messages, dummy);
        webElement2.doesNotHave(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DoesNotHaveCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DoesNotHaveCommand.class));
    }

    @Test
    void doesNotHave_whenMessagesIsNotNull_andChildSelectorIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.doesNotHave(messages, dummy, dummy);
        webElement2.doesNotHave(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DoesNotHaveCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DoesNotHaveCommand.class));
    }

    @Test
    void doesNotHaveLike_whenMessagesIsNotNull_andChildSelectorIsNotNull_executesCommand() {
        //Act
        webElement1.doesNotHaveLike(messages, dummy);
        webElement2.doesNotHaveLike(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DoesNotHaveLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DoesNotHaveLikeCommand.class));
    }

    @Test
    void doesNotHaveLike_whenMessagesIsNotNull_andChildSelectorIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.doesNotHaveLike(messages, dummy, dummy);
        webElement2.doesNotHaveLike(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DoesNotHaveLikeCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DoesNotHaveLikeCommand.class));
    }

    @Test
    void hasOnly_whenMessagesIsNotNull_andChildSelectorIsNotNull_executesCommand() {
        //Act
        webElement1.hasOnly(messages, dummy);
        webElement2.hasOnly(messages, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasOnlyCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasOnlyCommand.class));
    }

    @Test
    void hasOnly_whenMessagesIsNotNull_andChildSelectorIsNotNull_andAttributeIsNotNull_executesCommand() {
        //Act
        webElement1.hasOnly(messages, dummy, dummy);
        webElement2.hasOnly(messages, dummy, dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(HasOnlyCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(HasOnlyCommand.class));
    }

    @Test
    void setDivValueByJavaScript_executesCommand() {
        //Act
        webElement1.setDivValueByJavaScript(dummy);
        webElement2.setDivValueByJavaScript(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(SetDivValueByJavaScriptCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(SetDivValueByJavaScriptCommand.class));
    }

    @Test
    void setBodyValueByJavaScript_executesCommand() {
        //Act
        webElement1.setBodyValueByJavaScript(dummy);
        webElement2.setBodyValueByJavaScript(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(SetBodyValueByJavaScriptCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(SetBodyValueByJavaScriptCommand.class));
    }

    @Test
    void setTextByJavaScript_executesCommand() {
        //Act
        webElement1.setTextByJavaScript(dummy);
        webElement2.setTextByJavaScript(dummy);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(SetTextByJavaScriptCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(SetTextByJavaScriptCommand.class));
    }

    @Test
    void visible_executesCommand() {
        //Act
        webElement1.visible();
        webElement2.visible();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(VisibleCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(VisibleCommand.class));
    }

    @Test
    void notVisible_executesCommand() {
        //Act
        webElement1.notVisible();
        webElement2.notVisible();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(NotVisibleCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(NotVisibleCommand.class));
    }

    @Test
    void datesApproximatelyEqual_executesCommand() {
        //Act
        webElement1.datesApproximatelyEqual(dummy, dateTime, period);
        webElement2.datesApproximatelyEqual(dummy, dateTime, period);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DatesApproximatelyEqualCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DatesApproximatelyEqualCommand.class));
    }

    @Test
    void datesEqual_invokesDatesApproximatelyEqualCommand() {
        //Act
        webElement1.datesEqual(dummy, dateTime);
        webElement2.datesEqual(dummy, dateTime);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(DatesApproximatelyEqualCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(DatesApproximatelyEqualCommand.class));
    }

    @Test
    void pressKeyboardKey_executesCommand() {
        //Act
        webElement1.pressKeyboardKey(key);
        webElement2.pressKeyboardKey(key);

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(eq(info1), any(PressKeyboardKeyCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(eq(info2), any(PressKeyboardKeyCommand.class));
    }

}

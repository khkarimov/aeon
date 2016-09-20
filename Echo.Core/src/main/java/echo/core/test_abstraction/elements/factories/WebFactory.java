package echo.core.test_abstraction.elements.factories;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.exceptions.UnsupportedElementException;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Element;
import echo.core.test_abstraction.elements.web.*;

import java.lang.reflect.Type;

/**
 * Created by RafaelT on 6/8/2016.
 */
public class WebFactory implements IElementFactory {
    private AutomationInfo info;

    public WebFactory(AutomationInfo info) {
        this.info = info;
    }

    public Element create(Type elementType, String selector) {
        if (elementType.equals(Button.class)) {
            return new Button(info, By.CssSelector(selector));
        } else if (elementType.equals(Select.class)) {
            return new Select(info, By.CssSelector(selector));
        } else if (elementType.equals(TextBox.class)) {
            return new TextBox(info, By.CssSelector(selector));
        } else if (elementType.equals(Link.class)) {
            return new Link(info, By.CssSelector(selector));
        } else if (elementType.equals(Label.class)) {
            return new Label(info, By.CssSelector(selector));
        } else if (elementType.equals(Image.class)) {
            return new Image(info, By.CssSelector(selector));
        } else if (elementType.equals(Checkbox.class)) {
            return new Checkbox(info, By.CssSelector(selector));
        } else if (elementType.equals(FileDialogInput.class)) {
            return new FileDialogInput(info, By.CssSelector(selector));
        } else if (elementType.equals(ListItem.class)) {
            return new ListItem(info, By.CssSelector(selector));
        } else if (elementType.equals(RadioButton.class)) {
            return new RadioButton(info, By.CssSelector(selector));
        } else {
            throw new UnsupportedElementException(elementType.getClass());
        }
    }
}

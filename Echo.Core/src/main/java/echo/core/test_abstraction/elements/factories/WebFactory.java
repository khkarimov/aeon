package echo.core.test_abstraction.elements.factories;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.exceptions.UnsupportedElementException;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.*;
import echo.core.test_abstraction.elements.web.*;

import java.lang.reflect.Type;

/**
 * Created by RafaelT on 6/8/2016.
 */
public class WebFactory implements IElementFactory {
    private AutomationInfo info;

    public WebFactory (AutomationInfo info) {
        this.info = info;
    }

    public Element create(Type elementType, String selector) {
        if (elementType.equals(Button.class)) {
            return new Button(info, By.CssSelector(selector));
        }
        else if (elementType.equals(Select.class)) {
            return new Select(info, By.CssSelector(selector));
        }
        else if(elementType.equals(TextBox.class)) {
            return new TextBox(info, By.CssSelector(selector));
        }
        else if (elementType.equals(Link.class)) {
            return new Link(info, By.CssSelector(selector));
        }
        else if (elementType.equals(Label.class)) {
            return new Label(By.CssSelector(selector));
        }
        else if (elementType.equals(Image.class)) {
            return new Image(info, By.CssSelector(selector));
        }
        else if (elementType.equals(Checkbox.class)) {
            return new Checkbox(info, By.CssSelector(selector));
        }
        else if (elementType.equals(FileDialogInput.class)) {
            return new FileDialogInput(info, By.CssSelector(selector));
        }
        else throw new UnsupportedElementException(elementType.getClass());
    }

    public static Button createButton(String selector, AutomationInfo info) {
        return (Button) (new WebFactory(info).create(Button.class, selector));
    }

    public static Select createSelect(String selector, AutomationInfo info) {
        return (Select) (new WebFactory(info).create(Select.class, selector));
    }

    public static TextBox createTextBox(String selector, AutomationInfo info) {
        return (TextBox) (new WebFactory(info).create(TextBox.class, selector));
    }

    public static Label createLabel(String selector, AutomationInfo info) {
        return (Label) (new WebFactory(info).create(Label.class, selector));
    }

    public static Link createLink(String selector, AutomationInfo info) {
        return (Link) (new WebFactory(info).create(Link.class, selector));
    }

    public static Image createImage(String selector, AutomationInfo info) {
        return (Image) (new WebFactory(info)).create(Image.class, selector);
    }

    public static Checkbox createCheckbox(String selector, AutomationInfo info) {
        return (Checkbox) (new WebFactory(info)).create(Checkbox.class, selector);
    }

    public static FileDialogInput createFileDialogInput (String selector, AutomationInfo info) {
        return (FileDialogInput) (new WebFactory(info)).create(FileDialogInput.class, selector);
    }
}
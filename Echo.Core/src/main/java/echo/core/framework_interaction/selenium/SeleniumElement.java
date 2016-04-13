package echo.core.framework_interaction.selenium;

import echo.core.common.logging.ILog;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.webdriver.IWebElementAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Provides methods available for a web element.
 */
public class SeleniumElement implements IWebElementAdapter {
    private static final int LONG_STRING_LENGTH = 50;
    private WebElement underlyingWebElement;
    private ILog log;

    /**
     * Initializes a new instance of the <see cref="SeleniumElement"/> class.
     *
     * @param seleniumWebElement Underlying web element.
     * @param log                Record actions.
     */
    public SeleniumElement(WebElement seleniumWebElement, ILog log) {
        underlyingWebElement = seleniumWebElement;
        this.log = log;
    }

    public final WebElement getUnderlyingWebElement() {
        return underlyingWebElement;
    }

    protected final ILog getLog() {
        return log;
    }

    /**
     * Gets a value indicating whether or not this element is displayed.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is displayed, false if otherwise.
     */
    public final boolean Displayed(UUID guid) {
        getLog().Trace(guid, "WebElement.get_Displayed();");
        boolean result = getUnderlyingWebElement().isDisplayed();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is enabled.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is enabled, false if otherwise.
     */
    public final boolean Enabled(UUID guid) {
        getLog().Trace(guid, "WebElement.get_Enabled();");
        boolean result = getUnderlyingWebElement().isEnabled();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is selected.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is displayed, false if otherwise.
     */
    public final boolean Selected(UUID guid) {
        getLog().Trace(guid, "WebElement.get_Selected();");
        boolean result = getUnderlyingWebElement().isSelected();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets the tag name of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the tag name of the web element.
     */
    public final String GetTagName(UUID guid) {
        getLog().Trace(guid, "WebElement.get_TagName();");
        String result = getUnderlyingWebElement().getTagName();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets the location of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the location of the web element.
     */
    public final echo.core.framework_interaction.Point GetLocation(UUID guid) {
        getLog().Trace(guid, "WebElement.get_Location();");
        org.openqa.selenium.Point result = getUnderlyingWebElement().getLocation();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return new echo.core.framework_interaction.Point(result.getX(), result.getY());
    }

    /**
     * Gets the innerText of this element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the text of the web element.
     */
    public final String GetText(UUID guid) {
        getLog().Trace(guid, "WebElement.get_Text();");
        String result = getUnderlyingWebElement().getText().trim();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Clears the content of this element.
     *
     * @param guid Uniquely identify the web element.
     */
    public final void Clear(UUID guid) {
        getLog().Trace(guid, "WebElement.Clear();");
        underlyingWebElement.clear();
    }

    /**
     * Finds the first web element using the given method.
     *
     * @param guid   Uniquely identify the web element.
     * @param findBy Findby used to find the web element.
     * @return Returns the web element.
     */
    public final IWebElementAdapter FindElement(UUID guid, IBy findBy) {
        if (findBy == null) {
            throw new IllegalArgumentException("findBy");
        }

        getLog().Trace(guid, String.format("WebElement.FindElement(By.CssSelector(%1$s));", findBy));
        WebElement seleniumElement = underlyingWebElement.findElement(By.cssSelector(findBy.toString()));

        return new SeleniumElement(seleniumElement, getLog());
    }

    /**
     * Finds all web elements using the given method.
     *
     * @param guid   Uniquely identify the web element.
     * @param findBy Findby used to find the web elements.
     * @return Returns a collection of web elements.
     */
    public final Collection<IWebElementAdapter> FindElements(UUID guid, IBy findBy) {
        if (findBy == null) {
            throw new IllegalArgumentException("findBy");
        }

        List<IWebElementAdapter> result = new ArrayList<IWebElementAdapter>();

        getLog().Trace(guid, String.format("WebElement.FindElements(By.CssSelector(%1$s));", findBy));

        for (WebElement seleniumElement : underlyingWebElement.findElements(By.cssSelector(findBy.toString()))) {
            result.add(new SeleniumElement(seleniumElement, getLog()));
        }

        return new ArrayList<>(result);
    }

    /**
     * Gets the value of the specified attribute for this element.
     *
     * @param guid          Uniquely identify the web element.
     * @param attributeName Attribute name of the web element.
     * @return Returns the attribute associated with the attribute name.
     */
    public final String GetAttribute(UUID guid, String attributeName) {
        if (attributeName == null) {
            throw new IllegalArgumentException("attributeName");
        }

        if (attributeName.toUpperCase().equals("INNERHTML")) {
            attributeName = "innerHTML";
        }

        getLog().Trace(guid, String.format("WebElement.GetAttribute(%1$s);", attributeName));

        return underlyingWebElement.getAttribute(attributeName);
    }

    /**
     * Gets the value of a CSS property of this element.
     *
     * @param guid         Uniquely identify the web element.
     * @param propertyName Property name of the web element.
     * @return Returns the CSS value of the property name.
     */
    public final String GetCssValue(UUID guid, String propertyName) {
        if (propertyName == null) {
            throw new IllegalArgumentException("propertyName");
        }

        getLog().Trace(guid, String.format("WebElement.GetCssValue(%1$s);", propertyName));

        return underlyingWebElement.getCssValue(propertyName);
    }

    /**
     * Simulates typing text into the element.
     *
     * @param guid Uniquely identify the web element.
     * @param text Text that will be typed into the element.
     */
    public final void SendKeys(UUID guid, String text) {
        if (text == null) {
            throw new IllegalArgumentException("text");
        }

        // The selenium webdriver has a problem sending left-parenthesis in firefox. Replacing all left-parenthesis with the keystroke shift-9 seams to fix the issue.
        if (text.contains("(") && text.length() > 1) {
            String[] newText = text.split("[(]", -1);
            for (String s : newText) {
                if (newText[newText.length - 1].equals(s)) {
                    this.SendKeys(UUID.randomUUID(), s);
                } else {
                    this.SendKeys(UUID.randomUUID(), s);
                    this.SendKeys(UUID.randomUUID(), Keys.SHIFT + "9" + Keys.SHIFT);
                }
            }

            return;
        }

        if (text.length() < LONG_STRING_LENGTH) {
            getLog().Trace(guid, String.format("WebElement.SendKeys(%1$s);", text));
            underlyingWebElement.sendKeys(text);
            return;
        }

        int numberOfSubstrings = text.length() / LONG_STRING_LENGTH;

        if (text.length() % LONG_STRING_LENGTH > 0) {
            ++numberOfSubstrings;
        }

        int startIndex = 0;
        for (int i = 0; i < numberOfSubstrings; i++) {
            int charactersLeft = text.length() - startIndex;
            String substring = text.substring(startIndex,
                    startIndex + ((charactersLeft > LONG_STRING_LENGTH) ? LONG_STRING_LENGTH : charactersLeft));

            getLog().Trace(guid, String.format("WebElement.SendKeys(%1$s);", substring));

            underlyingWebElement.sendKeys(substring);

            startIndex += LONG_STRING_LENGTH;
        }
    }

    /**
     * Simulates typing text into the element.
     *
     * @param guid Uniquely identify the web element.
     * @param key  Keyboard key that will be typed into the element.
     */
    public final void SendKeys(UUID guid, Keys key) {
        SendKeys(guid, key);
    }

    /**
     * Clicks this element.
     *
     * @param guid              Uniquely identify the web element.
     * @param moveMouseToOrigin The move Mouse To Origin.
     */
    public final void Click(UUID guid, boolean moveMouseToOrigin) {
        // TODO: Ensure this works in a grid instance
        if (moveMouseToOrigin) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(0, 0);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        getLog().Trace(guid, "WebElement.Click();");

        underlyingWebElement.click();
    }

    /**
     * Submits this element to the web server.
     *
     * @param guid Uniquely identify the web element.
     */
    public final void Submit(UUID guid) {
        getLog().Trace(guid, "WebElement.Submit();");
        underlyingWebElement.submit();
    }
}
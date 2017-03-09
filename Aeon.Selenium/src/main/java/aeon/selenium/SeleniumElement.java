package aeon.selenium;

import aeon.core.common.exceptions.IncorrectElementTagException;
import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Provides methods available for a web element.
 */
public class SeleniumElement extends WebControl {
    private static final int LONG_STRING_LENGTH = 50;
    private WebElement underlyingWebElement;
    private SeleniumSelectElement selectHelper;
    private static Logger log = LogManager.getLogger(SeleniumElement.class);

    /**
     * Initializes a new instance of the {@link SeleniumElement} class.
     *
     * @param seleniumWebElement Underlying web element.
     */
    public SeleniumElement(WebElement seleniumWebElement) {
        underlyingWebElement = seleniumWebElement;
        if (underlyingWebElement.getTagName().equalsIgnoreCase("select")) {
            selectHelper = new SeleniumSelectElement(new Select(underlyingWebElement));
        } else {
            selectHelper = null;
        }
    }

    public final WebElement getUnderlyingWebElement() {
        return underlyingWebElement;
    }

    /**
     * Gets a value indicating whether or not this element is displayed.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is displayed, false if otherwise.
     */
    public final boolean Displayed(UUID guid) {
        log.trace("WebElement.get_Displayed();");
        boolean result = getUnderlyingWebElement().isDisplayed();
        log.trace(String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is enabled.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is enabled, false if otherwise.
     */
    public final boolean Enabled(UUID guid) {
        log.trace("WebElement.get_Enabled();");
        boolean result = getUnderlyingWebElement().isEnabled();
        log.trace(String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is selected.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns true if the element is displayed, false if otherwise.
     */
    public final boolean Selected(UUID guid) {
        log.trace("WebElement.get_Selected();");
        boolean result = getUnderlyingWebElement().isSelected();
        log.trace(String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets the tag name of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the tag name of the web element.
     */
    public final String GetTagName(UUID guid) {
        log.trace("WebElement.get_TagName();");
        String result = getUnderlyingWebElement().getTagName();
        log.trace(String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets the location of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the location of the web element.
     */
    public final aeon.core.common.Point GetLocation(UUID guid) {
        log.trace("WebElement.get_Location();");
        org.openqa.selenium.Point result = getUnderlyingWebElement().getLocation();
        log.trace(String.format("Result: %1$s", result));
        return new aeon.core.common.Point(result.getX(), result.getY());
    }

    /**
     * Gets the innerText of this element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the text of the web element.
     */
    public final String GetText(UUID guid) {
        log.trace("WebElement.get_Text();");
        String result = getUnderlyingWebElement().getText().trim();
        log.trace(String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Clears the content of this element.
     *
     * @param guid Uniquely identify the web element.
     */
    public final void Clear(UUID guid) {
        log.trace("WebElement.Clear();");
        underlyingWebElement.clear();
    }

    /**
     * Finds the first web element using the given method.
     *
     * @param guid   Uniquely identify the web element.
     * @param findBy Findby used to find the web element.
     * @return Returns the web element.
     */
    public final WebControl FindElement(UUID guid, IBy findBy) {
        try {
            if (findBy == null) {
                throw new IllegalArgumentException("findBy");
            }

            log.trace(String.format("WebElement.FindElement(By.CssSelector(%1$s));", findBy));
            WebElement seleniumElement = underlyingWebElement.findElement(By.cssSelector(findBy.toString()));

            return new SeleniumElement(seleniumElement);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Finds element through its text or value.
     *
     * @param guid Uniquely identifiable id associated with this call.
     * @param by   The selector.
     * @return The first web control found by the selector.
     */
    public WebControl FindElementByXPath(UUID guid, IBy by) {
        if (by == null) {
            throw new IllegalArgumentException("by");
        }

        try {
            return new SeleniumElement(underlyingWebElement.findElement(org.openqa.selenium.By.xpath(by.toString())));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Finds all elements corresponding to a given selector.
     *
     * @param guid A globally unique identifier associated with a call.
     * @param by   The selector.
     * @return A collection of WebControls.
     */
    public Collection<WebControl> FindElementsByXPath(UUID guid, IBy by) {
        if (by == null) {
            throw new IllegalArgumentException("by");
        }

        List<WebControl> result = new ArrayList<>();

        log.trace(String.format("WebElement.FindElementsByXPath(By.CssSelector(%1$s)),", by));

        for (WebElement seleniumElement : underlyingWebElement.findElements(By.xpath(by.toString()))) {
            result.add(new SeleniumElement(seleniumElement));
        }

        return new ArrayList<>(result);
    }

    /**
     * Finds all web elements using the given method.
     *
     * @param guid   Uniquely identify the web element.
     * @param findBy Findby used to find the web elements.
     * @return Returns a collection of web elements.
     */
    public final Collection<WebControl> FindElements(UUID guid, IBy findBy) {
        if (findBy == null) {
            throw new IllegalArgumentException("findBy");
        }

        List<WebControl> result = new ArrayList<>();

        log.trace(String.format("WebElement.FindElements(By.CssSelector(%1$s));", findBy));

        for (WebElement seleniumElement : underlyingWebElement.findElements(By.cssSelector(findBy.toString()))) {
            result.add(new SeleniumElement(seleniumElement));
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

        log.trace(String.format("WebElement.GetAttribute(%1$s);", attributeName));

        return underlyingWebElement.getAttribute(attributeName) == null ? "" : underlyingWebElement.getAttribute(attributeName);
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

        log.trace(String.format("WebElement.GetCssValue(%1$s);", propertyName));

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
            log.trace(String.format("WebElement.SendKeys(%1$s);", text));
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

            log.trace(String.format("WebElement.SendKeys(%1$s);", substring));

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
        // TODO(DionnyS): Ensure this works in a grid instance
        if (moveMouseToOrigin) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(0, 0);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        log.trace("WebElement.Click();");

        underlyingWebElement.click();
    }

    /**
     * Submits this element to the web server.
     *
     * @param guid Uniquely identify the web element.
     */
    public final void Submit(UUID guid) {
        log.trace("WebElement.Submit();");
        underlyingWebElement.submit();
    }

    //region functions specifically for select elements

    public final boolean IsMultiple(UUID guid) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        return selectHelper.IsMultiple(guid);
    }

    public final List<WebControl> GetAllSelectedOptions(UUID guid) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        return selectHelper.GetAllSelectedOptions(guid);
    }

    public final WebControl GetSelectedOption(UUID guid) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        return selectHelper.GetSelectedOption(guid);
    }

    public final String GetSelectedOptionText(UUID guid){
        if(selectHelper == null){
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        return selectHelper.GetSelectedOptionText(guid);
    }

    public final List<WebControl> GetOptions(UUID guid) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        return selectHelper.GetOptions(guid);
    }

    public final void DeselectAll(UUID guid) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.DeselectAll(guid);
    }

    public final void DeselectByIndex(UUID guid, int index) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.DeselectByIndex(guid, index);
    }

    public final void DeselectByText(UUID guid, String text) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.DeselectByText(guid, text);
    }

    public final void DeselectByValue(UUID guid, String value) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.DeselectByValue(guid, value);
    }

    public final void SelectByIndex(UUID guid, int index) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.SelectByIndex(guid, index);
    }

    public final void SelectByText(UUID guid, String text) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.SelectByText(guid, text);
    }

    public final void SelectByValue(UUID guid, String value) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException("select", getUnderlyingWebElement().getTagName());
        }
        selectHelper.SelectByValue(guid, value);
    }
    //endregion
}

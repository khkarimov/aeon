package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.common.Point;
import com.ultimatesoftware.aeon.core.common.exceptions.IncorrectElementTagException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides methods available for a web element.
 */
public class SeleniumElement extends WebControl {
    private static final String SELECT_ELEMENT_TAG = "select";
    private static final String RESULT = "Result: {}";
    private static final int LONG_STRING_LENGTH = 50;
    private WebElement underlyingWebElement;
    private SeleniumSelectElement selectHelper;
    private static Logger log = LoggerFactory.getLogger(SeleniumElement.class);

    /**
     * Initializes a new instance of the {@link SeleniumElement} class.
     *
     * @param seleniumWebElement Underlying web element.
     */
    public SeleniumElement(WebElement seleniumWebElement) {
        underlyingWebElement = seleniumWebElement;
        String tagName = underlyingWebElement.getTagName();
        if (tagName != null && tagName.equalsIgnoreCase(SELECT_ELEMENT_TAG)) {
            selectHelper = new SeleniumSelectElement(new Select(underlyingWebElement));
        } else {
            selectHelper = null;
        }
    }

    /**
     * Gets the underlying Web Element.
     *
     * @return The underlying Web Element is returned.
     */
    public WebElement getUnderlyingWebElement() {
        return underlyingWebElement;
    }

    /**
     * Gets a value indicating whether or not this element is displayed.
     * Uniquely identify the web element.
     *
     * @return Returns true if the element is displayed, false if otherwise.
     */
    boolean displayed() {
        log.trace("WebElement.get_Displayed();");
        boolean result = getUnderlyingWebElement().isDisplayed();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is enabled.
     * Uniquely identify the web element.
     *
     * @return Returns true if the element is enabled, false if otherwise.
     */
    boolean enabled() {
        log.trace("WebElement.get_Enabled();");
        boolean result = getUnderlyingWebElement().isEnabled();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets a value indicating whether or not this element is selected.
     * Uniquely identify the web element.
     *
     * @return Returns true if the element is displayed, false if otherwise.
     */
    boolean selected() {
        log.trace("WebElement.get_Selected();");
        boolean result = getUnderlyingWebElement().isSelected();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets the tag name of this element.
     * Uniquely identify the web element.
     *
     * @return Returns the tag name of the web element.
     */
    String getTagName() {
        log.trace("WebElement.get_TagName();");
        String result = getUnderlyingWebElement().getTagName();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets the location of this element.
     * Uniquely identify the web element.
     *
     * @return Returns the location of the web element.
     */
    Point getLocation() {
        log.trace("WebElement.get_Location();");
        org.openqa.selenium.Point result = getUnderlyingWebElement().getLocation();
        log.trace(RESULT, result);
        return new Point(result.getX(), result.getY());
    }

    /**
     * Gets the innerText of this element, without any leading or trailing whitespace, and with other whitespace collapsed.
     * Uniquely identify the web element.
     *
     * @return Returns the text of the web element.
     */
    String getText() {
        log.trace("WebElement.get_Text();");
        String result = getUnderlyingWebElement().getText().trim();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Clears the content of this element.
     * Uniquely identify the web element.
     */
    void clear() {
        log.trace("WebElement.clear();");
        underlyingWebElement.clear();
    }

    /**
     * Finds the first web element using the given method.
     * Uniquely identify the web element.
     *
     * @param findBy Findby used to find the web element.
     * @return Returns the web element.
     */
    WebControl findElement(IByWeb findBy) {
        if (findBy == null) {
            throw new IllegalArgumentException("findBy");
        }

        try {

            log.trace("WebElement.findElement(by.cssSelector({}));", findBy);
            WebElement seleniumElement = underlyingWebElement.findElement(By.cssSelector(findBy.toString()));

            return new SeleniumElement(seleniumElement);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException(e, findBy);
        }
    }

    /**
     * Finds element through its text or value.
     * Uniquely identifiable id associated with this call.
     *
     * @param by The selector.
     * @return The first web control found by the selector.
     */
    WebControl findElementByXPath(IByWeb by) {
        if (by == null) {
            throw new IllegalArgumentException("by");
        }

        try {
            return new SeleniumElement(underlyingWebElement.findElement(org.openqa.selenium.By.xpath(by.toString())));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new NoSuchElementException(e, by);
        }
    }

    /**
     * Finds all elements corresponding to a given selector.
     * A globally unique identifier associated with a call.
     *
     * @param by The selector.
     * @return A collection of WebControls.
     */
    Collection<WebControl> findElementsByXPath(IByWeb by) {
        if (by == null) {
            throw new IllegalArgumentException("by");
        }

        List<WebControl> result = new ArrayList<>();

        log.trace("WebElement.findElementsByXPath(by.cssSelector({})),", by);

        for (WebElement seleniumElement : underlyingWebElement.findElements(By.xpath(by.toString()))) {
            result.add(new SeleniumElement(seleniumElement));
        }

        return new ArrayList<>(result);
    }

    /**
     * Finds all web elements using the given method.
     * Uniquely identify the web element.
     *
     * @param findBy Findby used to find the web elements.
     * @return Returns a collection of web elements.
     */
    Collection<WebControl> findElements(IByWeb findBy) {
        if (findBy == null) {
            throw new IllegalArgumentException("findBy");
        }

        List<WebControl> result = new ArrayList<>();

        log.trace("WebElement.findElements(by.cssSelector({}));", findBy);

        for (WebElement seleniumElement : underlyingWebElement.findElements(By.cssSelector(findBy.toString()))) {
            result.add(new SeleniumElement(seleniumElement));
        }

        return new ArrayList<>(result);
    }

    /**
     * Gets the value of the specified attribute for this element.
     *
     * @param attributeName Attribute name of the web element.
     * @return Returns the attribute associated with the attribute name.
     */
    String getAttribute(String attributeName) {
        if (attributeName == null) {
            throw new IllegalArgumentException("attributeName");
        }

        if (attributeName.equalsIgnoreCase("INNERHTML")) {
            attributeName = "innerHTML";
        }

        log.trace("WebElement.getAttribute({});", attributeName);

        return underlyingWebElement.getAttribute(attributeName) == null ? "" : underlyingWebElement.getAttribute(attributeName);
    }

    /**
     * Checks whether a specific attribute exists on the element.
     *
     * @param attributeName The name of the attribute to check for.
     * @return True if the attribute exists, false otherwise.
     */
    boolean hasAttribute(String attributeName) {
        if (attributeName == null) {
            throw new IllegalArgumentException("attributeName");
        }

        log.trace("WebElement.hasAttribute({});", attributeName);

        return underlyingWebElement.getAttribute(attributeName) != null;
    }

    /**
     * Gets the value of a CSS property of this element.
     *
     * @param propertyName Property name of the web element.
     * @return Returns the CSS value of the property name.
     */
    String getCssValue(String propertyName) {
        if (propertyName == null) {
            throw new IllegalArgumentException("propertyName");
        }

        log.trace("WebElement.getCssValue({});", propertyName);

        return underlyingWebElement.getCssValue(propertyName);
    }

    /**
     * Simulates typing text into the element.
     *
     * @param text Text that will be typed into the element.
     */
    void sendKeys(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text");
        }

        // The selenium webdriver has a problem sending left-parenthesis in firefox. Replacing all left-parenthesis with the keystroke shift-9 seams to fix the issue.
        if (text.contains("(") && text.length() > 1) {
            String[] newText = text.split("[(]", -1);
            for (String s : newText) {
                if (newText[newText.length - 1].equals(s)) {
                    this.sendKeys(s);
                } else {
                    this.sendKeys(s);
                    this.sendKeys(Keys.SHIFT + "9" + Keys.SHIFT);
                }
            }

            return;
        }

        if (text.length() < LONG_STRING_LENGTH) {
            log.trace("WebElement.sendKeys({});", text);
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

            log.trace("WebElement.sendKeys({});", substring);

            underlyingWebElement.sendKeys(substring);

            startIndex += LONG_STRING_LENGTH;
        }
    }

    /**
     * Clicks this element.
     * Uniquely identify the web element.
     */
    public void click() {
        log.trace("WebElement.click();");

        underlyingWebElement.click();
    }

    /**
     * Submits this element to the web server.
     * Uniquely identify the web element.
     */
    void submit() {
        log.trace("WebElement.submit();");
        underlyingWebElement.submit();
    }

    /**
     * Indicates whether the element supports selecting multiple options at the same time.
     *
     * @return A boolean indicating whether the element supports selecting multiple options at the same time.
     */
    boolean isMultiple() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        return selectHelper.isMultiple();
    }

    /**
     * Gets all selected options belonging to an element.
     *
     * @return A list of the selected options belonging to an element.
     */
    List<WebControl> getAllSelectedOptions() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        return selectHelper.getAllSelectedOptions();
    }

    /**
     * Gets the selected option for the element.
     *
     * @return The selected option for the element.
     */
    WebControl getSelectedOption() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        return selectHelper.getSelectedOption();
    }

    /**
     * Gets the selected option text for the element.
     *
     * @return The selected option text for the element.
     */
    String getSelectedOptionText() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        return selectHelper.getSelectedOptionText();
    }

    /**
     * Gets all options belonging to the element.
     *
     * @return A list of all options belonging to a element.
     */
    List<WebControl> getOptions() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        return selectHelper.getOptions();
    }

    /**
     * Clears all selected entries on an element.
     */
    void deselectAll() {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.deselectAll();
    }

    /**
     * Deselect the option of the element at the given index.
     *
     * @param index The index of the option to deselect.
     */
    void deselectByIndex(int index) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.deselectByIndex(index);
    }

    /**
     * Deselect all options that display text matching the argument for the element.
     *
     * @param text The visible text to match against
     */
    void deselectByText(String text) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.deselectByText(text);
    }

    /**
     * Deselect all options that have a value matching the argument for the element.
     *
     * @param value The value to match against
     */
    void deselectByValue(String value) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.deselectByValue(value);
    }

    /**
     * Deselect the option at the given index for the element.
     *
     * @param index The option at this index will be deselected.
     */
    void selectByIndex(int index) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.selectByIndex(index);
    }

    /**
     * Select all options that display text matching the argument for the element.
     *
     * @param text The visible text to match against.
     */
    void selectByText(String text) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.selectByText(text);
    }

    /**
     * Select all options that have a value matching the argument for the element.
     *
     * @param value The value to match against
     */
    void selectByValue(String value) {
        if (selectHelper == null) {
            throw new IncorrectElementTagException(SELECT_ELEMENT_TAG, getUnderlyingWebElement().getTagName());
        }
        selectHelper.selectByValue(value);
    }
}

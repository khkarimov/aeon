package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for Selenium Select Element.
 */
public class SeleniumSelectElement extends WebControl {
    private Select underlyingSelectElement;
    private static Logger log = LoggerFactory.getLogger(SeleniumSelectElement.class);

    /**
     * Constructor for Selenium Select Element.
     *
     * @param selectElement The underlying Select Element
     */
    public SeleniumSelectElement(Select selectElement) {
        underlyingSelectElement = selectElement;
    }

    /**
     * Gets the underlying Select Element.
     *
     * @return The underlying Select Element
     */
    protected final Select getUnderlyingSelectElement() {
        return underlyingSelectElement;
    }

    /**
     * Indicates whether the select element supports selecting multiple options at the same time.
     *
     * @return A boolean indicating whether the select element supports selecting multiple options at the same time.
     */
    public final boolean isMultiple() {
        log.trace("SelectElement.get_IsMultiple();");
        return getUnderlyingSelectElement().isMultiple();
    }

    /**
     * Gets all selected options belonging to a select element.
     *
     * @return A list of the selected options belonging to a select element
     */
    public final List<WebControl> getAllSelectedOptions() {
        log.trace("SelectElement.get_AllSelectedOptions();");
        return underlyingSelectElement.getAllSelectedOptions().stream()
                .map(SeleniumElement::new)
                .collect(Collectors.toList());
    }

    /**
     * Gets the selected option for the select element.
     *
     * @return The selected option for the select element.
     */
    public final WebControl getSelectedOption() {
        log.trace("SelectElement.get_SelectedOption();");
        return new SeleniumElement(getUnderlyingSelectElement().getFirstSelectedOption());
    }

    /**
     * Gets the selected option text for the select element.
     *
     * @return The selected option text for the select element.
     */
    public final String getSelectedOptionText() {
        log.trace("SelectElement.get_SelectedOptionText();");
        return getUnderlyingSelectElement().getFirstSelectedOption().getText();
    }

    /**
     * Gets all options belonging to the select element.
     *
     * @return A list of all options belonging to a select element.
     */
    public final List<WebControl> getOptions() {
        log.trace("SelectElement.get_Options();");
        return underlyingSelectElement.getOptions().stream()
                .map(SeleniumElement::new)
                .collect(Collectors.toList());
    }

    /**
     * Clears all selected entries on a select element.
     */
    public final void deselectAll() {
        log.trace("SelectElement.deselectAll();");
        getUnderlyingSelectElement().deselectAll();
    }

    /**
     * Deselect the option of the select element at the given index.
     *
     * @param index The index of the option to deselect.
     */
    public final void deselectByIndex(int index) {
        log.trace("SelectElement.deselectByIndex({});", index);
        getUnderlyingSelectElement().deselectByIndex(index);
    }

    /**
     * Deselect all options that display text matching the argument for the select element.
     *
     * @param text The visible text to match against
     */
    public final void deselectByText(String text) {
        log.trace("SelectElement.deselectByText({});", text);
        getUnderlyingSelectElement().deselectByVisibleText(text);
    }

    /**
     * Deselect all options that have a value matching the argument for the select element.
     *
     * @param value The value to match against
     */
    public final void deselectByValue(String value) {
        log.trace("SelectElement.deselectByValue({});", value);
        getUnderlyingSelectElement().deselectByValue(value);
    }

    /**
     * Deselect the option at the given index for the select element.
     *
     * @param index The option at this index will be deselected.
     */
    public final void selectByIndex(int index) {
        log.trace("SelectElement.selectByIndex({});", index);
        getUnderlyingSelectElement().selectByIndex(index);
    }

    /**
     * Select all options that display text matching the argument for the select element.
     *
     * @param text The visible text to match against.
     */
    public final void selectByText(String text) {
        log.trace("SelectElement.selectByText({});", text);
        getUnderlyingSelectElement().selectByVisibleText(text);
    }

    /**
     * Select all options that have a value matching the argument for the select element.
     *
     * @param value The value to match against
     */
    public final void selectByValue(String value) {
        log.trace("SelectElement.selectByValue({});", value);
        getUnderlyingSelectElement().selectByValue(value);
    }
}

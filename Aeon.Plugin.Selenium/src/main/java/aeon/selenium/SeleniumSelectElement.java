package aeon.selenium;

import aeon.core.framework.abstraction.controls.web.WebControl;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for Selenium Dropdown Element.
 */
public class SeleniumSelectElement extends WebControl {
    private Select underlyingSelectElement;
    private static Logger log = LoggerFactory.getLogger(SeleniumSelectElement.class);

    /**
     * Constructor for Selenium Dropdown Element.
     *
     * @param selectElement The underlying Dropdown Element
     */
    public SeleniumSelectElement(Select selectElement) {
        underlyingSelectElement = selectElement;
    }

    /**
     * Gets the underlying Dropdown Element.
     *
     * @return The underlying Dropdown Element
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
        log.trace(String.format("SelectElement.deselectByIndex(%1$s);", index));

        getUnderlyingSelectElement().deselectByIndex(index);
    }

    /**
     * Deselect all options that display text matching the argument for the select element.
     *
     * @param text The visible text to match against
     */
    public final void deselectByText(String text) {
        log.trace(String.format("SelectElement.deselectByText(%1$s);", text));
        getUnderlyingSelectElement().deselectByVisibleText(text);
    }

    /**
     * Deselect all options that have a value matching the argument for the select element.
     *
     * @param value The value to match against
     */
    public final void deselectByValue(String value) {
        log.trace(String.format("SelectElement.deselectByValue(%1$s);", value));
        getUnderlyingSelectElement().deselectByValue(value);
    }

    /**
     * Deselect the option at the given index for the select element.
     *
     * @param index The option at this index will be deselected.
     */
    public final void selectByIndex(int index) {
        log.trace(String.format("SelectElement.selectByIndex(%1$s);", index));
        getUnderlyingSelectElement().selectByIndex(index);
    }

    /**
     * Dropdown all options that display text matching the argument for the select element.
     *
     * @param text The visible text to match against.
     */
    public final void selectByText(String text) {
        log.trace(String.format("SelectElement.selectByText(%1$s);", text));
        getUnderlyingSelectElement().selectByVisibleText(text);
    }

    /**
     * Dropdown all options that have a value matching the argument for the select element.
     *
     * @param value The value to match against
     */
    public final void selectByValue(String value) {
        log.trace(String.format("SelectElement.selectByValue(%1$s);", value));
        getUnderlyingSelectElement().selectByValue(value);
    }
}

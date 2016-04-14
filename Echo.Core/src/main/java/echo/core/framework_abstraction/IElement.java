package echo.core.framework_abstraction;

import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_interaction.Point;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Keys;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Defines the interface through which the user controls elements on the page.
 */
public interface IElement {
    /**
     * Gets a value indicating whether or not this element is displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is displayed.
     */
    default boolean Displayed(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets a value indicating whether or not this element is enabled.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is enabled.
     */
    default boolean Enabled(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets a value indicating whether or not this element is selected.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is selected.
     */
    default boolean Selected(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the tag name of this element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The tag name of this element.
     */
    default String GetTagName(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the location of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the location of the web element.
     */
    default Point GetLocation(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the innerText of this element, without any leading or trailing whitespace,
     * and with other whitespace collapsed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The innerText of this element, without any leading or trailing whitespace,
     * and with other whitespace collapsed.
     */
    default String GetText(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Clears the content of this element.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    default void Clear(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Clicks this element.
     *
     * @param guid              A globally unique identifier associated with this call.
     * @param moveMouseToOrigin If true, will place the cursor at 0,0 before clicking.
     */
    default void Click(UUID guid, boolean moveMouseToOrigin) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Submits this element to the web server.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    default void Submit(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Simulates typing text into the element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text to type into the element.
     * @throws IllegalArgumentException If <paramref name="text"/> is <see langword="null"/>.
     */
    default void SendKeys(UUID guid, String text) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Simulates typing text into the element.
     *
     * @param guid        A globally unique identifier associated with this call.
     * @param keyboardKey The key to type into the element.
     */
    default void SendKeys(UUID guid, Keys keyboardKey) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the value of the specified attribute for this element.
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    default String GetAttribute(UUID guid, String attributeName) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the value of a CSS property of this element.
     *
     * @param guid         A globally unique identifier associated with this call.
     * @param propertyName The name of the CSS property to get the value of.
     * @return The value of the specified CSS property.
     * @throws IllegalArgumentException If <paramref name="propertyName"/> is <see langword="null"/>.
     */
    default String GetCssValue(UUID guid, String propertyName) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     */
    default IElement FindElement(UUID guid, IBy findBy) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     */
    default Collection<IElement> FindElements(UUID guid, IBy findBy) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets a value indicating whether the parent element supports multiple selections.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether the parent element supports multiple selections.
     */
    default boolean IsMultiple(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets all of the selected options within the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return All of the selected options within the select element.
     */
    default List<IElement> GetAllSelectedOptions(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the selected item within the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The selected item within the select element.
     */
    default IElement GetSelectedOption(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Gets the list of options for the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The list of options for the select element.
     */
    default List<IElement> GetOptions(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    default void DeselectAll(UUID guid) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Deselect the option by the index, as determined by the "index" attribute of the element.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param index The value of the index attribute of the option to deselect.
     */
    default void DeselectByIndex(UUID guid, int index) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Deselect the option by the text displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text of the option to be deselected.
     */
    default void DeselectByText(UUID guid, String text) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Deselect the option having value matching the specified text.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The value of the option to deselect.
     */
    default void DeselectByValue(UUID guid, String value) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Select the option by the index, as determined by the "index" attribute of the element.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param index The value of the index attribute of the option to be selected.
     */
    default void SelectByIndex(UUID guid, int index) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Select all options by the text displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text of the option to be selected. If an exact match is not found,
     *             this method will perform a substring match.
     */
    default void SelectByText(UUID guid, String text) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    /**
     * Select an option by the value.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The value of the option to be selected.
     */
    default void SelectByValue(UUID guid, String value) {
        throw new NotImplementedException("Method Displayed not Implemented.");
    }

    default IBy getSelector(){
        throw new NotImplementedException("Method Displayed not Implemented.");
    }
}
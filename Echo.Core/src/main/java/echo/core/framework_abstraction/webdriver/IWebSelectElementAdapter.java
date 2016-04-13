package echo.core.framework_abstraction.webdriver;

import java.util.*;

/**
 * Provides a convenience method for manipulating selections of options in an HTML select element.
 */
public interface IWebSelectElementAdapter {
    /**
     * Gets a value indicating whether the parent element supports multiple selections.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether the parent element supports multiple selections.
     */
    boolean IsMultiple(UUID guid);

    /**
     * Gets all of the selected options within the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return All of the selected options within the select element.
     */
    List<IWebElementAdapter> GetAllSelectedOptions(UUID guid);

    /**
     * Gets the selected item within the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The selected item within the select element.
     */
    IWebElementAdapter GetSelectedOption(UUID guid);

    /**
     * Gets the list of options for the select element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The list of options for the select element.
     */
    List<IWebElementAdapter> GetOptions(UUID guid);

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void DeselectAll(UUID guid);

    /**
     * Deselect the option by the index, as determined by the "index" attribute of the element.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param index The value of the index attribute of the option to deselect.
     */
    void DeselectByIndex(UUID guid, int index);

    /**
     * Deselect the option by the text displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text of the option to be deselected.
     */
    void DeselectByText(UUID guid, String text);

    /**
     * Deselect the option having value matching the specified text.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The value of the option to deselect.
     */
    void DeselectByValue(UUID guid, String value);

    /**
     * Select the option by the index, as determined by the "index" attribute of the element.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param index The value of the index attribute of the option to be selected.
     */
    void SelectByIndex(UUID guid, int index);

    /**
     * Select all options by the text displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text of the option to be selected. If an exact match is not found,
     *             this method will perform a substring match.
     */
    void SelectByText(UUID guid, String text);

    /**
     * Select an option by the value.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The value of the option to be selected.
     */
    void SelectByValue(UUID guid, String value);
}

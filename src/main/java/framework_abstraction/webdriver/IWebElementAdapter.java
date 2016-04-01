package framework_abstraction.webdriver;

import common.webobjects.interfaces.IBy;
import framework_interaction.Point;
import org.openqa.selenium.Keys;

import java.util.Collection;
import java.util.UUID;

/**
 * Defines the interface through which the user controls elements on the page.
 */
public interface IWebElementAdapter {
    /**
     * Gets a value indicating whether or not this element is displayed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is displayed.
     */
    boolean Displayed(UUID guid);

    /**
     * Gets a value indicating whether or not this element is enabled.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is enabled.
     */
    boolean Enabled(UUID guid);

    /**
     * Gets a value indicating whether or not this element is selected.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A value indicating whether or not this element is selected.
     */
    boolean Selected(UUID guid);

    /**
     * Gets the tag name of this element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The tag name of this element.
     */
    String GetTagName(UUID guid);

    /**
     * Gets the location of this element.
     *
     * @param guid Uniquely identify the web element.
     * @return Returns the location of the web element.
     */
    Point GetLocation(UUID guid);

    /**
     * Gets the innerText of this element, without any leading or trailing whitespace,
     * and with other whitespace collapsed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The innerText of this element, without any leading or trailing whitespace,
     * and with other whitespace collapsed.
     */
    String GetText(UUID guid);

    /**
     * Clears the content of this element.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Clear(UUID guid);

    /**
     * Clicks this element.
     *
     * @param guid              A globally unique identifier associated with this call.
     * @param moveMouseToOrigin If true, will place the cursor at 0,0 before clicking.
     */
    void Click(UUID guid, boolean moveMouseToOrigin);

    /**
     * Submits this element to the web server.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Submit(UUID guid);

    /**
     * Simulates typing text into the element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param text The text to type into the element.
     * @throws IllegalArgumentException If <paramref name="text"/> is <see langword="null"/>.
     */
    void SendKeys(UUID guid, String text);

    /**
     * Simulates typing text into the element.
     *
     * @param guid        A globally unique identifier associated with this call.
     * @param keyboardKey The key to type into the element.
     */
    void SendKeys(UUID guid, Keys keyboardKey);

    /**
     * Gets the value of the specified attribute for this element.
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    String GetAttribute(UUID guid, String attributeName);

    /**
     * Gets the value of a CSS property of this element.
     *
     * @param guid         A globally unique identifier associated with this call.
     * @param propertyName The name of the CSS property to get the value of.
     * @return The value of the specified CSS property.
     * @throws IllegalArgumentException If <paramref name="propertyName"/> is <see langword="null"/>.
     */
    String GetCssValue(UUID guid, String propertyName);

    /**
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     */
    IWebElementAdapter FindElement(UUID guid, IBy findBy);

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     */
    Collection<IWebElementAdapter> FindElements(UUID guid, IBy findBy);
}
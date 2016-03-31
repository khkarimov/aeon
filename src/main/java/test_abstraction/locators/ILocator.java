package test_abstraction.locators;

import framework_interaction.ElementType;

import java.awt.*;

/**
 * An Interface for the element locator.
 */
public interface ILocator {
    /**
     * Gets the location info.
     */
    String getLocationInfo();

    /**
     * Gets the element type.
     */
    ElementType getElementType();

    /**
     * Finds the location of the element.
     *
     * @return The point on the screen where the element is located.
     */
    Point Locate();
}

package aeon.core.extensions;

import aeon.core.common.interfaces.IBy;
import org.pf4j.ExtensionPoint;

/**
 * The Extension point Interface for web and mobile extensions.
 */
public interface IProductTypeExtension extends ExtensionPoint {

    /**
     * Creates a By selector by parsing value and type.
     * @param value Value
     * @param type Type
     * @return By
     */
    IBy createSelector(String value, String type);

    /**
     * Creates a command class based on a String.
     * @param commandString Command string
     * @return Class
     */
    Class<?> createCommand(String commandString);
}

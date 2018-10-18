package aeon.core.extensions;

import aeon.core.common.interfaces.IBy;
import org.pf4j.ExtensionPoint;

import java.util.List;

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
     * Creates a command object.
     * @param commandString Command string
     * @param args Arguments
     * @param value Selector value
     * @param type Selector type
     * @return Command object
     */
    Object createCommand(String commandString, List<Object> args, String value, String type);
}

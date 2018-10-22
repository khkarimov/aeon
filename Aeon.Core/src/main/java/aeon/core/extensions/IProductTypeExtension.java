package aeon.core.extensions;

import aeon.core.common.interfaces.IBy;
import org.pf4j.ExtensionPoint;

import java.util.List;
import java.util.Map;

/**
 * The Extension point Interface for web and mobile extensions.
 */
public interface IProductTypeExtension extends ExtensionPoint {

    /**
     * Creates a By selector by parsing value and type.
     * @param selector Selector
     * @return By
     */
    IBy createSelector(Map<String, String> selector);

    /**
     * Creates a command object.
     * @param commandString Command string
     * @param args Arguments
     * @return Command object
     */
    Object createCommand(String commandString, List<Object> args);
}

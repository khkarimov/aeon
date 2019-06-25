package com.ultimatesoftware.aeon.core.common.exceptions;

/**
 * Extension that is thrown when a single extension is
 * requested, but zero or multiple extensions were found.
 */
public class AeonSinglePluginRequestedException extends RuntimeException {

    /**
     * Constructor to format a readable message.
     *
     * @param extensionName   The name of the requested extension.
     * @param foundExtensions The number of found extensions.
     */
    public AeonSinglePluginRequestedException(String extensionName, int foundExtensions) {
        super(String.format("Single extension for \"%s\" requested, but found %d.", extensionName, foundExtensions));
    }
}

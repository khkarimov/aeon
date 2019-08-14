package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * The class to handle the exception thrown when select does not contain option group.
 */
public class SelectDoesNotContainOptionGroupException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link SelectDoesNotContainOptionGroupException} class.
     *
     * @param optgroup The option group which was not found in the select.
     */
    public SelectDoesNotContainOptionGroupException(String optgroup) {
        super(String.format(Locale.getDefault(), Resources.getString("SelectDoesNotContainOptionGroupException"), optgroup));
    }
}

package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a select element is not supported.
 */
public class UnsupportedElementException extends RuntimeException implements Serializable {

    private Class forType;

    /**
     * Initializes a new instance of the {@link UnsupportedElementException} class.
     *
     * @param forType The select element object's {@code Type} which is not supported.
     */
    public UnsupportedElementException(Class forType) {
        super(String.format(Locale.getDefault(), Resources.getString("UnsupportedElementException_ctor_DefaultMessage"), forType));
        this.forType = forType;
    }

    /**
     * Gets the forType of the class.
     * @return the forType class.
     */
    public Class getForType() {
        return forType;
    }

    /**
     * Sets the forType class.
     * @param forType the class type to be set.
     */
    public void setForType(Class forType) {
        this.forType = forType;
    }
}

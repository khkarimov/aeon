package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when a select element is not supported.
 */
public class UnsupportedSelectElementException extends RuntimeException implements Serializable {
    private java.lang.Class forType;

    /**
     * Initializes a new instance of the <see cref="UnsupportedSelectElementException"/> class.
     *
     * @param forType The select element object's <see cref="Type"/> which is not supported.
     */
    public UnsupportedSelectElementException(java.lang.Class forType) {
        super(String.format(Resources.getString("UnsupportedSelectElementException_ctor_DefaultMessage"), forType));
        this.forType = forType;
    }

    public Class getForType() {
        return forType;
    }

    public void setForType(Class forType) {
        this.forType = forType;
    }
}

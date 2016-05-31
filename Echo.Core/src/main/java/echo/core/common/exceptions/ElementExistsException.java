package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by RafaelT on 5/31/2016.
 */
public class ElementExistsException extends RuntimeException implements Serializable {
    public ElementExistsException() {
        super(Resources.getString("ElementExistsException_ctor_DefaultMessage"));
    }
}
package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Created by Administrator on 6/29/2016.
 */
public class ElementIsVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsVisibleException} class.
     */
    public ElementIsVisibleException() {
        super(Resources.getString("ElementIsVisibleException_ctor_DefaultMessage"));
    }
}

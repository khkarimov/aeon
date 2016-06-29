package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by SebastianR on 6/29/2016.
 */
public class ValuesAreNotAlikeException extends RuntimeException implements Serializable {

    public ValuesAreNotAlikeException(){
        super(Resources.getString("ValuesAreNotAlikeException_ctor_DefaultMessage"));
    }
}

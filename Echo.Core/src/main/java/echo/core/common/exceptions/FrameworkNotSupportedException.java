package echo.core.common.exceptions;

import echo.core.common.Resources;
import echo.core.framework_interaction.ElementType;

/**
 * Created by dionnys on 4/14/16.
 */
public class FrameworkNotSupportedException extends RuntimeException {
    public FrameworkNotSupportedException(ElementType elementType) {
        super(String.format(Resources.getString("FrameworkNotSupportedException_ctor_DefaultMessage"), elementType.toString()));
    }
}

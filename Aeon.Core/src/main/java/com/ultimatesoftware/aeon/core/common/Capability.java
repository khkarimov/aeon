package com.ultimatesoftware.aeon.core.common;

import java.lang.annotation.*;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Capability {

    /**
     * asdasdasd.
     *
     * @return asdsad.
     */
    Capabilities value() default Capabilities.WEB;
}

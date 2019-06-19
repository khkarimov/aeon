package com.ultimatesoftware.aeon.core.common;

import java.lang.annotation.*;

/**
 * Annotation for product classes to request a capability.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Capability {

    /**
     * Returns the requested capability of the annotated product.
     *
     * @return The requested capability of the annotated product.
     */
    Capabilities value() default Capabilities.WEB;
}

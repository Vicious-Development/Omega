package com.vicious.omega.environment.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Indicates whether a isNull check is required before using a method
 */
@Target(ElementType.METHOD)
public @interface NullSafe {
}

package com.vicious.omega.environment.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Indicates a method or constructor intended solely for wrapping objects.
 */
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface Wrapper {
}

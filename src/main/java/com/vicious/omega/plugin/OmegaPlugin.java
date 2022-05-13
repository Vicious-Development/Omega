package com.vicious.omega.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OmegaPlugin {
    /**
     * The plugin's id. This should be as unique as you can possibly make it. Don't use short id like "abc" or "coolutils"
     * @return
     */
    String id();

    /**
     * Not required to be unique.
     * @return
     */
    String name() default "";
    /**
     * Version
     */
    String version() default "";
    /**
     * Description
     */
    String description() default "";
    /**
     * Dependencies
     */
    String[] dependencies() default {};
}

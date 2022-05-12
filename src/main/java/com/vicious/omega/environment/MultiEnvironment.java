package com.vicious.omega.environment;

public interface MultiEnvironment {
    default boolean activeIn(Environment required){
        return required.active();
    }
    static boolean active(Environment required){
        return required.active();
    }
}

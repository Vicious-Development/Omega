package com.vicious.omega.environment;

import com.vicious.omega.Omega;

public interface MultiEnvironment {
    default boolean activeIn(Environment required){
        return Omega.getEnvironment()==required;
    }
    static boolean active(Environment required){
        return Omega.getEnvironment()==required;
    }
}

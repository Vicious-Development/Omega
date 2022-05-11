package com.vicious.omega.environment;

import com.vicious.omega.Omega;

public class UnusableEnvironmentException extends RuntimeException{
    public UnusableEnvironmentException(){
        super(Omega.getEnvironment().toString());
    }
}

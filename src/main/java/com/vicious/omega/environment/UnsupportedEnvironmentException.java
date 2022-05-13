package com.vicious.omega.environment;

import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.viciouslib.util.reflect.deep.DeepReflection;
import com.vicious.viciouslib.util.reflect.deep.MethodSearchContext;
import com.vicious.viciouslib.util.reflect.deep.TotalFailureException;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

public class UnsupportedEnvironmentException extends RuntimeException{
    private String msg;
    public UnsupportedEnvironmentException(){
        super();
        StackTraceElement[] trace = getStackTrace();
        try {
            for (StackTraceElement elem : trace) {
                Class<?> source = Class.forName(elem.getClassName());
                try {
                    ReflectiveMethod m = DeepReflection.getMethod(source,new MethodSearchContext().name(trace[0].getMethodName()));
                    Method method = m.getReflectiveTarget(source);
                    EnvironmentCompatibility compat = method.getAnnotation(EnvironmentCompatibility.class);
                    if(compat == null) continue;
                    msg = "Application not running in any of the environments: " + Arrays.toString(compat.value());
                    break;
                } catch (TotalFailureException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

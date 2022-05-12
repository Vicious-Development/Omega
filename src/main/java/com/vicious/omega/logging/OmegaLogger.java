package com.vicious.omega.logging;

import com.vicious.omega.Omega;
import com.vicious.omega.environment.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OmegaLogger {
    private static OmegaLogger instance;
    public static OmegaLogger getInstance(){
        if(instance == null){
            if(Environment.SPONGE.active()){
                instance = new OmegaLogger(Omega.spongeLogger());
            }
            else{
                instance = new OmegaLogger(LoggerFactory.getLogger("Omega"));
            }
        }
        return instance;
    }

    private Object src;
    private OmegaLogger(Object src){
        this.src=src;
    }
    public void inf(String info){
        asSLF4J().info(info);
    }
    public void wrn(String warn){
        asSLF4J().warn(warn);
    }
    public void err(String error){
        asSLF4J().error(error);
    }
    public void dbg(String debug){
        asSLF4J().debug(debug);
    }
    public static void info(String info){
        getInstance().inf(info);
    }
    public static void warn(String warn){
        getInstance().wrn(warn);
    }
    public static void error(String error){
        getInstance().err(error);
    }
    public static void debug(String debug){
        getInstance().dbg(debug);
    }

    /**
     * Sponge and Omega both use this.
     * @return
     */
    public Logger asSLF4J(){
        return (Logger) src;
    }
}

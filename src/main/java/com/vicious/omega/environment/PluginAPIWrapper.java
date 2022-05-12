package com.vicious.omega.environment;

public class PluginAPIWrapper<T extends PluginAPIWrapper<T,S,B>,S,B> extends EnviromentWrapper<T>{
    public PluginAPIWrapper(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE})
    public S asSponge(){
        return (S)src;
    }
    @EnvironmentCompatibility({Environment.BUKKIT})
    public B asBukkit(){
        return (B)src;
    }
}

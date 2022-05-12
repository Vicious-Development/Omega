package com.vicious.omega.environment;

public class PluginAPIWrapper<OMEGATYPE extends PluginAPIWrapper<OMEGATYPE, SPONGETYPE, BUKKITTYPE>, SPONGETYPE, BUKKITTYPE> extends EnviromentWrapper<OMEGATYPE>{
    public PluginAPIWrapper(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE})
    public SPONGETYPE asSponge(){
        return (SPONGETYPE)src;
    }
    @EnvironmentCompatibility({Environment.BUKKIT})
    public BUKKITTYPE asBukkit(){
        return (BUKKITTYPE)src;
    }

    public boolean sponge(){
        return Environment.SPONGE.active();
    }

    public boolean bukkit(){
        return Environment.BUKKIT.active();
    }
}

package com.vicious.omega.environment;

/**
 * Used for storing objects that have an Omega implementation. (Implementation being something that doesn't wrap another object).
 */
public class OmegaAPIWrapper<OMEGATYPE extends PluginAPIWrapper<OMEGATYPE,SPONGETYPE,BUKKITTYPE>,OMEGAAPITYPE,SPONGETYPE,BUKKITTYPE> extends PluginAPIWrapper<OMEGATYPE,SPONGETYPE,BUKKITTYPE>{
    public OmegaAPIWrapper(Object src) {
        super(src);
    }
    public OMEGAAPITYPE asOmega(){
        return (OMEGAAPITYPE) src;
    }
}

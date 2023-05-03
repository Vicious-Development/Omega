package com.vicious.omega.entity;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;

//Todo: full impl
public class EntityType extends PluginAPIWrapper<EntityType, org.spongepowered.api.entity.EntityType, org.bukkit.entity.EntityType> {
    public EntityType(Object src) {
        super(src);
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public String getTypeName(){
        if(sponge()) return asSponge().getId();
        else if(bukkit()) return asBukkit().getName();
        else throw new UnsupportedEnvironmentException();
    }
}

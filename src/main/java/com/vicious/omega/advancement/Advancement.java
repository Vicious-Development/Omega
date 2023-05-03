package com.vicious.omega.advancement;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;

//TODO:impl
public class Advancement extends PluginAPIWrapper<Advancement, org.spongepowered.api.advancement.Advancement, org.bukkit.advancement.Advancement> {
    public Advancement(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getName(){
        if(sponge()) return asSponge().getName();
        else if(bukkit()) return asBukkit().getKey().getKey();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getId(){
        if(sponge()) return asSponge().getId();
        else if(bukkit()) return asBukkit().getKey().toString();
        else throw new UnsupportedEnvironmentException();
    }
}

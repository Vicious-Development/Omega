package com.vicious.omega.player;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.MultiEnvironment;
import com.vicious.omega.environment.UnusableEnvironmentException;

import java.util.UUID;

public class Player implements MultiEnvironment {
    @EnvironmentCompatibility({Environment.SPONGE})
    private Object obj;
    public Player(Object in) {
        obj =in;
    }
    @EnvironmentCompatibility({Environment.SPONGE})
    public org.spongepowered.api.entity.living.player.Player asSponge(){
        return (org.spongepowered.api.entity.living.player.Player)obj;
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public String getHostAddress() {
        if(activeIn(Environment.SPONGE)) asSponge().getConnection().getAddress().getAddress().getHostAddress();
        throw new UnusableEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public UUID getUUID(){
        if(activeIn(Environment.SPONGE)) asSponge().getUniqueId();
        throw new UnusableEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public String getName(){
        if(activeIn(Environment.SPONGE)) asSponge().getName();
        throw new UnusableEnvironmentException();
    }
}

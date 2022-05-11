package com.vicious.omega.server;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.EnvironmentSpecific;
import com.vicious.omega.environment.UnusableEnvironmentException;
import com.vicious.omega.player.Player;
import org.spongepowered.api.Sponge;

import java.util.UUID;

public class Server implements EnvironmentSpecific {
    private static Server instance;
    private Object sourceObject;
    @EnvironmentCompatibility({Environment.SPONGE})
    public static Server getServer(){
        if(instance == null){
            if(EnvironmentSpecific.active(Environment.SPONGE)) instance = new Server(Sponge.getServer());
            throw new UnusableEnvironmentException();
        }
        return instance;
    }
    private Server(Object source){
        sourceObject=source;
    }
    public org.spongepowered.api.Server asSponge(){
        return Sponge.getServer();
    }
    @EnvironmentCompatibility({Environment.SPONGE})
    public Player getPlayer(UUID uuid){
        if(activeIn(Environment.SPONGE)){
            return new Player(asSponge().getPlayer(uuid));
        }
        throw new UnusableEnvironmentException();
    }
}

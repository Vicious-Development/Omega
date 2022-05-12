package com.vicious.omega.player;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnusableEnvironmentException;

import java.util.UUID;

public class Player extends PluginAPIWrapper<Player,org.spongepowered.api.entity.living.player.Player, org.bukkit.entity.Player> {
    public Player(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getHostAddress() {
        if(Environment.SPONGE.active()) asSponge().getConnection().getAddress().getAddress().getHostAddress();
        else if(Environment.BUKKIT.active()) asBukkit().getAddress().getAddress().getHostAddress();
        throw new UnusableEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public UUID getUUID(){
        if(Environment.SPONGE.active()) asSponge().getUniqueId();
        else if(Environment.BUKKIT.active()) asBukkit().getUniqueId();
        throw new UnusableEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getName(){
        if(Environment.SPONGE.active()) asSponge().getName();
        else if(Environment.BUKKIT.active()) asBukkit().getName();
        throw new UnusableEnvironmentException();
    }
}

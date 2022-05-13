package com.vicious.omega.event.player;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.event.impl.PluginAPIEventWrapper;
import com.vicious.omega.event.impl.OmegaEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.net.InetSocketAddress;
import java.util.UUID;

public class PlayerLoginEvent extends PluginAPIEventWrapper<PlayerLoginEvent, ClientConnectionEvent.Login,org.bukkit.event.player.PlayerLoginEvent> implements OmegaEvent {
    public PlayerLoginEvent(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public UUID getPlayerUUID(){
        if(sponge()) return asSponge().getProfile().getUniqueId();
        else if(bukkit()) return asBukkit().getPlayer().getUniqueId();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public InetSocketAddress getAddress(){
        if(sponge()) return asSponge().getConnection().getAddress();
        else if(bukkit()) return asBukkit().getPlayer().getAddress();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getPlayerName(){
        if(sponge()) return asSponge().getProfile().getName().orElse(null);
        else if(bukkit()) return asBukkit().getPlayer().getName();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void cancel(boolean cancelled){
        if(sponge()) asSponge().setCancelled(cancelled);
        else if(bukkit()) asBukkit().setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER);
        else throw new UnsupportedEnvironmentException();
    }
}

package com.vicious.omega.event.player;

import com.vicious.omega.entity.player.Player;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.event.impl.PluginAPIEventWrapper;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 * Todo: add more methods.
 */
public class PlayerJoinEvent extends PluginAPIEventWrapper<PlayerJoinEvent, ClientConnectionEvent.Join, org.bukkit.event.player.PlayerJoinEvent> {
    public PlayerJoinEvent(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public Player getPlayer(){
        if(sponge()) return new Player(asSponge().getTargetEntity());
        else if(bukkit()) return new Player(asBukkit().getPlayer());
        else throw new UnsupportedEnvironmentException();
    }
}

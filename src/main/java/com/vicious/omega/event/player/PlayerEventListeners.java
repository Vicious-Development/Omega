package com.vicious.omega.event.player;

import org.bukkit.event.EventHandler;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerEventListeners implements org.bukkit.event.Listener {
    @Listener
    public void spongeOnPlayerLogin(ClientConnectionEvent.Login ev){
        new PlayerLoginEvent(ev);
    }
    @EventHandler
    public void bukkitOnPlayerLogin(org.bukkit.event.player.PlayerLoginEvent ev){
        new PlayerLoginEvent(ev);
    }
}

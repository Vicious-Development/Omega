package com.vicious.omega.event.bukkit;

import com.vicious.omega.event.omega.PlayerJoinEvent;
import com.vicious.omega.event.omega.PlayerLoginEvent;
import org.bukkit.event.EventHandler;

public class PlayerEventListeners implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerLogin(org.bukkit.event.player.PlayerLoginEvent ev){
        new PlayerLoginEvent(ev);
    }
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent ev){
        new PlayerJoinEvent(ev);
    }
}

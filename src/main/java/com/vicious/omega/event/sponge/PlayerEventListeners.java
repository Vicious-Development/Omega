package com.vicious.omega.event.sponge;

import com.vicious.omega.event.omega.PlayerJoinEvent;
import com.vicious.omega.event.omega.PlayerLoginEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerEventListeners {
    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login ev){
        new PlayerLoginEvent(ev);
    }
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join ev){
        new PlayerJoinEvent(ev);
    }
}

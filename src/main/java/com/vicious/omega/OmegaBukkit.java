package com.vicious.omega;

import com.vicious.omega.event.player.PlayerEventListeners;
import org.bukkit.plugin.java.JavaPlugin;

public class OmegaBukkit extends JavaPlugin {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerEventListeners(), this);
        Omega.getOmega().initPlugins();
    }
}

package com.vicious.omega;

import com.google.inject.Inject;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.logging.OmegaLogger;
import com.vicious.omega.server.Server;
import org.slf4j.Logger;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = "omega", name = "Omega", version = "@version@")
public class Omega {
    private static Omega OMEGA;

    @EnvironmentCompatibility(Environment.SPONGE)
    @Inject
    public PluginContainer spongePluginContainer;

    public Omega(){
        OMEGA=this;
        try {
            Class.forName("org.spongepowered.api.Sponge");
            Environment.SPONGE.setActive();
        } catch (ClassNotFoundException ignored) {}
        try {
            Class.forName("org.bukkit.Bukkit");
            Environment.BUKKIT.setActive();
        } catch (ClassNotFoundException ignored) {}
    }

    @EnvironmentCompatibility(Environment.SPONGE)
    public static Plugin asSponge() {
        return (Plugin)getOmega();
    }
    @EnvironmentCompatibility(Environment.SPONGE)
    public static Logger spongeLogger(){
        return getOmega().spongePluginContainer.getLogger();
    }

    public static Omega getOmega(){
        return OMEGA;
    }
    public static Server getServer(){
        return Server.getServer();
    }
    public static OmegaLogger getLogger(){
        return OmegaLogger.getInstance();
    }
}

package com.vicious.omega;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.server.Server;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "omega", name = "Omega", version = "@version@")
public class Omega {
    private static Environment environment = Environment.RAWJAVA;
    public static Environment getEnvironment(){
        return environment;
    }
    public Omega(){
        try {
            Class.forName("org.spongepowered.api.Sponge");
            environment = Environment.SPONGE;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Server getServer(){
        return Server.getServer();
    }
}

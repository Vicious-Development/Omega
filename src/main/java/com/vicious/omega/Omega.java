package com.vicious.omega;

import com.google.inject.Inject;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.event.impl.EventHandlers;
import com.vicious.omega.event.impl.OmegaListener;
import com.vicious.omega.event.player.PlayerEventListeners;
import com.vicious.omega.event.plugin.PluginInitializationEvent;
import com.vicious.omega.logging.OmegaLogger;
import com.vicious.omega.plugin.OmegaPluginImplementation;
import com.vicious.omega.plugin.PluginCollector;
import com.vicious.omega.plugin.PluginManager;
import com.vicious.viciouslib.util.reflect.deep.DeepReflection;
import com.vicious.viciouslib.util.reflect.deep.MethodSearchContext;
import com.vicious.viciouslib.util.reflect.deep.TotalFailureException;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveConstructor;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.lang.reflect.Modifier;

@Plugin(id = "omega", name = "Omega", version = "@version@")
public class Omega {
    private static Omega OMEGA;
    private static boolean pluginsInitialized = false;

    @EnvironmentCompatibility(Environment.SPONGE)
    @Inject
    public PluginContainer spongePluginContainer;

    public Omega() {
        OMEGA = this;
        EventHandlers.init();
        try {
            Class.forName("org.spongepowered.api.Sponge");
            Environment.SPONGE.setActive();
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("org.bukkit.Bukkit");
            Environment.BUKKIT.setActive();
        } catch (ClassNotFoundException ignored) {
        }
        if(Environment.BUKKIT.active() && Environment.SPONGE.active()){
            OmegaLogger.warn("Detected multiple minecraft plugin environments running! This could be unstable! Are you sure you know what you're doing?");
        }
        OmegaLogger.info("Omega started successfully");
        PluginCollector.collectAll();
    }

    @Listener
    public void spongeInit(GameStartedServerEvent event) {
        Sponge.getEventManager().registerListeners(this, new PlayerEventListeners());
        initPlugins();
    }

    /**
     *
     */
    void initPlugins() {
        if(pluginsInitialized) return;
        pluginsInitialized = true;
        OmegaPluginImplementation[] plugins = PluginManager.getPluginsInOrder();
        for (OmegaPluginImplementation plugin : plugins) {
            try {
                ReflectiveMethod initializer = DeepReflection.getMethod(plugin.getPluginClass(), new MethodSearchContext().accepts(PluginInitializationEvent.Self.class).annotated(OmegaListener.class));
                if (Modifier.isStatic(initializer.getReflectiveTarget(plugin.getPluginClass()).getModifiers())) {
                    OmegaLogger.error("Failed to load: " + plugin.getDetails().name() + " due to static initializer.");
                }
                ReflectiveConstructor constructor = new ReflectiveConstructor(new Class[]{});
                Object plgObj = constructor.construct();
                if (plgObj == null) {
                    OmegaLogger.error("Failed to load: " + plugin.getDetails().name() + " due to either missing default constructor!");
                } else {
                    initializer.invoke(plgObj, new PluginInitializationEvent.Self(plugin));
                    EventHandlers.dispatchEvent(new PluginInitializationEvent.Other(plugin));
                }
            } catch (TotalFailureException e) {
                OmegaLogger.error("Failed to load: " + plugin.getDetails().name() + " due to either missing or duplicate initializer.");
                e.printStackTrace();
            }
        }
    }

    @EnvironmentCompatibility(Environment.SPONGE)
    public static Plugin asSponge() {
        return (Plugin) getOmega();
    }

    @EnvironmentCompatibility(Environment.SPONGE)
    public static Logger spongeLogger() {
        return getOmega().spongePluginContainer.getLogger();
    }

    public static Omega getOmega() {
        return OMEGA;
    }
}

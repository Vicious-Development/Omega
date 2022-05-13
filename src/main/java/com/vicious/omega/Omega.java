package com.vicious.omega;

import com.google.inject.Inject;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.event.impl.EventHandlers;
import com.vicious.omega.event.impl.OmegaListener;
import com.vicious.omega.event.plugin.PluginInitializationEvent;
import com.vicious.omega.event.sponge.PlayerEventListeners;
import com.vicious.omega.logging.OmegaLogger;
import com.vicious.omega.plugin.OmegaPluginImplementation;
import com.vicious.omega.plugin.PluginCollector;
import com.vicious.omega.plugin.PluginManager;
import com.vicious.viciouslib.util.reflect.deep.DeepReflection;
import com.vicious.viciouslib.util.reflect.deep.MethodSearchContext;
import com.vicious.viciouslib.util.reflect.deep.TotalFailureException;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveConstructor;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;
import java.lang.reflect.Modifier;

@Plugin(id = "omega", name = "Omega", version = "@version@")
public class Omega {
    private static Omega OMEGA;
    private static boolean pluginsInitialized = false;

    @EnvironmentCompatibility(Environment.SPONGE)
    @Inject
    public PluginContainer pluginContainer;
    @Inject
    @DefaultConfig(sharedRoot = true)
    private File defaultConfig;
    @Inject
    private Game game;
    public Omega() {
        OMEGA = this;
        try {
            Class.forName("org.spongepowered.api.Sponge");
            Environment.SPONGE.setActive();
            OmegaLogger.info("Detected running in the Sponge environment: " + Environment.SPONGE.active());
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("org.bukkit.Bukkit");
            Environment.BUKKIT.setActive();
            OmegaLogger.info("Detected running in the Bukkit environment: " + Environment.BUKKIT.active());
        } catch (ClassNotFoundException ignored) {
        }
        if(Environment.BUKKIT.active() && Environment.SPONGE.active()){
            OmegaLogger.warn("Detected multiple minecraft plugin environments running! This could be unstable! Are you sure you know what you're doing?");
        }
        OmegaLogger.info("Omega started successfully");
        EventHandlers.init();
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
            OmegaLogger.info("Attempting to load plugin: " + plugin.getDetails().name());
            try {
                ReflectiveMethod initializer = DeepReflection.getMethod(plugin.getPluginClass(), new MethodSearchContext().accepts(PluginInitializationEvent.Self.class).annotated(OmegaListener.class));
                if (Modifier.isStatic(initializer.getReflectiveTarget(plugin.getPluginClass()).getModifiers())) {
                    OmegaLogger.error("Failed to load: " + plugin.getDetails().name() + " due to static initializer.");
                }
                ReflectiveConstructor constructor = new ReflectiveConstructor(new Class[]{});
                Object plgObj = constructor.construct(plugin.getPluginClass());
                if (plgObj == null) {
                    OmegaLogger.error("Failed to load: " + plugin.getDetails().name() + " due to missing default constructor!");
                } else {
                    OmegaLogger.info("Initializing " + plugin.getDetails().name());
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

    public static Omega getOmega() {
        return OMEGA;
    }
}

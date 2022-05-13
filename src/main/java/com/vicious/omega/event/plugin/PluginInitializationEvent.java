package com.vicious.omega.event.plugin;

import com.vicious.omega.event.impl.OmegaEvent;
import com.vicious.omega.plugin.OmegaPluginImplementation;

public class PluginInitializationEvent implements OmegaEvent {
    private final OmegaPluginImplementation plugin;
    public PluginInitializationEvent(OmegaPluginImplementation plugin){
        this.plugin=plugin;
    }
    public static class Self extends PluginInitializationEvent{
        public Self(OmegaPluginImplementation plugin) {
            super(plugin);
        }
    }
    public static class Other extends PluginInitializationEvent{
        public Other(OmegaPluginImplementation plugin) {
            super(plugin);
        }
    }
}

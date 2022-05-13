package com.vicious.omega.plugin;

import com.vicious.omega.environment.OmegaAPIWrapper;

public class Plugin extends OmegaAPIWrapper<Plugin,OmegaPluginImplementation, org.spongepowered.api.plugin.Plugin, org.bukkit.plugin.Plugin> {
    public Plugin(Object src) {
        super(src);
    }
}

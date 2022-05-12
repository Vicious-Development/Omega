package com.vicious.omega.plugin;

import com.vicious.omega.environment.PluginAPIWrapper;
import org.spongepowered.api.plugin.Plugin;

public class OmegaPlugin extends PluginAPIWrapper<OmegaPlugin,Plugin, org.bukkit.plugin.Plugin> {
    public OmegaPlugin(Object src) {
        super(src);
    }
}

package com.vicious.omega.world;

import com.vicious.omega.environment.PluginAPIWrapper;
import org.bukkit.World;


//TODO: WIP
public class WorldProperties extends PluginAPIWrapper<WorldProperties, org.spongepowered.api.world.storage.WorldProperties, World> {
    public WorldProperties(Object src) {
        super(src);
    }
}

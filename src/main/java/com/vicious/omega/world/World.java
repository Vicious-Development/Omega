package com.vicious.omega.world;

import com.google.common.collect.Lists;
import com.vicious.omega.entity.mob.Entity;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;

import java.util.Collection;
import java.util.List;

//TODO: WIP
public class World extends PluginAPIWrapper<World, org.spongepowered.api.world.World, org.bukkit.World> {
    public World(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public int getTotalEntities(){
        if(sponge()) return asSponge().getEntities().size();
        else if(bukkit()) return asBukkit().getEntities().size();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public int getTotalLoadedChunks(){
        if(sponge()) return Lists.newArrayList(asSponge().getLoadedChunks()).size();
        else if(bukkit()) return asBukkit().getLoadedChunks().length;
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public List<Chunk> getLoadedChunks(){
        if(sponge()) return wrapAsList(asSponge().getLoadedChunks(),Chunk::new);
        else if(bukkit()) return wrapAsList(asBukkit().getLoadedChunks(),Chunk::new);
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public Collection<Entity> getEntities() {
        if(sponge()) return wrap(asSponge().getEntities(),Entity::convert);
        else if(bukkit()) return wrap(asBukkit().getEntities(),Entity::convert);
        else throw new UnsupportedEnvironmentException();
    }
}

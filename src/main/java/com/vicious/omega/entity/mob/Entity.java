package com.vicious.omega.entity.mob;

import com.flowpowered.math.vector.Vector3d;
import com.vicious.omega.entity.player.Player;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.annotations.Wrapper;
import org.bukkit.Location;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.text.Text;

import java.util.UUID;


//TODO:impl
public class Entity<OMEGAENTITY extends Entity<OMEGAENTITY,SPONGEENTITY,BUKKITENTITY>,SPONGEENTITY extends org.spongepowered.api.entity.Entity,BUKKITENTITY extends org.bukkit.entity.Entity> extends PluginAPIWrapper<OMEGAENTITY,SPONGEENTITY,BUKKITENTITY> {
    @Wrapper
    public Entity(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public UUID getUUID(){
        if(Environment.SPONGE.active()) return asSponge().getUniqueId();
        else if(Environment.BUKKIT.active()) return asBukkit().getUniqueId();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getName(){
        if(Environment.SPONGE.active()) return asSponge().get(Keys.DISPLAY_NAME).orElse(Text.of("")).toPlainSingle();
        else if(Environment.BUKKIT.active()) return asBukkit().getName();
        else throw new UnsupportedEnvironmentException();
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Vector3d getPos(){
        if(Environment.SPONGE.active()) return asSponge().getLocation().getPosition();
        else if(Environment.BUKKIT.active()) {
            Location l = asBukkit().getLocation();
            return new Vector3d(l.getX(),l.getY(), l.getZ());
        }
        else throw new UnsupportedEnvironmentException();
    }

    @Wrapper
    public static Entity convert(Object o){
        if(o instanceof org.bukkit.entity.Player || o instanceof org.spongepowered.api.entity.living.player.Player) return new Player(o);
        else return new Entity<>(o);
    }
}

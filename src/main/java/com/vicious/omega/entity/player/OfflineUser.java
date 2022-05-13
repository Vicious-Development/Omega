package com.vicious.omega.entity.player;

import com.flowpowered.math.vector.Vector3d;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.inventory.Inventory;
import org.bukkit.OfflinePlayer;
import org.spongepowered.api.entity.living.player.User;

import java.util.UUID;

//TODO:impl
public class OfflineUser extends PluginAPIWrapper<OfflineUser, User, OfflinePlayer> {
    public OfflineUser(Object src) {
        super(src);
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public String getName(){
        if(sponge()) return asSponge().getName();
        else if(bukkit()) return asBukkit().getName();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public boolean isOnline(){
        if(sponge()) return asSponge().isOnline();
        else if(bukkit()) return asBukkit().isOnline();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public Player getPlayer(){
        if(sponge()) return new Player(asSponge().getPlayer().orElse(null));
        else if(bukkit()) return new Player(asBukkit().getPlayer());
        else throw new UnsupportedEnvironmentException();
    }

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public Vector3d getPosition(){
        if(sponge()) return asSponge().getPosition();
        else throw new UnsupportedEnvironmentException();
    }

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public UUID getWorldUUID(){
        if(sponge()) return asSponge().getWorldUniqueId().orElse(null);
        else throw new UnsupportedEnvironmentException();
    }

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public boolean setLocation(Vector3d pos, UUID world){
        if(sponge()) return asSponge().setLocation(pos,world);
        else throw new UnsupportedEnvironmentException();
    }

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public void setLocation(Vector3d rotation){
        if(sponge()) asSponge().setRotation(rotation);
        else throw new UnsupportedEnvironmentException();
    }

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public Vector3d getRotation(){
        if(sponge()) return asSponge().getRotation();
        else throw new UnsupportedEnvironmentException();
    }

    //TODO: implement Sponge getStatisticData()

    //todo: add bukkit implementation
    @EnvironmentCompatibility({Environment.SPONGE})
    public Inventory getEnderChestInventory(){
        if(sponge()) return new Inventory(asSponge().getEnderChestInventory());
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public UUID getUUID(){
        if(sponge()) return asSponge().getUniqueId();
        else if(bukkit()) return asBukkit().getUniqueId();
        else throw new UnsupportedEnvironmentException();
    }
}

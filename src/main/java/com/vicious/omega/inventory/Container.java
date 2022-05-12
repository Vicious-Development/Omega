package com.vicious.omega.inventory;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.player.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collection;

/**
 * Complete
 */
public class Container extends PluginAPIWrapper<Container, org.spongepowered.api.item.inventory.Container, InventoryHolder> {
    public Container(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.SPIGOT})
    public Collection<Player> getViewers(){
        if(Environment.SPONGE.active()) return wrap(asSponge().getViewers(),Player::new);
        else if(Environment.BUKKIT.active()) return wrap(asBukkit().getInventory().getViewers(),Player::new);
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.SPIGOT})
    public boolean hasViewers(){
        if(Environment.SPONGE.active()) return asSponge().hasViewers();
        else if(Environment.BUKKIT.active()) return !asBukkit().getInventory().getViewers().isEmpty();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.SPIGOT})
    public void open(Player viewer){
        if(Environment.SPONGE.active()) asSponge().open(viewer.asSponge());
        else if(Environment.BUKKIT.active()) viewer.asBukkit().openInventory(asBukkit().getInventory());
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.SPIGOT})
    public void close(Player viewer){
        if(Environment.SPONGE.active()) asSponge().close(viewer.asSponge());
            //Not really the same method logically but this is what we are limited to :/ Let's be fair though, the player should always have one inventory open... right?
        else if(Environment.BUKKIT.active()) viewer.asBukkit().closeInventory();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.SPIGOT})
    public boolean isViewedSlot(Slot slot){
        if(Environment.SPONGE.active()) return asSponge().isViewedSlot((org.spongepowered.api.item.inventory.Slot) slot.asSponge());
        else if(Environment.BUKKIT.active()) return !(slot.asBukkit().getHolder() instanceof org.bukkit.entity.Player);
        else throw new UnsupportedEnvironmentException();
    }
}

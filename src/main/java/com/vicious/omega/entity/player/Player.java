package com.vicious.omega.entity.player;

import com.vicious.omega.entity.mob.Entity;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.annotations.NullSafe;
import com.vicious.omega.environment.annotations.Wrapper;
import org.spongepowered.api.text.serializer.TextSerializers;


//TODO:impl
public class Player extends Entity<Player,org.spongepowered.api.entity.living.player.Player, org.bukkit.entity.Player> {
    @Wrapper
    public Player(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getHostAddress() {
        if(Environment.SPONGE.active()) asSponge().getConnection().getAddress().getAddress().getHostAddress();
        else if(Environment.BUKKIT.active()) asBukkit().getAddress().getAddress().getHostAddress();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getName(){
        if(Environment.SPONGE.active()) asSponge().getName();
        else if(Environment.BUKKIT.active()) asBukkit().getName();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean hasPermission(String permission){
        if(Environment.SPONGE.active()) asSponge().hasPermission(permission);
        else if(Environment.BUKKIT.active()) asBukkit().hasPermission(permission);
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean sendMessage(String message){
        if(Environment.SPONGE.active()) asSponge().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
        else if(Environment.BUKKIT.active()) asBukkit().sendMessage(message);
        throw new UnsupportedEnvironmentException();
    }

    @NullSafe
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void kick(String message){
        nullSafe(()->{
            if(Environment.SPONGE.active()) asSponge().kick(TextSerializers.FORMATTING_CODE.deserialize(message));
            else if(Environment.BUKKIT.active()) asBukkit().kickPlayer(message);
            throw new UnsupportedEnvironmentException();
        });
    }
}

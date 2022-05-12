package com.vicious.omega.server;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.player.Player;
import com.vicious.omega.world.World;
import com.vicious.omega.world.WorldProperties;
import org.bukkit.Bukkit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Collection;
import java.util.UUID;


public class Server extends PluginAPIWrapper<Server,org.spongepowered.api.Server, org.bukkit.Server> {
    private static Server instance;

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public static Server getServer(){
        if(instance == null){
            if(Environment.SPONGE.active()) instance = new Server(Sponge.getServer());
            else if(Environment.BUKKIT.active()) instance = new Server(Bukkit.getServer());
            throw new UnsupportedEnvironmentException();
        }
        return instance;
    }

    private Server(Object source){
        super(source);
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Player getPlayer(UUID uuid){
        if(Environment.SPONGE.active()){
            return new Player(asSponge().getPlayer(uuid));
        }
        if(Environment.BUKKIT.active()) return new Player(asBukkit().getPlayer(uuid));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Player getPlayer(String name){
        if(Environment.SPONGE.active()){
            return new Player(asSponge().getPlayer(name));
        }
        else if(Environment.BUKKIT.active()) return new Player(asBukkit().getPlayer(name));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getMaxPlayers(){
        if(Environment.SPONGE.active()) return asSponge().getMaxPlayers();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<Player> getOnlinePlayers() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getOnlinePlayers(),Player::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getOnlinePlayers(),Player::new);
        }
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<World> getWorlds() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getWorlds(), World::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getWorlds(),World::new);
        }
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<WorldProperties> getWorldProperties() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getAllWorldProperties(), WorldProperties::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getWorlds(),WorldProperties::new);
        }
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public World getWorld(UUID uuid){
        if(Environment.SPONGE.active()) return new World(asSponge().getWorld(uuid));
        else if(Environment.BUKKIT.active()) return new World(asBukkit().getWorld(uuid));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public World getWorld(String name){
        if(Environment.SPONGE.active()) return new World(asSponge().getWorld(name));
        else if(Environment.BUKKIT.active()) return new World(asBukkit().getWorld(name));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public WorldProperties getDefaultWorld(){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getDefaultWorld());
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public String getDefaultWorldName(){
        if(Environment.SPONGE.active()) return asSponge().getDefaultWorldName();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public World loadWorld(String name){
        if(Environment.SPONGE.active()) return new World(asSponge().loadWorld(name));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public World loadWorld(UUID uuid){
        if(Environment.SPONGE.active()) return new World(asSponge().loadWorld(uuid));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public WorldProperties getWorldProperties(String name){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getWorldProperties(name));
        if(Environment.BUKKIT.active()) return new WorldProperties(asBukkit().getWorld(name));
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public WorldProperties getWorldProperties(UUID uuid){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getWorldProperties(uuid));
        if(Environment.BUKKIT.active()) return new WorldProperties(asBukkit().getWorld(uuid));
        throw new UnsupportedEnvironmentException();
    }

    //TODO: Implement Sponge unload world

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Scoreboard getScoreboard(){
        if(Environment.SPONGE.active()) return new Scoreboard(asSponge().getServerScoreboard());
        if(Environment.BUKKIT.active()) return new Scoreboard(asBukkit().getScoreboardManager().getMainScoreboard());
        throw new UnsupportedEnvironmentException();
    }

    //TODO Impl sponge chunk layout

    //TODO Impl sponge getRunningTimeTicks

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void broadcast(String serializedText){
        if(Environment.SPONGE.active()) asSponge().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize(serializedText));
        else if(Environment.BUKKIT.active()) asBukkit().broadcastMessage(serializedText);
        else throw new UnsupportedEnvironmentException();
    }

    //TODO Impl sponge setBroadcastChannel

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getIP(String serializedText){
        if(Environment.SPONGE.active()) asSponge().getBoundAddress().get().toString();
        else if(Environment.BUKKIT.active()) asBukkit().getIp();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean hasWhitelist(){
        if(Environment.SPONGE.active()) asSponge().hasWhitelist();
        else if(Environment.BUKKIT.active()) asBukkit().hasWhitelist();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void setHasWhitelist(boolean enabled){
        if(Environment.SPONGE.active()) asSponge().setHasWhitelist(enabled);
        else if(Environment.BUKKIT.active()) asBukkit().setWhitelist(enabled);
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean getOnlineMode(){
        if(Environment.SPONGE.active()) asSponge().getOnlineMode();
        else if(Environment.BUKKIT.active()) asBukkit().getOnlineMode();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getMotd(){
        if(Environment.SPONGE.active()) TextSerializers.FORMATTING_CODE.serialize(asSponge().getMotd());
        else if(Environment.BUKKIT.active()) asBukkit().getMotd();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void shutdown(){
        if(Environment.SPONGE.active()) asSponge().shutdown();
        else if(Environment.BUKKIT.active()) asBukkit().shutdown();
        else System.exit(0);
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void shutdown(String message){
        if(Environment.SPONGE.active()) asSponge().shutdown(TextSerializers.FORMATTING_CODE.deserialize(message));
        //Todo allow bukkit to shutdown with msg.
        else if(Environment.BUKKIT.active()) asBukkit();
        else System.exit(0);
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void getConsole(){
        if(Environment.SPONGE.active()) new Console(asSponge().getConsole());
        else if(Environment.BUKKIT.active()) new Console(asBukkit().getConsoleSender());
        throw new UnsupportedEnvironmentException();
    }

    //TODO Implement Sponge getChunkTicketManager

    //TODO Implement Sponge getGameProfileManager

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public double getTPS(){
        if(Environment.SPONGE.active()) asSponge().getTicksPerSecond();
            //TODO use a better measurement
        else if(Environment.BUKKIT.active()) asBukkit().getTicksPerAnimalSpawns();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getIdleTimeout(){
        if(Environment.SPONGE.active()) asSponge().getPlayerIdleTimeout();
        else if(Environment.BUKKIT.active()) asBukkit().getIdleTimeout();
        throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void setIdleTimeout(int timeout){
        if(Environment.SPONGE.active()) asSponge().setPlayerIdleTimeout(timeout);
        else if(Environment.BUKKIT.active()) asBukkit().setIdleTimeout(timeout);
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean isMainThread(){
        if(Environment.SPONGE.active()) return asSponge().isMainThread();
        else if(Environment.BUKKIT.active()) return asBukkit().isPrimaryThread();
        else throw new UnsupportedEnvironmentException();
    }
}

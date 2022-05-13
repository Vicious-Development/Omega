package com.vicious.omega.server;

import com.vicious.omega.entity.mob.Entity;
import com.vicious.omega.entity.player.Player;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.world.Chunk;
import com.vicious.omega.world.World;
import com.vicious.omega.world.WorldProperties;
import org.bukkit.Bukkit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class Server extends PluginAPIWrapper<Server,org.spongepowered.api.Server, org.bukkit.Server> {
    private static Server instance;

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public static Server getServer(){
        if(instance == null){
            if(Environment.SPONGE.active()) instance = new Server(Sponge.getServer());
            else if(Environment.BUKKIT.active()) instance = new Server(Bukkit.getServer());
            else throw new UnsupportedEnvironmentException();
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
        else if(Environment.BUKKIT.active()) return new Player(asBukkit().getPlayer(uuid));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Player getPlayer(String name){
        if(Environment.SPONGE.active()){
            return new Player(asSponge().getPlayer(name));
        }
        else if(Environment.BUKKIT.active()) return new Player(asBukkit().getPlayer(name));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getMaxPlayers(){
        if(Environment.SPONGE.active()) return asSponge().getMaxPlayers();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<Player> getOnlinePlayers() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getOnlinePlayers(),Player::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getOnlinePlayers(),Player::new);
        }
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<World> getWorlds() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getWorlds(), World::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getWorlds(),World::new);
        }
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Collection<WorldProperties> getWorldProperties() {
        if(Environment.SPONGE.active()){
            return wrap(asSponge().getAllWorldProperties(), WorldProperties::new);
        }
        else if(Environment.BUKKIT.active()){
            return wrap(asBukkit().getWorlds(),WorldProperties::new);
        }
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public World getWorld(UUID uuid){
        if(Environment.SPONGE.active()) return new World(asSponge().getWorld(uuid));
        else if(Environment.BUKKIT.active()) return new World(asBukkit().getWorld(uuid));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public World getWorld(String name){
        if(Environment.SPONGE.active()) return new World(asSponge().getWorld(name));
        else if(Environment.BUKKIT.active()) return new World(asBukkit().getWorld(name));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public WorldProperties getDefaultWorld(){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getDefaultWorld());
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public String getDefaultWorldName(){
        if(Environment.SPONGE.active()) return asSponge().getDefaultWorldName();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public World loadWorld(String name){
        if(Environment.SPONGE.active()) return new World(asSponge().loadWorld(name));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE})
    public World loadWorld(UUID uuid){
        if(Environment.SPONGE.active()) return new World(asSponge().loadWorld(uuid));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public WorldProperties getWorldProperties(String name){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getWorldProperties(name));
        else if(Environment.BUKKIT.active()) return new WorldProperties(asBukkit().getWorld(name));
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public WorldProperties getWorldProperties(UUID uuid){
        if(Environment.SPONGE.active()) return new WorldProperties(asSponge().getWorldProperties(uuid));
        else if(Environment.BUKKIT.active()) return new WorldProperties(asBukkit().getWorld(uuid));
        else throw new UnsupportedEnvironmentException();
    }

    //TODO: Implement Sponge unload world

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Scoreboard getScoreboard(){
        if(Environment.SPONGE.active()) return new Scoreboard(asSponge().getServerScoreboard());
        else if(Environment.BUKKIT.active()) return new Scoreboard(asBukkit().getScoreboardManager().getMainScoreboard());
        else throw new UnsupportedEnvironmentException();
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
        if(Environment.SPONGE.active()) return asSponge().getBoundAddress().get().toString();
        else if(Environment.BUKKIT.active()) return asBukkit().getIp();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean hasWhitelist(){
        if(Environment.SPONGE.active()) return asSponge().hasWhitelist();
        else if(Environment.BUKKIT.active()) return asBukkit().hasWhitelist();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void setHasWhitelist(boolean enabled){
        if(Environment.SPONGE.active()) asSponge().setHasWhitelist(enabled);
        else if(Environment.BUKKIT.active()) asBukkit().setWhitelist(enabled);
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean getOnlineMode(){
        if(Environment.SPONGE.active()) return asSponge().getOnlineMode();
        else if(Environment.BUKKIT.active()) return asBukkit().getOnlineMode();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getMotd(){
        if(Environment.SPONGE.active()) return TextSerializers.FORMATTING_CODE.serialize(asSponge().getMotd());
        else if(Environment.BUKKIT.active()) return asBukkit().getMotd();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void shutdown(){
        if(Environment.SPONGE.active()) asSponge().shutdown();
        else if(Environment.BUKKIT.active()) asBukkit().shutdown();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void shutdown(String message){
        if(Environment.SPONGE.active()) asSponge().shutdown(TextSerializers.FORMATTING_CODE.deserialize(message));
        //Todo allow bukkit to shutdown with msg.
        else if(Environment.BUKKIT.active()) asBukkit();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void getConsole(){
        if(Environment.SPONGE.active()) new Console(asSponge().getConsole());
        else if(Environment.BUKKIT.active()) new Console(asBukkit().getConsoleSender());
        else throw new UnsupportedEnvironmentException();
    }

    //TODO Implement Sponge getChunkTicketManager

    //TODO Implement Sponge getGameProfileManager

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public double getTPS(){
        if(Environment.SPONGE.active()) return asSponge().getTicksPerSecond();
            //TODO use a better measurement
        else if(Environment.BUKKIT.active()) return asBukkit().getTicksPerAnimalSpawns();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getIdleTimeout(){
        if(Environment.SPONGE.active()) return asSponge().getPlayerIdleTimeout();
        else if(Environment.BUKKIT.active()) return asBukkit().getIdleTimeout();
        else throw new UnsupportedEnvironmentException();
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

    /**
     * @return total number of chunks loaded in every world.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getTotalLoadedChunks(){
        int total=0;
        if(Environment.SPONGE.active()) {
            for (World world : getWorlds()) {
                total+=world.getTotalLoadedChunks();
            }
        }
        else if(Environment.BUKKIT.active()) {
            for (org.bukkit.World world : asBukkit().getWorlds()) {
                total+=world.getLoadedChunks().length;
            }
        }
        else throw new UnsupportedEnvironmentException();
        return total;
    }

    /**
     * @return all chunks loaded in all worlds.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public List<Chunk> getLoadedChunks(){
        List<Chunk> chunks = new ArrayList<>();
        for (World world : getWorlds()) {
            chunks.addAll(world.getLoadedChunks());
        }
        return chunks;
    }

    /**
     * @return gets the total number of entities on the server.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getTotalEntities(){
        int total=0;
        if(Environment.SPONGE.active()) {
            for (org.spongepowered.api.world.World world : asSponge().getWorlds()) {
                total+=world.getEntities().size();
            }
        }
        else if(Environment.BUKKIT.active()) {
            for (org.bukkit.World world : asBukkit().getWorlds()) {
                total+=world.getEntities().size();
            }
        }
        else throw new UnsupportedEnvironmentException();
        return total;
    }

    /**
     * @return gets every entity on the server.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public List<Entity> getEntities(){
        List<Entity> entities = new ArrayList<>();
        for (World world : getWorlds()) {
            entities.addAll(world.getEntities());
        }
        return entities;
    }

    /**
     * Returns a player's UUID regardless of if they are online or not.
     * @param id - the uuid to look for.
     * @return the player name
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public String getNameOfPlayerWithUUID(UUID id){
        if(sponge()) {
            try {
                return asSponge().getGameProfileManager().get(id).get().getName().get();
            } catch (Exception e) {
                return null;
            }
        }
        else if(bukkit()) return asBukkit().getOfflinePlayer(id).getName();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Path getGameDir(){
        if(sponge()) return Sponge.getGame().getGameDirectory();
        else if(bukkit()) return asBukkit().getWorldContainer().toPath();
        else throw new UnsupportedEnvironmentException();
    }
}


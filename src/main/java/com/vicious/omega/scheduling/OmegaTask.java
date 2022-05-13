package com.vicious.omega.scheduling;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.MultiEnvironment;
import com.vicious.omega.plugin.Plugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.Future;

public class OmegaTask implements MultiEnvironment {
    private Plugin owner;
    private Object src;
    public OmegaTask(Object src){
        this.src=src;
    }
    public void setOwner(Plugin owner){
        this.owner=owner;
    }
    public void cancel(){
        if(Environment.SPONGE.active()) asSponge().cancel();
        else asJava().cancel(false);
        Scheduler.remove(this);
    }
    public boolean isCancelled(){
        if(Environment.SPONGE.active()) return !Sponge.getScheduler().getScheduledTasks().contains(asSponge());
        else return asJava().isCancelled();
    }
    public boolean isDone(){
        if(Environment.SPONGE.active()) return !Sponge.getScheduler().getScheduledTasks().contains(asSponge());
        else return asJava().isDone();
    }

    @EnvironmentCompatibility(Environment.SPONGE)
    public Task asSponge(){
        return (Task) src;
    }
    @EnvironmentCompatibility(Environment.SPONGE)
    public Future<?> asJava(){
        return (Future<?>) src;
    }

    public Plugin getOwner() {
        return owner;
    }
}

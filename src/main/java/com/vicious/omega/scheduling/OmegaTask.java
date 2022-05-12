package com.vicious.omega.scheduling;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.EnvironmentSpecific;
import com.vicious.omega.environment.UnusableEnvironmentException;
import com.vicious.omega.plugin.OmegaPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public class OmegaTask implements EnvironmentSpecific {
    private OmegaPlugin owner;
    private Object src;
    public OmegaTask(Object src){
        this.src=src;
    }
    public void setOwner(OmegaPlugin owner){
        this.owner=owner;
    }
    public void cancel(){
        if(activeIn(Environment.SPONGE)) asSponge().cancel();
        else asJava().cancel(false);
        Scheduler.remove(this);
    }
    public boolean isCancelled(){
        if(activeIn(Environment.SPONGE)) return !Sponge.getScheduler().getScheduledTasks().contains(asSponge());
        else return asJava().isCancelled();
    }
    public boolean isDone(){
        if(activeIn(Environment.SPONGE)) return !Sponge.getScheduler().getScheduledTasks().contains(asSponge());
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

    public OmegaPlugin getOwner() {
        return owner;
    }
}

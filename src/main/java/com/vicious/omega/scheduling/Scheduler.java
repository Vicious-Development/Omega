package com.vicious.omega.scheduling;

import com.vicious.omega.Omega;
import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.EnvironmentSpecific;
import com.vicious.omega.environment.UnusableEnvironmentException;
import com.vicious.omega.plugin.OmegaPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//Todo: For non-sponge environments implement a async executor service.
public class Scheduler implements EnvironmentSpecific {
    private static Map<OmegaPlugin, IdentityHashMap<OmegaTask,OmegaTask>> taskMap = new HashMap<>();
    private static ScheduledExecutorService syncScheduler = Executors.newScheduledThreadPool(1);
    public static OmegaTask scheduleAtFixedRate(long interval, long initialDelay, TimeUnit unit, OmegaPlugin plugin, Runnable command){
        if(EnvironmentSpecific.active(Environment.SPONGE)) return add(new OmegaTask(Sponge.getScheduler().createTaskBuilder().interval(interval, unit).delay(initialDelay,unit).execute(command).submit(plugin.asSponge())),plugin);
        return add(new OmegaTask(syncScheduler.scheduleAtFixedRate(command,initialDelay,interval,unit)),plugin);
    }
    public static OmegaTask schedule(long delay, TimeUnit unit, OmegaPlugin plugin, Runnable command){
        if(EnvironmentSpecific.active(Environment.SPONGE)) return add(new OmegaTask(Sponge.getScheduler().createTaskBuilder().delay(delay,unit).execute(command).submit(plugin.asSponge())),plugin);
        return add(new OmegaTask(syncScheduler.schedule(command,delay,unit)),plugin);
    }

    public static OmegaTask scheduleAtFixedRate(long interval, long initialDelay, TimeUnit unit, boolean async, OmegaPlugin plugin, Runnable command){
        if(EnvironmentSpecific.active(Environment.SPONGE)) {
            Task.Builder bld = Sponge.getScheduler().createTaskBuilder().delay(initialDelay,unit).interval(interval,unit).execute(command);
            if(async) bld.async();
            return add(new OmegaTask(bld.submit(plugin.asSponge())),plugin);
        }
        return add(new OmegaTask(syncScheduler.scheduleAtFixedRate(command,initialDelay,interval,unit)),plugin);
    }
    public static OmegaTask schedule(long delay, TimeUnit unit, OmegaPlugin plugin, boolean async, Runnable command){
        if(EnvironmentSpecific.active(Environment.SPONGE)) {
            Task.Builder bld = Sponge.getScheduler().createTaskBuilder().delay(delay,unit).execute(command);
            if(async) bld.async();
            return add(new OmegaTask(bld.submit(plugin.asSponge())),plugin);
        }
        else{
            return add(new OmegaTask(syncScheduler.schedule(command,delay,unit)),plugin);
        }
    }
    public static OmegaTask submit(boolean async, OmegaPlugin plugin, Runnable command){
        if(EnvironmentSpecific.active(Environment.SPONGE)){
            Task.Builder bld = Sponge.getScheduler().createTaskBuilder().execute(command);
            if(async) bld.async();
            return add(new OmegaTask(bld.submit(plugin.asSponge())),plugin);
        }
        else{
            return add(new OmegaTask(syncScheduler.submit(command)),plugin);
        }
    }
    private static OmegaTask add(OmegaTask task, OmegaPlugin owner){
        IdentityHashMap<OmegaTask,OmegaTask> tasks = taskMap.get(owner);
        if(tasks == null){
            tasks = new IdentityHashMap<>();
            taskMap.put(owner,tasks);
        }
        task.setOwner(owner);
        tasks.put(task,task);
        return task;
    }

    public static void remove(OmegaTask omegaTask) {
        taskMap.get(omegaTask.getOwner()).remove(omegaTask);
    }
    public static Set<OmegaTask> getActiveTasks(){
        Set<OmegaTask> ret = new HashSet<>();
        for (OmegaPlugin omegaPlugin : taskMap.keySet()) {
            ret.addAll(getActiveTasks(omegaPlugin));
        }
        return ret;
    }
    public static Set<OmegaTask> getActiveTasks(OmegaPlugin plugin){
        Set<OmegaTask> ret = new HashSet<>();
        Map<OmegaTask,OmegaTask> tasks = taskMap.get(plugin);
        if(tasks == null) return ret;
        ret.addAll(tasks.values());
        return ret;
    }
}
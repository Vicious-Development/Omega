package com.vicious.omega.event.impl;

import com.vicious.omega.event.impl.consumers.IEventConsumer;
import com.vicious.omega.event.player.PlayerLoginEvent;
import com.vicious.omega.event.plugin.PluginInitializationEvent;
import com.vicious.viciouslib.util.reflect.deep.DeepReflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventHandlers {
    private static Map<Class<?>,EventHandler<?>> handlers = new HashMap<>();
    public static void init(){
        addEventHandler(PlayerLoginEvent.class);
        addEventHandler(PluginInitializationEvent.Other.class);
    }
    public static <T extends OmegaEvent> void dispatchEvent(T ev){
        //Send event to all its
        DeepReflection.cycleAndExecute(ev.getClass(),(cls)->{
            //Ignore interfaces.
            if(cls.isInterface()) return null;
            boolean done = false;
            for (Class<?> inf : cls.getInterfaces()) {
                if (inf == OmegaEvent.class) {
                    done = true;
                }
            }
            EventHandler<T> handler = ((EventHandler<T>)handlers.get(cls));
            if(handler != null) handler.receiveEvent(ev);
            return null;
        });
    }
    public static <T extends OmegaEvent> void addEventHandler(Class<T> eventClass){
        handlers.put(eventClass, new EventHandler<>(eventClass));
    }
    public static void register(Class<?> cls){
        //Indicates that ALL event containing methods should be considered listeners.
        if(cls.isAnnotationPresent(OmegaListener.class)){
            for (Method mtd : cls.getDeclaredMethods()) {
                Class<?>[] params = mtd.getParameterTypes();
                //Cycle through all superclasses, if it has the OmegaEvent class return true.
                if(shouldRegister(mtd)) EventHandlers.register(cls,mtd);

            }
        }
        //Indicates that only methods marked with the annotation should be considered listeners.
        else {
            for (Method mtd : cls.getDeclaredMethods()) {
                if(!mtd.isAnnotationPresent(OmegaListener.class)) continue;
                if(shouldRegister(mtd)) EventHandlers.register(cls,mtd);
            }
        }
    }
    private static boolean shouldRegister(Method mtd){
        Class<?>[] params = mtd.getParameterTypes();
        //Cycle through all superclasses, if it has the OmegaEvent class return true.
        return params.length != 1 && DeepReflection.cycleAndExecute(params[0],(pcls)->{
            if(pcls == OmegaEvent.class){
                return true;
            }
            return null;
        }) != null;
    }
    public static <T extends OmegaEvent> void register(IEventConsumer<T> consumer, Class<T> eventClass){
        EventHandler<T> handler = (EventHandler<T>) handlers.get(eventClass);
        if(handler != null) handler.register(consumer);
    }
    private static void register(Object in, Method m){
        Class<?> handlerClass = m.getParameterTypes()[0];
        EventHandler handler = handlers.get(handlerClass);
        handler.register(in,m);
    }
}

package com.vicious.omega.event.impl;

import com.vicious.omega.event.impl.consumers.EventConsumer;
import com.vicious.omega.event.impl.consumers.IEventConsumer;
import com.vicious.omega.logging.OmegaLogger;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventHandler<T extends OmegaEvent> {
    private final Class<T> listeningTo;
    public EventHandler(Class<T> listeningTo){
        this.listeningTo=listeningTo;
    }
    //Map containing the event handlers and their respective executing objects.
    private final Map<Object, EventConsumer<T,?>> handlers = new HashMap<>();

    public void register(Object in, Method m) {
        handlers.put(in,EventConsumer.of(new ReflectiveMethod(m.getName(),listeningTo),listeningTo));
    }
    public void register(IEventConsumer<T> consumer){
        handlers.put(consumer,EventConsumer.of(consumer,listeningTo));
    }
    public void unregister(Object in){
        handlers.remove(in);
    }

    /**
     * Executes the event when received.
     * @param event
     */
    public void receiveEvent(T event){
        for (Object o : handlers.keySet()) {
            try {
                handlers.get(o).invoke(o, event);
            } catch (Exception e){
                OmegaLogger.error("Caught an exception while executing a " + event.getClass().getName() + "!");
                OmegaLogger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

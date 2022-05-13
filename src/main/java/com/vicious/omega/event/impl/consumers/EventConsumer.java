package com.vicious.omega.event.impl.consumers;

import com.vicious.omega.event.impl.OmegaEvent;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class EventConsumer<T extends OmegaEvent,C> {
    protected C consumer;
    public EventConsumer(C consumer){
        this.consumer=consumer;
    }
    public abstract void invoke(Object owner, T event) throws Exception;
    public static <T extends OmegaEvent> EventConsumer<T,?> of(@NonNull Object consumer, Class<T> event){
        if(consumer instanceof IEventConsumer<?>){
            return new LambdaEventConsumer<>((IEventConsumer<T>) consumer);
        }
        else if(consumer instanceof ReflectiveEventConsumer<?>){
            return new ReflectiveEventConsumer<T>((ReflectiveMethod) consumer);
        }
        else throw new IllegalArgumentException("Consumer is not of a recognized consumer type.");
    }
}

package com.vicious.omega.event.impl.consumers;

import com.vicious.omega.event.impl.OmegaEvent;
import com.vicious.viciouslib.util.reflect.wrapper.ReflectiveMethod;

public class ReflectiveEventConsumer<T extends OmegaEvent> extends EventConsumer<T, ReflectiveMethod>{
    public ReflectiveEventConsumer(ReflectiveMethod consumer) {
        super(consumer);
    }

    @Override
    public void invoke(Object owner, T event) {
        consumer.invoke(owner,event);
    }
}

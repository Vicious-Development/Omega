package com.vicious.omega.event.impl.consumers;

import com.vicious.omega.event.impl.OmegaEvent;

public class LambdaEventConsumer<T extends OmegaEvent> extends EventConsumer<T, IEventConsumer<T>>{
    public LambdaEventConsumer(IEventConsumer<T> consumer) {
        super(consumer);
    }

    @Override
    public void invoke(Object owner, T event) throws Exception{
        consumer.accept(event);
    }
}

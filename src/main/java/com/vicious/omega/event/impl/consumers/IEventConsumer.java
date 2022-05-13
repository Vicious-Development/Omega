package com.vicious.omega.event.impl.consumers;

import com.vicious.omega.event.impl.OmegaEvent;

@FunctionalInterface
public interface IEventConsumer<T extends OmegaEvent> {
    void accept(T event) throws Exception;
}

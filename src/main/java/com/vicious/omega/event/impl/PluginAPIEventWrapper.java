package com.vicious.omega.event.impl;

import com.vicious.omega.environment.PluginAPIWrapper;

public class PluginAPIEventWrapper<OMEGAEVENT extends PluginAPIEventWrapper<OMEGAEVENT,SPONGEEVENT,BUKKITEVENT>,SPONGEEVENT,BUKKITEVENT> extends PluginAPIWrapper<OMEGAEVENT,SPONGEEVENT,BUKKITEVENT> implements OmegaEvent {
    public PluginAPIEventWrapper(Object src) {
        super(src);
        EventHandlers.dispatchEvent(this);
    }
}

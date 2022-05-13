package com.vicious.omega.plugin;

import java.util.Arrays;

public class DuplicatePluginException extends Exception{
    private final OmegaPluginImplementation[] plugins;
    public DuplicatePluginException(OmegaPluginImplementation... plugins){
        this.plugins = plugins;
    }

    @Override
    public String getMessage() {
        return "Duplicate Plugins found: " + Arrays.toString(plugins);
    }
}

package com.vicious.omega.plugin;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.EnvironmentCompatibility;
import com.vicious.omega.environment.MultiEnvironment;
import org.spongepowered.api.plugin.Plugin;

import java.util.Objects;

public class OmegaPlugin implements MultiEnvironment {
    private Object src;
    public OmegaPlugin(Object src){
        this.src=src;
    }
    @EnvironmentCompatibility(Environment.SPONGE)
    public Plugin asSponge(){
        return (Plugin) src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmegaPlugin that = (OmegaPlugin) o;
        return Objects.equals(src, that.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src);
    }
}

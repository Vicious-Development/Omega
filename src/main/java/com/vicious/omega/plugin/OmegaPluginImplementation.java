package com.vicious.omega.plugin;

public class OmegaPluginImplementation {
    private OmegaPlugin details;
    private int loadOrder;
    private Class<?> pluginClass;
    private boolean loaded;

    public OmegaPluginImplementation(OmegaPlugin details, Class<?> pluginClass){
        this.details=details;
        this.pluginClass=pluginClass;
    }

    public void loaded(boolean b) {
        this.loaded=b;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public OmegaPlugin getDetails() {
        return details;
    }

    public Class<?> getPluginClass() {
        return pluginClass;
    }
    public int getLoadOrder(){
        return loadOrder;
    }

    public void setLoadOrder(int loadOrder) {
        this.loadOrder = loadOrder;
    }

    @Override
    public String toString() {
        return details.name() + ":\npid: " + details.id() + "\nversion: " + details.version();
    }
}

package com.vicious.omega.plugin;

public class MissingDependencyException extends Exception{
    private OmegaPluginImplementation plugin;
    public MissingDependencyException(OmegaPluginImplementation plugin){
        this.plugin=plugin;
    }

    @Override
    public String getMessage() {
        String missing = plugin.getDetails().name() + " is missing required dependencies: [";
        boolean comma = false;
        for (String dependency : plugin.getDetails().dependencies()) {
            if(comma) missing+=',';
            if(PluginManager.getPlugin(dependency) == null){
                comma = true;
                missing += dependency;
            }
        }
        missing+=']';
        return missing;
    }
}

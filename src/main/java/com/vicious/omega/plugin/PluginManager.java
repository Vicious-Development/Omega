package com.vicious.omega.plugin;

import com.vicious.omega.logging.OmegaLogger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginManager {
    private static Map<String,OmegaPluginImplementation> plugins = new HashMap<>();
    public static void addPlugin(OmegaPluginImplementation impl) throws DuplicatePluginException {
        if(plugins.containsKey(impl.getDetails().id())){
            throw new DuplicatePluginException(impl, plugins.get(impl.getDetails().id()));
        }
        else plugins.put(impl.getDetails().id(),impl);
    }

    public static OmegaPluginImplementation[] getPluginsInOrder() {
        List<OmegaPluginImplementation> plgList = new ArrayList<>();
        int i = 0;
        for (OmegaPluginImplementation value : plugins.values()) {
            value.setLoadOrder(i);
            plgList.add(value);
            i++;
        }
        for (OmegaPluginImplementation plugin : plgList) {
            for (String dependency : plugin.getDetails().dependencies()) {
                OmegaPluginImplementation depPlugin = plugins.get(dependency);
                if(depPlugin == null){
                    plugin.loaded(false);
                    MissingDependencyException ex = new MissingDependencyException(plugin);
                    OmegaLogger.error(ex.getMessage());
                    continue;
                }
                else if(!depPlugin.isLoaded()){
                    plugin.loaded(false);
                    OmegaLogger.error(depPlugin.getDetails().name() + " has already failed to load, causing " + plugin.getDetails().name() + " to fail to load as well.");
                    continue;
                }
                if(depPlugin.getLoadOrder() < plugin.getLoadOrder()){
                    int newOrder = depPlugin.getLoadOrder();
                    depPlugin.setLoadOrder(plugin.getLoadOrder());
                    plugin.setLoadOrder(newOrder);
                }
            }
        }
        for (int j = 0; j < plgList.size(); j++) {
            if(!plgList.get(i).isLoaded()){
                plgList.remove(i);
                i--;
            }
        }
        OmegaPluginImplementation[] result = new OmegaPluginImplementation[plgList.size()];
        for(OmegaPluginImplementation plugin : plgList){
            result[plugin.getLoadOrder()]=plugin;
        }
        return result;
    }
    @Nullable
    public static OmegaPluginImplementation getPlugin(String pluginId){
        return plugins.get(pluginId);
    }
}

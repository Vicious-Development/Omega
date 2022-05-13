package com.vicious.omega.plugin;

import com.vicious.omega.logging.OmegaLogger;
import com.vicious.omega.util.Directories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginCollector {
    public static boolean hasBeenCollected = false;

    /**
     * Finds all plugins and creates a wrapper object for them.
     */
    public static void collectAll(){
        if(hasBeenCollected) return;;
        hasBeenCollected = true;
        File pluginsDir = Directories.pluginsDir.toFile();
        for (File file : pluginsDir.listFiles()) {
            if(file.getName().endsWith(".jar")) {
                try{
                    OmegaPluginImplementation impl = processZip(file);
                    if(impl != null) PluginManager.addPlugin(impl);
                } catch (DuplicatePluginException e) {
                    OmegaLogger.error(e.getMessage());
                    OmegaLogger.error("Will not load one duplicate.");
                    e.printStackTrace();
                }
            }
        }
    }
    public static OmegaPluginImplementation processZip(File file) throws DuplicatePluginException{
        try {
            ZipFile zip = new ZipFile(file);
            ZipEntry pluginEntry = zip.getEntry("omegaplugin.info");
            InputStream infoStream = zip.getInputStream(pluginEntry);
            Scanner scan = new Scanner(infoStream);
            String mainClassName = scan.nextLine();
            scan.close();
            Class<?> plugin = Class.forName(mainClassName);
            OmegaPlugin plg = plugin.getAnnotation(OmegaPlugin.class);
            if(plg == null){
                OmegaLogger.error(zip.getName() + " plugin lacks an @OmegaPlugin annotation in main class: " + mainClassName + "!");
                return null;
            }
             return new OmegaPluginImplementation(plg,plugin);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Collects only plugins that aren't already in the PluginManager.
     * This will ignore duplicates.
     */
    public static List<OmegaPluginImplementation> collectNew(){
        File pluginsDir = Directories.pluginsDir.toFile();
        List<OmegaPluginImplementation> newPlugins = new ArrayList<>();
        for (File file : pluginsDir.listFiles()) {
            if(file.getName().endsWith(".jar")) {
                try{
                    OmegaPluginImplementation impl = processZip(file);
                    if(impl != null){
                        newPlugins.add(impl);
                    }
                } catch (DuplicatePluginException ignored) {}
            }
        }
        return newPlugins;
    }
}

package com.vicious.omega.server;

import com.vicious.omega.environment.PluginAPIWrapper;
import org.bukkit.command.ConsoleCommandSender;
import org.spongepowered.api.command.source.ConsoleSource;


//TODO WIP
public class Console extends PluginAPIWrapper<Console, ConsoleSource, ConsoleCommandSender> {
    public Console(Object src) {
        super(src);
    }
}

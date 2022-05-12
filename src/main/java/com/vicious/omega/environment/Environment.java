package com.vicious.omega.environment;

public enum Environment {
    SPONGE,
    BUKKIT,
    SPIGOT,
    PAPER,
    JDA,
    RAWJAVA;

    boolean active = false;
    public boolean active() {
        return active;
    }

    public void setActive() {
        active=true;
    }
}

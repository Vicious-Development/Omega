package com.vicious.omega.environment;

public enum Environment {
    SPONGE,
    BUKKIT,
    SPIGOT,
    BUNGEE,
    PAPER,
    JDA,
    OMEGA,
    RAWJAVA;

    boolean active = false;
    public boolean active() {
        return active;
    }

    public void setActive() {
        active=true;
    }

    @Override
    public String toString() {
        return name();
    }
}

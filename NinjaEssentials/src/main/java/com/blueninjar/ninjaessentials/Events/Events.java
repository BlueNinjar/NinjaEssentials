package com.blueninjar.ninjaessentials.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Copyright (c) 2017s. Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */
public class Events {

    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}

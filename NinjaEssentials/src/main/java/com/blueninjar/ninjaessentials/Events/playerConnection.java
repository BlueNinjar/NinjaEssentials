package com.blueninjar.ninjaessentials.Events;

import com.blueninjar.ninjaessentials.NinjaEssentials;
import com.blueninjar.ninjaessentials.commands.vanishCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright (c) 2017s. Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */
public class playerConnection implements Listener {
    NinjaEssentials plugin;


    public playerConnection(NinjaEssentials instance) {
        plugin = instance;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
     Player p = e.getPlayer();

     if(plugin.getConfig().getBoolean("CustomJoin") == true) {
         if (p.hasPlayedBefore()) {
             e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("hasPlayedMessage").replaceAll("%PLAYERNAME%", p.getName())));
         } else {
             e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("newPlayerMessage").replaceAll("%PLAYERNAME%", p.getName())));
         }
     }

        for (Player vanish : vanishCommand.vanished) {
            if (!e.getPlayer().hasPermission("ninjafactions.cansee.vanished")) {
                e.getPlayer().hidePlayer(vanish);
            }
        }

    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(vanishCommand.vanished.contains(e.getPlayer())) vanishCommand.vanished.remove(e.getPlayer());
        e.setQuitMessage((ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("playerQuitMessage").replaceAll("%PLAYERNAME%", p.getName()))));

    }
}

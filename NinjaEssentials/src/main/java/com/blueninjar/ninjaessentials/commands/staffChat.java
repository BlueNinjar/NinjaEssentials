package com.blueninjar.ninjaessentials.commands;

import com.blueninjar.ninjaessentials.NinjaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Copyright (c) 2017s. Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */
public class staffChat implements Listener, CommandExecutor {
    NinjaEssentials plugin;


    public staffChat(NinjaEssentials instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
        if (cmd.getName().equalsIgnoreCase("staff")) {
            if (!sender.hasPermission("ninjafactions.staffchat")) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPermMessage")));
                return true;
            }
            if (args.length >= 1) {
                String msg = "";
                int x = 1;
                for (String a : args) {
                    if (x == 0) {
                        x++;
                        continue;
                    }
                    msg = msg + " " + a;
                }
                msg = msg.trim();
                if (sender instanceof Player) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if (p.hasPermission("ninjafactions.staffchat.see")) {
                            p.sendMessage(ChatColor.RED + "[Staff Chat] " + ChatColor.GOLD + sender.getName() + ": " + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }
                }
                if (!(sender instanceof Player)) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if (p.hasPermission("ninjafactions.staffchat.see")) {
                            p.sendMessage(ChatColor.RED + "[Staff Chat]" + sender.getName() + ": " + ChatColor.GOLD + msg);
                        }
                    }
                }
            } else {
                sender.sendMessage("Usage: /staffchat <message>");
            }
        }
        return true;
    }
}

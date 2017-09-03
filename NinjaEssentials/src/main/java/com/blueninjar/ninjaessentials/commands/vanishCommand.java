package com.blueninjar.ninjaessentials.commands;

import com.blueninjar.ninjaessentials.API.HotBarAPI;
import com.blueninjar.ninjaessentials.NinjaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017s. Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */
public class vanishCommand implements CommandExecutor {
    public static List<Player> vanished = new ArrayList<>();
    public static int VanishHotBar;
    NinjaEssentials plugin;

    public vanishCommand(NinjaEssentials instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        if (p.hasPermission("ninjafactions.vanished") || p.isOp()) {

            if (args.length == 0) {
                if (vanished.contains(p)) {
                    vanished.remove(p);
                    p.sendMessage(ChatColor.GOLD + "You are no longer vanished!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                        HotBarAPI.sendHotBarMessage(p, " ");
                        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        Bukkit.getServer().getScheduler().cancelTask(VanishHotBar);
                    }
                } else {
                    vanished.add(p);
                    p.sendMessage(ChatColor.GOLD + "You are vanished!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20000000, 1, true, false));
                        VanishHotBar = Bukkit.getScheduler().scheduleSyncRepeatingTask(NinjaEssentials.getInstance(), () -> {
                            HotBarAPI.sendHotBarMessage(p, ChatColor.GREEN + "You are currently " + ChatColor.RED + ChatColor.BOLD + "VANISHED");
                        }, 0, 0 * 20);
                        if (!all.hasPermission("ninjafactions.cansee.vanished") || !all.isOp()) {
                            all.hidePlayer(p);
                        }
                    }
                }
            }

        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPermMessage")));
        }
        return false;
    }
}
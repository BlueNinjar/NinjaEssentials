package com.blueninjar.ninjaessentials.Events;

import com.blueninjar.ninjaessentials.NinjaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 2017s. Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */
public class tntExplode implements Listener {
    NinjaEssentials plugin;


    public tntExplode(NinjaEssentials instance) {
        plugin = instance;
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (plugin.getConfig().getBoolean("autoExplode") == true) {
            if (plugin.getConfig().getBoolean("rightclickExplode") == false) {


                Player p = e.getPlayer();

                if (p.getItemInHand().getType() == Material.TNT) {
                    e.setCancelled(true);
                    if (p.getItemInHand().getAmount() == 1) {
                        p.setItemInHand(null);
                    } else {
                        ItemStack m = p.getItemInHand();
                        m.setAmount(p.getItemInHand().getAmount() - 1);
                        p.setItemInHand(m);
                    }

                    double x = e.getBlock().getLocation().getBlockX();
                    double y = e.getBlock().getLocation().getBlockY() + 0.3;
                    double z = e.getBlock().getLocation().getBlockZ();

                    Location loc = new Location(e.getBlock().getWorld(), x, y, z);

                    p.getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);

                }
            } else {

            }
        }
    }
    @EventHandler
    public void clickTNT(PlayerInteractEvent event) {
        if (plugin.getConfig().getBoolean("rightclickExplode") == true) {
            if (plugin.getConfig().getBoolean("autoExplode") == false) {
                Player p = event.getPlayer();
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (event.getClickedBlock().getType() == Material.TNT) {
                        event.getClickedBlock().setType(Material.AIR);
                        p.getWorld().spawnEntity(event.getClickedBlock().getLocation().subtract(0.0D, 0.0D, 1.0D), EntityType.PRIMED_TNT);

                    }
                }
            } else {


            }
        }
    }
}

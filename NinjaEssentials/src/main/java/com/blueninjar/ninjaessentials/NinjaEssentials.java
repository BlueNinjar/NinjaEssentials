package com.blueninjar.ninjaessentials;

import com.blueninjar.ninjaessentials.Events.playerConnection;
import com.blueninjar.ninjaessentials.Events.tntExplode;
import com.blueninjar.ninjaessentials.commands.staffChat;
import com.blueninjar.ninjaessentials.commands.vanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Copyright (c) 2017.year Luke Paul. All rights reserved. Please email lukepaultech@gmail.com for usage rights and other information.
 */

public final class NinjaEssentials extends JavaPlugin {
    private Scoreboard s;
    private Set<Player> vanishedList = new HashSet<>();
    private static NinjaEssentials instance;


    @Override
    public void onEnable() {

                instance = this;
                //Print Message on Console
                System.out.print("[Ninja Essentials] has loaded & is now enabled!");
                //Loading Stuff here!
                loadConfig();
                loadEvents();
                loadCommands();
                loadMetrics();
                s = Bukkit.getScoreboardManager().getMainScoreboard();
                registerHealthbar();
    }

            @Override
            public void onDisable() {
                System.out.print("[Ninja Essentials] has un-loaded & is now disabled!");
                vanishedList.clear();
            }

            public static NinjaEssentials getInstance() {
                return instance;
            }

            public void registerHealthbar() {
                if (s.getObjective("health") != null) {
                    s.getObjective("health").unregister();
                }
                Objective o = s.registerNewObjective("health", "health");
                o.setDisplayName(ChatColor.RED + "❤");
                o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                o.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }

            public void loadConfig() {
                final FileConfiguration config = this.getConfig();
                saveDefaultConfig();
            }

            public void loadEvents() {
                PluginManager pm = getServer().getPluginManager();
                pm.registerEvents(new playerConnection(this), this);
                pm.registerEvents(new staffChat(this), this);
                pm.registerEvents(new tntExplode(this), this);


            }

            public void loadCommands() {
                this.getCommand("staff").setExecutor(new staffChat(this));
                this.getCommand("sv").setExecutor(new vanishCommand(this));
            }

            public void loadMetrics() {
                Metrics metrics = new Metrics(this);
                metrics.addCustomChart(new Metrics.SingleLineChart("players", new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        // (This is useless as there is already a player chart by default.)
                        return Bukkit.getOnlinePlayers().size();
                    }
                }));

                metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", new Callable<Map<String, Integer>>() {
                    @Override
                    public Map<String, Integer> call() throws Exception {
                        Map<String, Integer> valueMap = new HashMap<>();
                        valueMap.put("servers", 1);
                        valueMap.put("players", Bukkit.getOnlinePlayers().size());
                        return valueMap;
                    }
                }));
            }

            public void updateListen() {
                updateListener updater = new updateListener(this, "14681"); //Replace "14681" with your own ID. The ID can be found on the end of the resource URL. https://www.spigotmc.org/resources/hidehub. -> 14681 <- /
                updateListener.UpdateResults result = updater.checkForUpdates();
                if(result.getResult() == updateListener.UpdateResult.FAIL)
                {
                    //Updater failed, use result.getVersion() for the stacktrace
                }
                else if(result.getResult() == updateListener.UpdateResult.NO_UPDATE)
                {
                    //No update available

                }
                else if(result.getResult() == updateListener.UpdateResult.UPDATE_AVAILABLE)
                {
                    //An update has been found!
                }
            }
        }

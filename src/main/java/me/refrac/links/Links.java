/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.links;

import me.refrac.links.commands.*;
import me.refrac.links.events.*;
import me.refrac.links.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.refrac.links.utils.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Zachary Baldwin / Refrac
 */
public class Links extends JavaPlugin {

    public static Links plugin;
    private static File cfile;
    private static FileConfiguration config;

    private LinksGUI linksGUI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        long startTiming = System.currentTimeMillis();
        this.createConfig();

        if (getLinksConfig().getBoolean("SilentStart.Enabled")) {
            getCommand("links").setExecutor(new LinksCommand());
            getCommand("linksreload").setExecutor(new LinksReloadCommand());

            Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
            this.linksGUI = new LinksGUI(this);

            Logger.SUCCESS.out("Links successfully enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");

            Logger.INFO.out("Checking for updates...");
            new UpdateChecker(Links.plugin, 90283).getLatestVersion(version -> {
                if (!Links.plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    Logger.NONE.out(Utils.color("&7&m-----------------------------------------"));
                    Logger.NONE.out(Utils.color("&bA new version of Links&7(Links " + version + ") &bhas been released!"));
                    Logger.NONE.out(Utils.color("&bPlease update here: " + Utils.getPluginURL));
                    Logger.NONE.out(Utils.color("&7&m-----------------------------------------"));
                } else
                    Logger.NONE.out(ChatColor.GREEN + "Links is up to date!");
            });
        } else {
            Logger.NONE.out("");
            Logger.NONE.out(ChatColor.LIGHT_PURPLE + " _       ___   _    _   _  __   ____   ");
            Logger.NONE.out(ChatColor.LIGHT_PURPLE + "| |     |_ _| | |  | | | |/ /  / ___|  ");
            Logger.NONE.out(ChatColor.LIGHT_PURPLE + "| |      | |  |      | | ' /    ___    ");
            Logger.NONE.out(ChatColor.LIGHT_PURPLE + "| |___   | |  | |  | | | .      ___) | " + ChatColor.YELLOW + "By " + Utils.getAuthor);
            Logger.NONE.out(ChatColor.LIGHT_PURPLE + "|_____| |___| |_|  |_| |_| |_| |____/  " + ChatColor.YELLOW + "v" + Utils.getVersion);
            Logger.NONE.out("");

            Logger.INFO.out("Loading commands");
            getCommand("links").setExecutor(new LinksCommand());
            getCommand("linksreload").setExecutor(new LinksReloadCommand());
            Logger.SUCCESS.out("Successfully loaded the commands");

            Logger.INFO.out("Loading events");
            Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
            this.linksGUI = new LinksGUI(this);
            Logger.SUCCESS.out("Successfully loaded the events");

            Logger.SUCCESS.out("Links successfully enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");

            Logger.INFO.out("Checking for updates...");
            new UpdateChecker(Links.plugin, 90283).getLatestVersion(version -> {
                if (!Links.plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                    Logger.NONE.out(Utils.color("&7&m-----------------------------------------"));
                    Logger.NONE.out(Utils.color("&bA new version of Links&7(Links " + version + ") &bhas been released!"));
                    Logger.NONE.out(Utils.color("&bPlease update here: " + Utils.getPluginURL));
                    Logger.NONE.out(Utils.color("&7&m-----------------------------------------"));
                } else
                    Logger.NONE.out(ChatColor.GREEN + "Links is up to date!");
            });
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin = null;
    }

    public static FileConfiguration getLinksConfig() {
        return config;
    }

    private void createConfig() {
        cfile = new File (getDataFolder(), "config.yml");
        if (!cfile.exists()) {
            cfile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(cfile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            Logger.ERROR.out("Failed to create the config file! Report this to the developer @ " + Utils.getSupport);
        }
    }

    public void reloadConfig() {
        cfile = new File(getDataFolder(), "config.yml");
        try {
            config = YamlConfiguration.loadConfiguration(cfile);
        } catch(Exception e) {
            e.printStackTrace();
            Logger.ERROR.out("Failed to reload the config file! Report this to the developer @ " + Utils.getSupport);
        }
    }

    public LinksGUI getLinksGUI() {
        return linksGUI;
    }
}
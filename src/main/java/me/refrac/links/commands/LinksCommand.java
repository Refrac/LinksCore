/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.links.commands;

import me.refrac.links.Links;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import me.refrac.links.utils.*;

/**
 * @author Zachary Baldwin / Refrac
 */
public class LinksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Logger.WARNING.out("You can only use this command as a player!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (Links.getLinksConfig().getBoolean("GUI.Enabled")) {
                player.openInventory(Links.plugin.getLinksGUI().getInventory());
            } else {
                for (String m : Links.getLinksConfig().getStringList("Links")) {
                    player.sendMessage(Utils.color(m));
                }
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                if (!player.hasPermission("links.help")) {
                    player.sendMessage(Utils.color(Links.getLinksConfig().getString("Messages.no_permission")));
                    return false;
                }

                this.sendHelpMessage(player);
            } else if (args[0].equalsIgnoreCase("about")) {
                if (!player.hasPermission("links.about")) {
                    player.sendMessage(Utils.color(Links.getLinksConfig().getString("Messages.no_permission")));
                    return false;
                }

                this.sendAboutMessage(player);
            } else if (args[0].equalsIgnoreCase("update")) {
                if (!player.hasPermission("links.update")) {
                    player.sendMessage(Utils.color(Links.getLinksConfig().getString("Messages.no_permission")));
                    return false;
                }

                player.sendMessage(ChatColor.RED + "Checking for updates...");
                new UpdateChecker(me.refrac.links.Links.plugin, 90283).getLatestVersion(version -> {
                    if (!Links.plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(Utils.color("&7&m-----------------------------------------"));
                        player.sendMessage(Utils.color("&bA new version of Links&7(Links " + version + ") &bhas been released!"));
                        player.sendMessage(Utils.color("&bPlease update here: " + Utils.getPluginURL));
                        player.sendMessage(Utils.color("&7&m-----------------------------------------"));
                    } else
                        player.sendMessage(ChatColor.GREEN + "Links is up to date!");
                });
            } else if (args[0].equalsIgnoreCase("dev")) {
                if (!player.getName().equalsIgnoreCase("Refrac")) {
                    player.sendMessage(Utils.getDevMessage);
                    player.sendMessage(Utils.getDevMessage2);
                    player.sendMessage(Utils.getDevMessage3);
                    return false;
                }
                player.sendMessage(ChatColor.WHITE + "Hello Refrac!");
                player.sendMessage(ChatColor.GRAY + "Server Version: " + ChatColor.WHITE + Bukkit.getVersion());
                player.sendMessage(ChatColor.GRAY + "Plugin Version - " + ChatColor.WHITE + Utils.getVersion);
                player.sendMessage(ChatColor.GRAY + "Config Version - " + ChatColor.WHITE + Links.getLinksConfig().getString("configVersion"));
                player.sendMessage(ChatColor.GRAY + "End of log.");
                return true;
            }
            return false;
        }
        return false;
    }

    public void sendHelpMessage(Player player) {
        player.sendMessage(Utils.color("&7&m---------------&7[ &d&lLINKS HELP &7]&7&m---------------"));
        player.sendMessage("");
        player.sendMessage(Utils.color("&d/links &7| &eShows all of the server links"));
        player.sendMessage(Utils.color("&d/links help &7| &eThis help page"));
        player.sendMessage(Utils.color("&d/links about &7| &eShows plugin info"));
        player.sendMessage(Utils.color("&d/linksreload &7| &eReloads the config files"));
        player.sendMessage(Utils.color("&d/links update &7| &eChecks for an update on SpigotMC"));
        player.sendMessage("");
        player.sendMessage(Utils.color("&7&m---------------&7[ &d&lLINKS HELP &7]&7&m---------------"));
    }

    public void sendAboutMessage(Player player) {
        player.sendMessage(Utils.color("&7&m---------------&7[ &d&lLINKS ABOUT &7]&7&m---------------"));
        player.sendMessage("");
        player.sendMessage(Utils.color("&dCreated by: &e" + Utils.getAuthor));
        player.sendMessage(Utils.color("&dVersion: &e" + Utils.getVersion));
        player.sendMessage(Utils.color("&dSupport: &e" + Utils.getSupport));
        player.sendMessage("");
        player.sendMessage(Utils.color("&7&m---------------&7[ &d&lLINKS ABOUT &7]&7&m---------------"));
    }
}
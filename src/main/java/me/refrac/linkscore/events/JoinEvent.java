package me.refrac.linkscore.events;

import me.refrac.linkscore.*;
import me.refrac.linkscore.utils.*;
import org.bukkit.event.player.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.*;

import java.util.List;

public class JoinEvent implements Listener
{
    private UpdateChecker checker;

    @EventHandler
    public void onJoin(final PlayerJoinEvent e)
    {
        // Check if plugin is outdated
        Player p = e.getPlayer();
        if(p.hasPermission("links.update"))
        {
            if (LinksCore.plugin.getConfig ().getBoolean ( "Update.Enabled" ))
            {
                checker = new UpdateChecker(LinksCore.plugin);
                if (checker.isConnected())
                {
                    if(checker.hasUpdate())
                    {
                        p.sendMessage(ChatColor.GRAY + "*************************");
                        p.sendMessage(ChatColor.RED + "LinksCore is outdated!");
                        p.sendMessage(ChatColor.GREEN + "Newest version: " + this.checker.getLatestVersion());
                        p.sendMessage(ChatColor.RED + "Your version: " + Settings.VERSION);
                        p.sendMessage(ChatColor.GOLD + "Download: " + Settings.PLUGIN_URL);
                        p.sendMessage(ChatColor.GRAY + "*************************");
                    }
                }
            }
        }

        // Join Message
        List<String> messages =  LinksCore.plugin.getConfig().getStringList("message");

        for (String message : messages)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
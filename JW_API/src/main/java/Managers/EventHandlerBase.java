package Managers;

import GUI.Inventory.GUI_Inventory;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class EventHandlerBase implements Listener
{


    public EventHandlerBase()
    {
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }

    @EventHandler
    public void onPluginStart(PluginEnableEvent event)
    {

    }
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event)
    {

    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {

    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {

    }

}

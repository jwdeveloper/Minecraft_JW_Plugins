package jw_api.jw_api;

import NPC.NPC_Manager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NPC_EventManager implements Listener
{

   Main main;

   public  NPC_EventManager(Main main)
   {
       this.main = main;
       Bukkit.getPluginManager().registerEvents((Listener)this, main);
   }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent playerJoinEvent)
    {
        NPC_Manager.Instance().Load_from_File();
        NPC_Manager.Instance().ShowToPlayer(playerJoinEvent.getPlayer());

    }
    @EventHandler
    public void PlayerLeave(PlayerQuitEvent playerJoinEvent)
    {
        NPC_Manager.Instance().Save_to_File();
        NPC_Manager.Instance().HideToPlayer(playerJoinEvent.getPlayer());
    }
}

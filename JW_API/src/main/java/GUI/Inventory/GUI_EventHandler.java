package GUI.Inventory;

import GUI.Components.GUI_Title;
import GUI.Inventory.Events.GUI_AcctionEvent;
import GUI.Inventory.Events.GUI_Acctions;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI_EventHandler implements Listener
{




    private ArrayList<GUI_Inventory> members = new ArrayList<>();

    public Plugin plugin;

    private static GUI_EventHandler instnace;

    public static GUI_EventHandler Instnace()
    {
        if(instnace == null)
        {
            instnace = new GUI_EventHandler();
        }
        return instnace;


    }

    public GUI_EventHandler()
    {



        plugin= Bukkit.getPluginManager().getPlugins()[0];
        Bukkit.getPluginManager().registerEvents(this,plugin);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance,()->
        {
            if(placed_block.size()==0)
                return;

            Location location =  placed_block.get(0);
            location.getBlock().setType(Material.AIR);

            placed_block.remove(location);
        },1,1);
    }

    public ArrayList<Location> placed_block = new ArrayList<>();

    @EventHandler
    public void MoveEvent(PlayerMoveEvent event)
    {

        /*Player player = event.getPlayer();

        Vector direction =   player.getLocation().getDirection();

        Location location_1  = player.getLocation().add(direction.multiply(-1));
        Location location_2 = new Location(player.getWorld(),location_1.getX(),location_1.getY(),location_1.getZ()).add(0,1,0);


        placed_block.add(location_1);
        placed_block.add(location_2);


        location_1.getBlock().setType(Material.GREEN_STAINED_GLASS);
        location_2.getBlock().setType(Material.GREEN_STAINED_GLASS);
        player.getWorld().spawnParticle(Particle.HEART,location_1.getX(),location_1.getY(),location_1.getZ(),5);*/
    }



    public void AddInventory(GUI_Inventory inventory)
    {
        if(members.contains(inventory)==false)
        members.add(inventory);
    }
    public void RemoveInventory(GUI_Inventory inventory)
    {
        members.remove(inventory);
    }





    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getRawSlot()==-999)
            return;

      //&&name.equalsIgnoreCase(members.get(i).getCurrentTitle())
        for(int i=0;i<members.size();i++)
        {
            Inventory inventory = members.get(i).inventory;
            if(inventory==null || members.get(i).Is_closed==true)continue;

            if (event.getInventory() == members.get(i).inventory)
            {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                members.get(i).clickEvent.forEach(e -> e.Execute(event,event.getClickedInventory(),GUI_Acctions.CLICK));
            }
        }

    }


    @EventHandler(priority  = EventPriority.LOWEST)
    public void onPlayerChat(BlockBreakEvent event)
    {
        for(int i=0;i<members.size();i++)
        {
            GUI_Inventory inv = members.get(i);
            if (event.getPlayer() == inv.player && inv.Is_BlockSelecting==true)
            {
                Bukkit.getScheduler().runTask(plugin,()->
                {
                    inv.onBlock.Execute(event.getBlock(),inv.player);
                    inv.open(inv.player);
                    inv.updateScreen=false;
                });
                inv.Is_BlockSelecting=false;
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority  = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        for(int i=0;i<members.size();i++)
        {
            GUI_Inventory inv = members.get(i);
            if (event.getPlayer() == inv.player && inv.Is_ChatOpen==true)
            {
                Bukkit.getScheduler().runTask(plugin,()->{

                    inv.onChat.Execute(event.getMessage(),inv.player);
                    inv.open(inv.player);
                    inv.updateScreen=false;
                });
                inv.Is_ChatOpen=false;
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event)
    {
        for(int i=0;i<members.size();i++)
        {
            GUI_Inventory inv = members.get(i);
            if (event.getPlayer() == inv.player )
            {
                inv.close();
            }
        }
    }
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event)
    {
        for(int i=0;i<members.size();i++)
        {
            GUI_Inventory inv = members.get(i);
            inv.player.closeInventory();
            inv.close();
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        for(int i=0;i<members.size();i++)
        {

            GUI_Inventory inv = members.get(i);
            if (event.getPlayer() == inv.player && inv.Is_ChatOpen==true)
            {
               event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event)
    {

        for(int i=0;i<members.size();i++)
        {
            Inventory inventory = members.get(i).inventory;
            //Bukkit.getConsoleSender().sendMessage("Inventoyrs "+event.getInventory().toString()+ " " +inventory.toString());
            if(inventory==null || members.get(i).Is_closed==true)continue;
           // Bukkit.getConsoleSender().sendMessage("Open "+members.get(i).getCurrentTitle()+ " "+members.size());
            if (event.getInventory() ==inventory && members.get(i).Is_firstTimeOpen==true )
            {

                members.get(i).openEvent.forEach(e -> e.Execute(event,event.getInventory(),GUI_Acctions.OPEN));
            }
        }

    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {

        //&& name.equalsIgnoreCase(members.get(i).getCurrentTitle())

        for(int i=0;i<members.size();i++)
        {
            Inventory inventory = members.get(i).inventory;
            if(inventory==null || members.get(i).Is_closed==true)continue;

            if (event.getInventory() ==inventory)
            {
                    //Bukkit.getConsoleSender().sendMessage("Close "+members.get(i).getCurrentTitle());
                    members.get(i).closeEvent.forEach(e -> e.Execute(-1,event.getInventory(), GUI_Acctions.GET));
            }
        }

    }


}

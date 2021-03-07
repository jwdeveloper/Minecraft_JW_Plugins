package Managers;



import com.mojang.authlib.GameProfile;
import com.mysql.fabric.xmlrpc.base.Array;
import jw_api.jw_api.Main;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;


public class Player_Manager implements Listener
{

    private HashMap<UUID, DisguiseManager> disguisedPlayers= new HashMap<>();

    public static  Player_Manager instance;


    public static Player_Manager Get_instance()
    {
        if(instance==null)
          instance = new Player_Manager();
        return instance;
    }



    public DisguiseManager getPlayer(Player player)
    {
        return this.disguisedPlayers.get(player.getUniqueId());
    }
    public void setGP(Player player, EntityLiving entityLiving, boolean disguising) {

        Arrays.asList(entityLiving.getClass().getFields()).forEach(e ->
                {
                    Bukkit.getConsoleSender().sendMessage(e.getName()+" "+ e.getType());
                }
        );

        try
        {
            Field gp2 = entityLiving.getClass().getSuperclass().getDeclaredField("bt");




            gp2.setAccessible(true);
            if (disguising)
            {
                gp2.set(entityLiving, getPlayer(player).getOldProfile());
            } else {
                gp2.set(entityLiving, getPlayer(player).getNewProfile());
            }
            gp2.setAccessible(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (getPlayer(player) != null)
            {
                this.disguisedPlayers.remove(player.getUniqueId());
            }
        }
    }

    public  void Set_Displayed_Name(Player player,String name)
    {


        CraftPlayer craftPlayer = ((CraftPlayer)player );
        GameProfile profile = new GameProfile(player.getUniqueId(), name);
        EntityLiving entityLiving = craftPlayer.getHandle();
        this.disguisedPlayers.put(player.getUniqueId(), new DisguiseManager(player, name, craftPlayer.getProfile(), profile));

        player.setDisplayName(name);
        player.setPlayerListName(name);
        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle()));

        setGP(player,entityLiving,true);
        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()));
        try {
            for (final Player others : Bukkit.getOnlinePlayers()) {
                if (!others.getUniqueId().equals(player.getUniqueId())) {
                    ((CraftPlayer) others).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                    ((CraftPlayer) others).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle()));
                    Bukkit.getServer().getScheduler().runTask(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            others.hidePlayer(player);
                        }
                    });
                    Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            others.showPlayer(player);
                        }
                    }, 5);
                }
            }
        }
        catch (Exception ex)
        {
               Bukkit.getConsoleSender().sendMessage(ex.getMessage());
         }


    }

    public static void Give_Items(Player player, ArrayList<ItemStack> itemStacks)
    {
        PlayerInventory inv = player.getInventory();
        ArrayList<Integer> armor_slots = new ArrayList<>();
        armor_slots.add(39);
        armor_slots.add(38);
        armor_slots.add(37);
        armor_slots.add(36);
        for(int i=0;i<inv.getSize();i++)
        {
            if(itemStacks.size()==0)
                return;

            if(armor_slots.contains(i)==true)
                continue;

            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
            {
                ItemStack to_insert = itemStacks.get(itemStacks.size()-1);
                inv.setItem(i,to_insert);
                itemStacks.remove(to_insert);
            }
        }
        if(itemStacks.size()!=0)
        {
            World world =player.getWorld();
            for(ItemStack itemStack:itemStacks)
            {
                world.dropItem(player.getLocation(), itemStack);
            }
        }
    }




}

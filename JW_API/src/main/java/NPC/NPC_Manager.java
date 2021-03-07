package NPC;

import Managers.File_Manager;
import Packets.PacketReader;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NPC_Manager
{

    public interface NPC_Action
    {
        public void OnComplited(NPC npc);
    }

    public List<NPC> npcs = new ArrayList<>();
    private Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
    PacketReader packetReader = new PacketReader();
    private  static NPC_Manager instance;
    public static NPC_Manager Instance()
    {
        if(instance==null)
            instance = new NPC_Manager();

        return  instance;
    }




   public void Save_to_File()
   {

   }
   public void Load_from_File()
   {

   }
   public void CreateNPC(String name, Location location,NPC_Action npc_action)
   {
       new BukkitRunnable() {
           @Override
           public void run() {
               NPC npc = new NPC(name,location);
               npcs.add(npc);
               npc_action.OnComplited(npc);
           }
       }.runTaskLater(plugin, 1);
   }
   public void ShowToPlayer(Player player)
   {
       new BukkitRunnable() {
           @Override
           public void run() {
               packetReader.inject(player);
               for(NPC npc : npcs)
               {
                   PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                   connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc.entityPlayer));
                   connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc.entityPlayer));
                   connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc.entityPlayer, (byte) (npc.entityPlayer.yaw * 256 / 360))); // Correct head rotation when spawned in player look direction.
               }
           }
       }.runTaskLater(plugin, 1);
   }
   public void HideToPlayer(Player player)
   {
        new BukkitRunnable() {
            @Override
            public void run() {
                packetReader.unijnect(player);
                for(NPC npc : npcs)
                {
                    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc.entityPlayer));
                    connection.sendPacket(new PacketPlayOutEntityDestroy(npc.entityPlayer.getId()));
                }
            }
        }.runTaskLater(plugin, 1);
    }
   public void Load_NPC_Manager()
   {
       if(Bukkit.getOnlinePlayers().isEmpty()==false)
       {
           for (Player p :Bukkit.getOnlinePlayers())
           {
               packetReader.inject(p);
               ShowToPlayer(p);
           }
       }
   }
   public void Close_NPC_Manager()
   {
       if(Bukkit.getOnlinePlayers().isEmpty()==false)
       {
           for (Player p :Bukkit.getOnlinePlayers())
           {
               packetReader.unijnect(p);
               for(NPC npc : npcs)
               {
                   PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
                   connection.sendPacket(new PacketPlayOutEntityDestroy(npc.entityPlayer.getId()));
               }
           }
       }
   }

   public static void Load()
   {
       ArrayList<NPC> npcs= File_Manager.Load_List(File_Manager.CurrentPath()+"\\plugins\\JW_npc\\","npc.json", NPC.class);
      NPC_Manager.instance.npcs = npcs;
   }
   public static void Save()
   {

        File_Manager.Save(  NPC_Manager.instance.npcs,File_Manager.CurrentPath()+"\\plugins\\JW_npc\\","npc.json");
   }


   public NPC GetNpc(String name)
   {
       Optional<NPC> result = npcs.stream().filter(e -> e.name == name).findFirst();


        return result.get();
   }




}

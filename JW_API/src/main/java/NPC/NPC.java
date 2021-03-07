package NPC;

import CustomEvents.PlayerNpcEvent;
import GUI.Inventory.GUI_Itemstack;
import Managers.ProfileLoader;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class NPC implements Listener
{




    public GUI_Itemstack helemet = new GUI_Itemstack(Material.AIR);
    public GUI_Itemstack chest = new GUI_Itemstack(Material.AIR);
    public GUI_Itemstack legs = new GUI_Itemstack(Material.AIR);
    public GUI_Itemstack boots = new GUI_Itemstack(Material.AIR);

    public GUI_Itemstack lefthand = new GUI_Itemstack(Material.AIR);
    public GUI_Itemstack righthand = new GUI_Itemstack(Material.AIR);

    public Material mount = Material.AIR;

    public EntityPlayer entityPlayer;
    private Location location;
    private String uuid;
    public String name;
    private Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
    public World world;

    @EventHandler
    public void onClickNpc(PlayerNpcEvent e)
    {
        if(e.Npc_Clicked==this)
      e.player.sendMessage("Nie dotykaj mnie, jestem "+e.Npc_Clicked.name);

    }


    private void RemoveFromTablist(){
       /* PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(npc., 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet);*/
    }

    public void WalkTo(Location location,float time)
    {

    }
    public void  RefrshInventory()
    {
        Bukkit.getScheduler().runTask(plugin,()->{
            entityPlayer.setLocation(location.getX(),location.getY(),location.getZ(),2,2);

            final List<Pair<EnumItemSlot, ItemStack>> equipmentList = new ArrayList<>();


            equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy( helemet)));
            equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(chest)));
            equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(legs)));
            equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(boots)));
            equipmentList.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(righthand)));
            equipmentList.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(lefthand)));

            for (Player p : Bukkit.getOnlinePlayers())
            {
                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutEntityEquipment(entityPlayer.getId(), equipmentList ));
            }
        });
    }
    public void Is_Visible(boolean visile)
    {

    }
    public void rename(String name)
    {
        Spawn(name,this.getNpclocation());
    }
    public  Location getNpclocation()
    {
        return new Location(world, entityPlayer.locX(),entityPlayer.locY(),entityPlayer.locZ());
    }
    public void setNpclocation(Location location)
    {
        Bukkit.getScheduler().runTask(plugin,()->{
            entityPlayer.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());

            for (Player p : Bukkit.getOnlinePlayers())
            {
                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutEntityTeleport(entityPlayer));
            }
        });
    }




    int LookID = 0;
    int i=0;
    public void StartLookAtPlayer(Player player)
    {
        Location loc = player.getLocation();
        LookID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0],()->
        {

            for (Player p : Bukkit.getOnlinePlayers())
            {

                int player_pos =  (int)(loc.getYaw()*((i)/360.0f));
                Bukkit.getConsoleSender().sendMessage(player_pos+ "");
                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
              //  connection.sendPacket(new PacketPlayOutEntityHeadRotation(entityPlayer,  (byte) (-player_pos/10)));
                connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entityPlayer.getId(),(byte) player_pos, (byte)1, true));
            }
            i +=10;

        },1l,1l);
    }
    public void StopLookAtPlayer()
    {
        Bukkit.getScheduler().cancelTask(LookID);
    }

    public void setMount(EntityType type)
    {

        world.spawnEntity(getNpclocation().add(0,1,0), type);
    }
    public void Kill()
    {
        new BukkitRunnable() {
            @Override
            public void run() {

                if(NPC_Manager.Instance().npcs.contains(this))
                  NPC_Manager.Instance().npcs.remove(this);

                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
                    connection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
                }
            }
        }.runTaskLater(plugin, 1);


    }

    public void Spawn(String name,Location location)
    {
        if(entityPlayer!=null)
            this.Kill();

         world = location.getWorld();
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
        GameProfile gameProfile = new ProfileLoader(uuid,name, name).loadProfile();
        entityPlayer = new EntityPlayer(server,
                worldServer,
                gameProfile,
                new PlayerInteractManager(worldServer));

        entityPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(entityPlayer,  (byte) ((location.getYaw() * (256.0f/360f))))); // Correct head rotation when spawned in player look direction.
          //  connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
        }
    }
    public void changeSkin(String nameoruuid)
    {
       if(nameoruuid.length()>16)
       {
           this.uuid =nameoruuid;
       }

    }
    public ItemStack Get_Head()
    {
        return null;
    }
    public NPC(String name, Location location)
    {
     Bukkit.getPluginManager().registerEvents((Listener)this, plugin);
     this.location = location;
     this.uuid = uuid;
     this.name =name;
     Spawn(name,location);

    }


}

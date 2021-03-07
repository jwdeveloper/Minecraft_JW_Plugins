package Packets;

import CustomEvents.PlayerNpcEvent;
import NPC.NPC;
import NPC.NPC_Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class PacketReader
{




   public HashMap<Player,Channel> chanels = new HashMap<>();

    public void inject(Player player)
    {

        if(chanels.containsKey(player))
            return;

        CraftPlayer cPlayer = (CraftPlayer)player;
        Channel channel = cPlayer.getHandle().playerConnection.networkManager.channel;

        chanels.put(player,channel);

        if(channel.pipeline().get("PacketInjector") != null)
           return;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>()
        {@Override protected void decode(ChannelHandlerContext arg0, PacketPlayInUseEntity packet, List<Object> arg2) throws Exception
        {    arg2.add(packet);
             ReadPacket(player,packet);}});

        }

        public void unijnect(Player player)
        {
            Channel chanel  = chanels.get(player);
            if(chanel.pipeline().get("PacketInjector") != null)
                chanel.pipeline().remove("PacketInjector");
        }

        public void ReadPacket(Player player, Packet<?> packet)
        {
            if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
                int id = (Integer)getValue(packet, "a");

                       
                       
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugins()[0], new Runnable() {
                            @Override
                            public void run() {

                                for(NPC npc : NPC_Manager.Instance().npcs)
                                {

                                    if (npc.entityPlayer.getId() == id)
                                    {
                                        PlayerNpcEvent.MouseClick mouseClick = PlayerNpcEvent.MouseClick.Left;
                                        if (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
                                        {
                                            Bukkit.getPluginManager().callEvent(new PlayerNpcEvent(player,npc, mouseClick));
                                        }
                                        else if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT"))
                                            mouseClick = PlayerNpcEvent.MouseClick.Right;
                                        else
                                            return;



                                    }
                                }
                              

                            }
                        },0);
                      
                    
                }
            }


    public void setValue(Object obj,String name,Object value){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }catch(Exception e){}
    }

    public Object getValue(Object obj,String name){
        Object result=null;
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(obj);
            field.setAccessible(false);

        }catch(Exception e){}

        return result;
    }
}

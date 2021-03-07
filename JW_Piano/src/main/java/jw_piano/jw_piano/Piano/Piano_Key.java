package jw_piano.jw_piano.Piano;


import jw_api.jw_api.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Piano_Key implements I_Pressable
{
    ArrayList<Block> blocks = new ArrayList<>();

    ArrayList<Material> Channel_Block = new ArrayList<>();
    ArrayList<Material> Channel_Block_black = new ArrayList<>();


    Location location;

    Note.Tone note;
    int octave;
    int note_number;
    boolean is_flat;

    public Piano_Key(Location location,int note,int octave)
    {
        this.location = location;
        this.note = Piano_Notes.get_note(note);
        this.is_flat = Piano_Notes.is_flat(note);
        this.octave = octave;
        this.note_number = note;
        GetBlocks(location);
        Relese(0,0,0);
    }



    public void GetBlocks(Location l)
    {
        if(is_flat==false)
            location.add(new Vector(0,1,0));
        for(int i=0;i<4;i++)
        {

            if(is_flat == false)
            {
                if(i==0)
                {
                    is_flat= true;
                    l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()-1,l.getBlockZ()).setType(MaterialRelese());
                    is_flat= false;
                }
                else
                {
                    l.getWorld().getBlockAt(l.getBlockX(),l.getBlockY()-1,l.getBlockZ()).setType(MaterialRelese());
                    blocks.add(l.getWorld().getBlockAt(l));
                }
            }
            else
            {
                blocks.add(l.getWorld().getBlockAt(l));
            }

            l.add(new Vector(1,0,0));
        }
    }




    public void createHelix(Location loc) {


      /*  PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(Particles.ANGRY_VILLAGER,true, (float) (loc.getX()), (float) (loc.getY() ), (float) (loc.getZ() ), 0, 0, 0, 0, 1);
        for(Player online : Bukkit.getOnlinePlayers())
        {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
        }*/

    }
    float i=0;






    @Override
    public Material MaterialRelese()
    {
        if(is_flat)
            return Material.QUARTZ_BLOCK;
        else
        return Material.POLISHED_BLACKSTONE_BRICK_SLAB;
    }

    @Override
    public Material MaterialPress() {
        if(is_flat)
            return Material.QUARTZ_SLAB;
        else
            return  Material.AIR;
    }

    @Override
    public boolean Is_Pressed() {
        return false;
    }


    public void Effect(int velocity,int channel)
    {
        createHelix( location.clone().add(0,2,0));

        if(velocity>60)
            i=4;
        else
            i=0;

        if(channel == 0)
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(0,1+(5)*(velocity/100) ,0),1, new Particle.DustOptions(Color.fromBGR(255-(int)5*25,  255,255-(int)5*25 ),(int)10 ));
        else
            location.getWorld().spawnParticle(Particle.REDSTONE,  location.clone().add(01,1+(5)*(velocity/100),0),1, new Particle.DustOptions(Color.fromBGR(255,255-(int)5*25  ,255-(int)5*25 ),(int)10 ));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance,()->
        {
            if(i<5)
            {
                if(channel == 0)
                    location.getWorld().spawnParticle(Particle.REDSTONE,  location.clone().add(0,1+(i*5)*(velocity/100) ,0),1, new Particle.DustOptions(Color.fromBGR(255-(int)i*25,  255,255-(int)i*25 ),(int)i ));
                else
                    location.getWorld().spawnParticle(Particle.REDSTONE,location.clone().add(0,1+(i*5)*(velocity/100),0),1, new Particle.DustOptions(Color.fromBGR(255,255-(int)i*25  ,255-(int)i*25 ),(int)i ));

                i+=0.2f;
            }

        },1,1);
    }


    public void Set_light(boolean state)
    {
        if(state)
        {

          //  LightAPI.createLight(location.clone().add(-1,1,0), LightType.BLOCK,16,true);
        }
        else
        {
        //    LightAPI.deleteLight(location.clone().add(-1,1,0), LightType.BLOCK,true);
        }
    }

    @Override
    public void Press(int id, int velocity, int channel)
    {
        Bukkit.getScheduler().runTask(Main.instance, () ->
        {

            Effect(velocity,channel);
          //  Set_light(true);
            for(Block b:blocks)
            {
                b.setType(this.MaterialPress());
            }
        });
    }

    @Override
    public void Relese(int id, int velocity, int channel) {
        Bukkit.getScheduler().runTask(Main.instance, () ->
        {
       //     Set_light(false);
            for(Block b:blocks)
            {
                b.setType(this.MaterialRelese());
            }
        });
    }

    @Override
    public void SetMaterial(Material material)
    {
        Bukkit.getScheduler().runTask(Main.instance,()->
        {
            if(is_flat==false)
            {
                blocks.get(0).getLocation().add(-1,-1,0).getBlock().setType(material);
            }
            for (Block b : blocks) {

                if(is_flat==false)
                {
                    b.getLocation().clone().add(0,-1,0).getBlock().setType(material);
                }

                b.setType(material);
            }
        });
    }
}

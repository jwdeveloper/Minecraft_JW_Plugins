package jw_piano.jw_piano.Piano;

import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;


public class Piano_Pedal  implements I_Pressable
{


    Location location;

    boolean is_pressed;
    ArrayList<Block> blocks_up = new ArrayList<>();
    ArrayList<Block> blocks_down = new ArrayList<>();
    public Piano_Pedal(Location location)
    {
        this.location = location;
        Get_Blocks();
    }

    public void Get_Blocks()
    {
        Block current;
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<3;j++)
            {
                current = location.getWorld().getBlockAt(location.clone().add(i,0,j));
                blocks_up.add(current);

                current = location.getWorld().getBlockAt(location.clone().add(i,-1,j));
                blocks_down.add(current);
            }
        }
    }

    @Override
    public Material MaterialRelese() {
        return Material.SMOOTH_SANDSTONE_SLAB;
    }

    @Override
    public Material MaterialPress() {
        return Material.SMOOTH_SANDSTONE_SLAB;
    }

    @Override
    public boolean Is_Pressed() {
        return is_pressed;
    }

    @Override
    public void Press(int id, int velocity, int channel)
    {
        if(is_pressed == false)
        {
        Bukkit.getScheduler().runTask(Main.instance,()->
        {
            for(int i=0;i<blocks_up.size();i++)
            {
                blocks_up.get(i).setType(Material.AIR);
                blocks_down.get(i).setType( MaterialPress());
            }
        });
        }
        is_pressed = true;
    }

    @Override
    public void Relese(int id, int velocity, int channel)
    {
        if(is_pressed == true)
        {
            Bukkit.getScheduler().runTask(Main.instance,()->
            {
                for(int i=0;i<blocks_up.size();i++)
                {
                    blocks_up.get(i).setType(MaterialRelese());
                    blocks_down.get(i).setType(Material.AIR);
                }
            });
        }
        is_pressed = false;
    }

    @Override
    public void SetMaterial(Material material)
    {

        Bukkit.getScheduler().runTask(Main.instance,()->
        {
            for (Block b : blocks_up) {
                b.setType(material);
            }
        });
    }
}
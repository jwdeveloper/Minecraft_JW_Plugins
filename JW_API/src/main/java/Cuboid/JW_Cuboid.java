package Cuboid;

import Managers.Block_Manager;
import com.mysql.jdbc.Buffer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JW_Cuboid
{

     public String ID = UUID.randomUUID().toString();
     public String cuboid_name  = new String();
     public Vector position_min= new Vector(0,-1,0);
     public Vector position_max = new Vector(0,-1,0);
     public String world_name  = new String();
     public ArrayList<String> owners = new ArrayList<>();
     public ArrayList<String> permissions = new ArrayList<>();

     private List<Block> cuboid_blocks;
     private Location location_min;
     private Location location_max;
     public World world;



     public List<Block> Get_Cuboid_Blocks()
     {
         if(cuboid_blocks==null)
         {
             this.cuboid_blocks = Block_Manager.generateCuboidRegion(location_min,location_max);
         }

         return this.cuboid_blocks;
     }

     public void Load()
     {
         World world = Bukkit.getWorld(world_name);
         Bukkit.getConsoleSender().sendMessage("JW_Cuboid: "+this.cuboid_name+" cound not find world "+world_name);

         if(world!=null)
         {
             location_min = new Location(world,position_min.getX(),position_max.getY(),position_max.getZ());
             location_max = new Location(world,position_max.getX(),position_max.getY(),position_max.getZ());
         }
     }


    public JW_Cuboid(Location loc1,int radius)
    {
        location_min = new Location(loc1.getWorld(),loc1.getX()-radius,255,loc1.getZ()-radius);
        location_max = new Location(loc1.getWorld(),loc1.getX()+radius,0,loc1.getZ()+radius);

        this.world_name = loc1.getWorld().getName();
        this.position_min = location_min.toVector();
        this.position_max = location_max.toVector();
    }

     public JW_Cuboid(Location loc1,Location loc2)
     {
         int max_x = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
         int min_x = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
         int max_y = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
         int min_y = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
         int max_z = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
         int min_z = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

         location_min = new Location(loc1.getWorld(),min_x,min_y,min_z);
         location_max = new Location(loc1.getWorld(),max_x,max_y,max_z);


          this.world_name = loc1.getWorld().getName();
          this.position_min = location_min.toVector();
          this.position_max = location_max.toVector();
     }









}

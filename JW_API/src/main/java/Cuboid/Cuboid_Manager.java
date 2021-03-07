package Cuboid;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class Cuboid_Manager implements Listener
{


    public ArrayList<JW_Cuboid> cuboids = new ArrayList<>();


    public boolean Is_Atlocation(Location play_location,JW_Cuboid jw_cuboid)
    {

        if(!play_location.getWorld().equals(jw_cuboid.world))
            return  false;

        if(   play_location.getX() > jw_cuboid.position_min.getX() &&  play_location.getX() < jw_cuboid.position_max.getX()
          &&  play_location.getY() > jw_cuboid.position_min.getY() &&  play_location.getY() < jw_cuboid.position_max.getY()
          &&  play_location.getZ() > jw_cuboid.position_min.getZ() &&  play_location.getZ() < jw_cuboid.position_max.getZ())
            return true;


        return false;
    }


    @EventHandler
    public void PlayerMove(PlayerMoveEvent playerMoveEvent)
    {
        for(int i=0;i<cuboids.size();i++)
        {
            if(Is_Atlocation(playerMoveEvent.getPlayer().getLocation(),cuboids.get(i)))
            {
                playerMoveEvent.getPlayer().sendMessage("You are a cuboid");
                break;
            }
        }
    }

    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent playerMoveEvent)
    {

    }
    @EventHandler
    public void PlayerBlockPlace(BlockPlaceEvent playerMoveEvent)
    {
        for(int i=0;i<cuboids.size();i++)
        {
            if(Is_Atlocation(playerMoveEvent.getBlock().getLocation(),cuboids.get(i)))
            {
                playerMoveEvent.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void PlayerBlockDestroy(BlockDamageEvent playerMoveEvent)
    {
        for(int i=0;i<cuboids.size();i++)
        {
            if(Is_Atlocation(playerMoveEvent.getBlock().getLocation(),cuboids.get(i)))
            {
                playerMoveEvent.setCancelled(true);
                break;
            }
        }
    }
}

package Managers;

import com.mysql.fabric.xmlrpc.base.Array;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;
import java.util.function.Consumer;

public class Block_Selector_Manager implements Listener
{


    private ArrayList<Players_blocks> players_block = new ArrayList<>();


    private static Block_Selector_Manager Instance;

    public static Block_Selector_Manager GetInstnace()
    {
        if(Instance == null)
        {
            Instance = new Block_Selector_Manager();
            Bukkit.getPluginManager().registerEvents(Instance, Main.instance);
        }
         return Instance;
    }

    public void SelectBlocks(int amount, Player player, Consumer<ArrayList<Block>> acction)
    {
      this.players_block.add(new Players_blocks(amount,player.getUniqueId(),acction));
    }


    @EventHandler
    private void OnBreak(BlockBreakEvent event)
    {
      for(Players_blocks  pb:players_block)
      {
          if(pb.uuid.equals(event.getPlayer().getUniqueId()))
          {
               if(pb.Add_Block(event.getBlock()))
               {
                   players_block.remove(pb);
               }
               event.setCancelled(true);
              break;
          }
      }
    }


    public static class Players_blocks
    {

        public Players_blocks(int amount, UUID uuid, Consumer<ArrayList<Block>> acction) {
            this.amount = amount;
            this.uuid = uuid;
            this.acction = acction;
        }

        public boolean Add_Block(Block block)
        {
            result.add(block);
            amount--;
            if(amount<=0)
            {
                this.acction.accept(result);
                return true;
            }
            return false;
        }

        public int amount;
        public UUID uuid;
        public ArrayList<Block> result = new ArrayList<>();
        public  Consumer<ArrayList<Block>> acction;
    }

}

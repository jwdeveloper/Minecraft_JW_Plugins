package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_EventHandler;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class GUI_ListPlayers extends GUI_List
{

    public  ArrayList<String> players = new ArrayList<>();
    private ArrayList<GUI_Button>  items = new ArrayList<>();
    public GUI_ListPlayers(GUI_Inventory parent, String name)
    {
        super(parent, name);

       this.onOpen.add((a,b,c)->
       {
           this.ClearItems();
           items.clear();

           players.forEach(uuid ->
           {
               Bukkit.getScheduler().runTask(GUI_EventHandler.Instnace().plugin,()->
               {
                   try
                   {
                       OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                       GUI_Button head = GUI_Button_Factory.Player_head(player.getName());
                       head.tag = uuid;
                       head.acction = GUI_Acctions.CLICK;
                       head.Set_Text(player.getName());
                       this.items.add(head);
                       this.addItems(items);
                       this.refresh();
                   }
                   catch (Exception e)
                   {

                   }

               });
           });
           this.refresh();
       });



    }

    public void remove_player(String name)
    {
        this.players.remove(name);
    }
    public void add_player(String name)
    {
        this.players.add(name);
    }

    public void clear()
    {
        this.players.clear();
    }


    public void open(Player player,ArrayList<String> players)
    {
        this.players = players;
        this.open(player);
    }
}

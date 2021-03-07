package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Events.GUI_ClickEvent;
import GUI.Inventory.Forms.Basic.GUI_SelectList;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_EventHandler;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class GUI_Select_Player extends GUI_SelectList
{

    public GUI_Select_Player(GUI_Inventory parent, String name, GUI_ClickEvent event)
    {
        super(parent, name, new ArrayList<>());
        this.items = new ArrayList<>();

        Inicialize();
        this.onSelect.add(event);
    }
    public GUI_Select_Player(GUI_Inventory parent, String name)
    {
        super(parent, name, new ArrayList<>());
        this.items = new ArrayList<>();

        Inicialize();
    }

    public void Inicialize()
    {
        this.onOpen.add((a,b,c)->
        {
            OfflinePlayer[]  players = Bukkit.getOfflinePlayers();

            if(players.length!=this.items.size())
            {
                this.items = new ArrayList<>();
                Arrays.stream(players).forEach(p ->
                {
                    Bukkit.getScheduler().runTask(GUI_EventHandler.Instnace().plugin,()->
                    {
                        GUI_Button head = GUI_Button_Factory.Player_head(p.getName());
                        head.tag = p.getUniqueId().toString();
                        head.acction = GUI_Acctions.CLICK;
                        head.Set_Text(p.getName());
                        this.items.add(head);
                        this.addItems(items);
                        this.refresh();
                    });
                });
            }
        });
    }
}

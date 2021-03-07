package GUI.Inventory.Forms.Events;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_SelectList;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import ORM.Entitis.Event_Entity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GUI_Event_Window_List extends GUI_SelectList
{

    GUI_Event_Window gui_event_window = new GUI_Event_Window(this);
    ArrayList<Event_Entity> event_entities = new ArrayList<>();
    public GUI_Event_Window_List(GUI_Inventory parent, String name)
    {
        super(parent, name, new ArrayList<>());

        gui_event_window= new GUI_Event_Window(this);

        this.onOpen.add((a,b,c)->
        {
            ArrayList<GUI_Button> buttons = new ArrayList<>();
            event_entities.stream().forEach(e -> buttons.add(new GUI_Button(Material.BOOK,e.name,e.id, GUI_Acctions.CLICK,e.get_full_description())));
            this.addItems(buttons);
        });

        this.onSelect.add((a,b,c)->
        {

            event_entities.stream().forEach(e ->
            {
                if(a.tag.equalsIgnoreCase(e.id))
                {
                    Bukkit.getConsoleSender().sendMessage("2");
                    gui_event_window.open(player,e);
                    return;
                }
            });


        });

    }

    public void open(Player player,ArrayList<Event_Entity> events_entity)
    {
        add_events_windows(events_entity);

        this.open(player);
    }

    public void add_events_windows(ArrayList<Event_Entity> events_entity)
    {
        this.event_entities.clear();
        events_entity.forEach(event_entity ->
        {
            event_entities.add(event_entity);
        });
    }


}

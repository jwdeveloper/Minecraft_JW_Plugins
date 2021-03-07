package GUI.Inventory.Forms.Events;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import ORM.Entitis.Event_Entity;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GUI_Event_Window extends GUI_List
{



    Event_Entity event_entity;

    public GUI_Event_Window(GUI_Inventory parent)
    {
        super(parent, "Event");

        this.enableEditing=true;
        this.enableDeleting=true;
        this.hide_EditButton=false;


        this.onOpen.add((a,b,c)->
        {
            ArrayList<GUI_Button> gui_buttons = new ArrayList<>();
            this.setName("Events: "+event_entity.name);

            ArrayList<String> events  = event_entity.events;
            for(int i=0;i<events.size();i++)
            {
                gui_buttons.add(new GUI_Button(Material.CREEPER_BANNER_PATTERN,events.get(i),String.valueOf(i), GUI_Acctions.CLICK));
            }
            this.addItems(gui_buttons);
        });

        this.onEdit.add((a,b,c)->
        {

            int index = Integer.valueOf(a.tag);
            String event = event_entity.events.get(index);

           this.openTextbox("Change event",event,(x,y)->
           {
               event_entity.events.set(index,x);
               this.open(player);
           });

        });
        this.onDelete.add((a,b,c)->
        {
            event_entity.events.remove(a.tag);
            this.open(player);
        });
        this.onClickInsert.add((a,b,c)->
        {
            this.openTextbox("Enter event","",(x,y)->
            {
                event_entity.events.add(x);
                this.open(player);
            });

        });
    }

    public void open(Player player,Event_Entity event_entity)
    {
        this.event_entity = event_entity;
        this.player = player;
        this.open(player);
    }

}

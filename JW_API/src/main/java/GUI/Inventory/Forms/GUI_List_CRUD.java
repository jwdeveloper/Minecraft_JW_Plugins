package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GUI_List_CRUD extends GUI_List
{

    ArrayList<String> strings = new ArrayList<>();

    public GUI_List_CRUD(GUI_Inventory parent, String name)
    {
        super(parent, name);

        this.enableEditing=true;
        this.enableDeleting=true;
        this.hide_EditButton=false;
        this.hide_DeleteAll=false;
        this.enableDeleteAll=true;


        this.onOpen.add((a,b,c)->
        {
            ArrayList<GUI_Button> gui_buttons = new ArrayList<>();

            for(int i=0;i<strings.size();i++)
            {
                gui_buttons.add(new GUI_Button(Material.CREEPER_BANNER_PATTERN,strings.get(i),String.valueOf(i), GUI_Acctions.CLICK));
            }
            this.addItems(gui_buttons);
        });

        this.onEdit.add((a,b,c)->
        {
            int index = Integer.parseInt(a.tag);
            this.textbox.onText  = (x,y)->
            {
                strings.remove(index);
                strings.add(index,x);
                this.open(player);
            };
            this.openTextBox("New",strings.get(index));
        });
        this.onDelete.add((a,b,c)->
        {
            int index = Integer.parseInt(a.tag);
            strings.remove(index);
            this.open(player);
        });
        this.onClickInsert.add((a,b,c)->
        {
            this.textbox.onText  = (x,y)->{
                strings.add(x);
                this.open(player);
            };
            this.openTextBox("New","");
        });
        this.onDeleteAll.add((a,b,c)->
        {
          this.strings.clear();
          this.open(player);
        });


    }



    public void open(Player player,ArrayList<String> string)
    {
        this.strings =string;
        this.open(player);
    }
}

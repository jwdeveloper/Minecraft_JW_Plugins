package GUI.Inventory.Forms.Basic;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;

import java.util.ArrayList;

public class GUI_SelectList extends GUI_List
{

    public ArrayList<GUI_Button> items;

    public GUI_SelectList(GUI_Inventory parent, String name, ArrayList<GUI_Button> items)
    {
        super(parent, name);

        hide_InsertButton = true;
        hide_DeleteButting=true;
        hide_EditButton=true;
        enableSelecting=true;
        this.items = items;

        this.onOpen.add((a,b,c)->{

            this.addItem(0,7,new GUI_Button(this.backgroundMaterial," ", GUI_Acctions.BACKGROUND));
            this.addItem(0,6,new GUI_Button(this.backgroundMaterial," ", GUI_Acctions.BACKGROUND));
            this.addItems(this.items);
        });
    }
    public GUI_SelectList(GUI_Inventory parent, String name)
    {
        super(parent, name);

        hide_InsertButton = true;
        hide_DeleteButting=true;
        hide_EditButton=true;
        enableSelecting=true;

        this.onOpen.add((a,b,c)->{

            this.addItem(0,7,new GUI_Button(this.backgroundMaterial," ", GUI_Acctions.BACKGROUND));
            this.addItem(0,6,new GUI_Button(this.backgroundMaterial," ", GUI_Acctions.BACKGROUND));
        });
    }
}

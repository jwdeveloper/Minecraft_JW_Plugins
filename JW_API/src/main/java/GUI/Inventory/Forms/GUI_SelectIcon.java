package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_ClickEvent;
import GUI.Inventory.Forms.Basic.GUI_SelectList;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_Inventory;

import java.util.ArrayList;

public class GUI_SelectIcon extends GUI_SelectList
{

    public GUI_SelectIcon(GUI_Inventory parent, String name, GUI_ClickEvent event)
    {
        super(parent, name, new ArrayList<>());
        this.items = GUI_Button_Factory.Get_Materials();
        this.onSelect.add(event);
    }

    public GUI_SelectIcon(GUI_Inventory parent, String name)
    {
        super(parent, name, new ArrayList<>());
        this.items = GUI_Button_Factory.Get_Materials();
    }
}

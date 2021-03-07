package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.Forms.Basic.GUI_SelectList;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import Managers.File_Manager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GUI_ListFiles extends GUI_SelectList
{
    String path = new String();
    ArrayList<GUI_Button> gui_buttons = new ArrayList<>();

    public GUI_ListFiles(GUI_Inventory parent, String name)
    {
        super(parent, name);

        this.onOpen.add((a,b,c)->
        {
            gui_buttons.clear();
            for(String file: File_Manager.Get_FileNames(path))
            {
                gui_buttons.add(new GUI_Button(Material.PAPER,file,file, GUI_Acctions.CLICK));
            }

            this.addItems(gui_buttons);
        });
    }


    public void open(Player player,String path)
    {
        this.path = path;
        this.open(player);
    }
}

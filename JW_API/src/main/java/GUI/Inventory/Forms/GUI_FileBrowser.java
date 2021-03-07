package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.GUI_List_CRUD;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import GUI.Inventory.GUI_Itemstack;
import Managers.File_Manager;
import jw_api.jw_api.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GUI_FileBrowser extends GUI_List_CRUD
{

    public String directory = File_Manager.CurrentPath();
    public String[] file_types;

    public GUI_FileBrowser(GUI_Inventory parent, String name)
    {
        super(parent, name);

        this.onOpen.add((a,b,c)->
        {
          this.addItems(Load_Files());
        });

    }


    public ArrayList<GUI_Button> Load_Files()
    {
        ArrayList<GUI_Button> result = new ArrayList<>();

        try
        {
            Files.walk(Paths.get(directory)).filter(Files::isRegularFile).forEach( f ->
            {
                for(int i=0;i<file_types.length;i++)
                {
                    if(f.getFileName().toString().contains(file_types[i]))
                        result.add(new GUI_Button(Material.PAPER,f.getFileName().toString(),f.toAbsolutePath().toString(), GUI_Acctions.CLICK));
                }

            });
        }
        catch (IOException e)
        {

        }


        return  result;
    }

    public void Set_Directiory(String directory)
    {
        this.directory = directory;
    }

    public void Open(Player player, String ... file_type)
    {
        this.file_types = file_type;
        this.open(player);
    }



}

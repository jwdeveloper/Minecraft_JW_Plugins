package GUI.Inventory.Forms;

import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class GUI_Panel extends GUI_Inventory
{

    public GUI_Panel(String name)
    {
        super(name, 6);
       this.debug=true;
        this.onOpen.add((a,b,c)->{
           this.drawBorderBackground(Material.RED_STAINED_GLASS_PANE);
           this.addItem(4,4, GUI_Button_Factory.CancelButton());
           this.refresh();
        });
        this.onClicekCancel.add((a,b,c)->{
            Bukkit.getConsoleSender().sendMessage(a.tag);
            Is_TaskRunning=false;
            Bukkit.getServer().getScheduler().cancelTask(task_id);
            this.close();
        });
    }



    int counter =0;
    int slot =30;
    int task_id = 0;
    public void start_Animation()
    {
        Material [] back = new Material[7];

        back[0] = Material.PINK_STAINED_GLASS_PANE;
        back[1] = Material.MAGENTA_STAINED_GLASS_PANE;
        back[2] = Material.PURPLE_STAINED_GLASS_PANE;
        back[3] = Material.BLUE_STAINED_GLASS_PANE;
        back[4] = Material.CYAN_STAINED_GLASS_PANE;
        back[5] = Material.LIGHT_BLUE_STAINED_GLASS_PANE;

       Is_TaskRunning=true;
     task_id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0],()->
        {

            this.drawBorderBackground(back[counter%6]);
            this.setAlert("Wczytywanie: "+counter+"/1000%");
           // this.addItem(0,counter,  new GUI_Button(Material.DIAMOND,"DS","dsd", GUI_Acctions.BACKGROUND));
            this.refresh();
            counter++;

        },1l,2l);
    }


}

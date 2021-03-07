package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GUI_LoadingBar extends GUI_Inventory
{

    int counter =0;
    int task_id =0;
    public GUI_LoadingBar(GUI_Inventory parent, String name)
    {
        super(name, 3);
        this.setParent(parent);
        this.onOpen.add((a,b,c)->
        {
            this.drawFilledBackground(Material.GRAY_STAINED_GLASS_PANE);
            this.drawBorderBackground(Material.BLACK_STAINED_GLASS_PANE);
            this.refresh();
        });

    }


    public void Run_Task()
    {
        Is_TaskRunning=true;
        task_id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0],()->
        {
            int state = (counter*70/100)/10;
            this.setAlert(ChatColor.DARK_GREEN+"["+counter+"/100%]");
            for(int i=0;i<state;i++)
            {
                this.addItem(1,1+i,  new GUI_Button(Material.LIME_STAINED_GLASS_PANE,"","", GUI_Acctions.BACKGROUND));
            }
            this.refresh();
            counter++;

            if(counter>=100)
            {
                Is_TaskRunning=false;
                Bukkit.getServer().getScheduler().cancelTask(task_id);
                this.getParent().open(this.player);
            }

        },1l,2l);
    }

}

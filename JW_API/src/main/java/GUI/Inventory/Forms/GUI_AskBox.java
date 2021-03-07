package GUI.Inventory.Forms;


import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Events.GUI_ClickEvent;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import GUI.Inventory.Languages.GUI_Language_Manager;
import org.bukkit.Material;

public class GUI_AskBox extends GUI_Inventory
{

    public GUI_ClickEvent onYes = (a, b, c)->{};
    public GUI_ClickEvent onNo= (a, b, c)->{};

    public GUI_AskBox(GUI_Inventory parent,String name)
    {

        super(name, 5);
        this.setParent(parent);


        onOpen.add((a,b,c)->{
            this.drawFilledBackground(Material.BLACK_STAINED_GLASS_PANE);
            this.addItem(2,2,new GUI_Button(Material.LIME_STAINED_GLASS_PANE, GUI_Language_Manager.lang().Yes,"Yes", GUI_Acctions.CLICK));
            this.addItem(2,1,new GUI_Button(Material.LIME_STAINED_GLASS_PANE,GUI_Language_Manager.lang().Yes,"Yes", GUI_Acctions.CLICK));
            this.addItem(2,3,new GUI_Button(Material.LIME_STAINED_GLASS_PANE,GUI_Language_Manager.lang().Yes,"Yes", GUI_Acctions.CLICK));
            this.addItem(2,5,new GUI_Button(Material.RED_STAINED_GLASS_PANE,GUI_Language_Manager.lang().No,"No", GUI_Acctions.CLICK));
            this.addItem(2,6,new GUI_Button(Material.RED_STAINED_GLASS_PANE,GUI_Language_Manager.lang().No,"No", GUI_Acctions.CLICK));
            this.addItem(2,7,new GUI_Button(Material.RED_STAINED_GLASS_PANE,GUI_Language_Manager.lang().No,"No", GUI_Acctions.CLICK));
            this.refresh();
        });

        onClickItem.add((a,b,c)->{
            if(a.tag.equalsIgnoreCase("Yes"))
                onYes.Execute(a,b,c);
            if(a.tag.equalsIgnoreCase("No"))
                onNo.Execute(a,b,c);

            this.close();
            this.getParent().open(player);
        });
    }





}

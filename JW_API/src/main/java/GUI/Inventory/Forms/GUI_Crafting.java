package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Events.GUI_ClickEvent;
import GUI.Inventory.Forms.Basic.GUI_Details;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import ORM.Entitis.Crafting_Recepture_Entity;
import ORM.Entitis.Crafting_entity;
import org.bukkit.Material;

public class GUI_Crafting extends GUI_Details<Crafting_Recepture_Entity>
{


    int size = 3;

    public GUI_SelectIcon gui_selectIcon;

    private GUI_ClickEvent on_slot_selected;

    public GUI_Crafting(GUI_Inventory parent, String name)
    {
        super(parent, name, 5);

        gui_selectIcon = new GUI_SelectIcon(this,"Select material",on_slot_selected);

        this.onOpen.add((a,c,b)->
        {
            this.drawBorderBackground(Material.BROWN_STAINED_GLASS_PANE);
            this.addItem(1,4,new GUI_Button(Material.BROWN_STAINED_GLASS_PANE," ", GUI_Acctions.BACKGROUND));
            this.addItem(2,4,new GUI_Button(Material.BROWN_STAINED_GLASS_PANE," ", GUI_Acctions.BACKGROUND));
            this.addItem(3,4,new GUI_Button(Material.BROWN_STAINED_GLASS_PANE," ", GUI_Acctions.BACKGROUND));


            for(int i=0;i<size;i++)
            {
                for(int j=0;j<size;j++)
                {
                    int index = i*size+j;
                    Crafting_entity crafting_entity = this.detail.Get_Item(index);
                    this.addItem(1+i,1+j,new GUI_Button(crafting_entity.material,crafting_entity.get_name(),String.valueOf(index), GUI_Acctions.CLICK,String.valueOf(crafting_entity.amount)));
                }
            }

        });

        this.onClickItem.add((a,b,c)->
        {
            on_slot_selected = (x,y,z)->
            {
                Crafting_entity crafting_entity= this.detail.Get_Item(Integer.valueOf(a.tag));

                if(crafting_entity == null)
                {
                    crafting_entity = new Crafting_entity();
                }
                crafting_entity.material = x.getType();

                this.open(player);
            };
            this.gui_selectIcon.open(player);
        });
    }
}

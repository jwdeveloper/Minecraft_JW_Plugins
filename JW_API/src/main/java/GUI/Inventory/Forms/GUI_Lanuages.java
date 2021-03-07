package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_Details;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import GUI.Inventory.Languages.GUI_Language_Manager;
import Languages.Language;
import Languages.Language_Manager;
import org.bukkit.Material;

public class GUI_Lanuages extends GUI_Details<Language>
{

    public GUI_Lanuages(GUI_Inventory parent)
    {
        super(parent, GUI_Language_Manager.lang().Select+" "+GUI_Language_Manager.lang().Language);

        this.onOpen.add((a,b,c)->
        {
            this.setName(GUI_Language_Manager.lang().Select+" "+GUI_Language_Manager.lang().Language+ "  ["+Language_Manager.Instance().language+"]");
            this.drawBorderBackground(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

            this.addItem_PlayerHead(3,3,"Earth",new GUI_Button(Material.PLAYER_HEAD,GUI_Language_Manager.lang().Polish,Language.Polish.toString(),GUI_Acctions.CLICK));
            this.addItem_PlayerHead(3,5,"Earth",new GUI_Button(Material.PLAYER_HEAD,GUI_Language_Manager.lang().English,Language.English.toString(),GUI_Acctions.CLICK));

        });

        this.onClickItem.add((a,b,c)->
        {
            Language type =  Language.valueOf(a.tag);
            if(type!=null)
            {
              this.detail = type;
              Language_Manager.Instance().Set_Language(type);
              this.open(player);
            }
        });
    }
}

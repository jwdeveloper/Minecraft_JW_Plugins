package GUI.Inventory.Forms.Basic;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.GUI_Lanuages;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import GUI.Inventory.Languages.GUI_Language_Manager;
import org.bukkit.Material;

public class GUI_Menu_template extends GUI_Inventory
{
    private boolean Lang_btn =true;

  //  GUI_Lanuages gui_lanuages;

    int max_size_title = 38;

    public GUI_Menu_template(String name, int height)
    {
        super(name, height);
        Center_title(name);
       /* gui_lanuages = new GUI_Lanuages(this);
        this.onOpen.add((a,b,c)->
        {

            if(Lang_btn == true)
            {
                this.addItem_PlayerHead(0,8,"Earth",new GUI_Button(Material.PLAYER_HEAD,GUI_Language_Manager.lang().Language,"Lang",GUI_Acctions.CLICK));
            }


        });

        this.onClickItem.add((a,b,c)->
        {
            if(a.tag.equalsIgnoreCase("Lang"))
            {
                this.setAlert("");
                gui_lanuages.open(b);
                return;
            }
        });*/
    }
    public void Center_title(String title)
    {
     //   title = "1 2 3 4 5 6 7 8 9";
        String result= new String();
        title = "§3§l"+title;
       int title_size = title.length();

       int start = (max_size_title/2)- (title_size/2);
       int l =0;

        for(int i=0;i<start+title_size;i++)
        {
            if(i >=start)
            {
                result += title.charAt(l);
                l+=1;
            }
            else
                result+=" ";
        }
        this.setName(result);
    }
    public void Visible_Lang_BTN(boolean state)
    {
        Lang_btn = state;
    }

}

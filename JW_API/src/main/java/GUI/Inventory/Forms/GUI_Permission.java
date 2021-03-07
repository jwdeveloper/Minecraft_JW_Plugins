package GUI.Inventory.Forms;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_List;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import ORM.Entitis.Permission;
import ORM.Repository.Permission_Repository_template;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class GUI_Permission extends GUI_List
{

    Permission_Repository_template permission_repository_template;

    public GUI_Permission(GUI_Inventory parent,Permission_Repository_template permission_repository_template)
    {
        super(parent, "Permissions");
        this.permission_repository_template = permission_repository_template;

        this.enableEditing=true;
        this.enableDeleting=true;
        this.hide_EditButton=false;



        this.onOpen.add((a,b,c)->
        {
            ArrayList<GUI_Button> gui_buttons = new ArrayList<>();
            permission_repository_template.Get_Many(null).forEach(q ->
            {
                gui_buttons.add(new GUI_Button(Material.CREEPER_BANNER_PATTERN,q.name,q.id, GUI_Acctions.CLICK));
            });
            this.addItems(gui_buttons);
        });

        this.onEdit.add((a,b,c)->
        {
            Permission permission =  permission_repository_template.Get_One(a.tag);
            player.sendMessage( ChatColor.BOLD+"Current name -> "+ChatColor.RESET+permission.name);
            this.opentextbox("Change name",(x,y)->
            {
                permission.name = x;
                permission_repository_template.Update_One(a.tag,permission);
                this.open(player);
            });
        });
        this.onDelete.add((a,b,c)->
        {
            permission_repository_template.Delete_One(a.tag,null);
            this.open(player);
        });
        this.onClickInsert.add((a,b,c)->
        {
            this.opentextbox("Enter name",(x,y)->
            {
                Permission permission = new Permission();
                permission.name = x;
                permission_repository_template.Insert_One(permission);
                this.open(player);
            });
        });
    }
}

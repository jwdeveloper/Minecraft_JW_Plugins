package GUI.Inventory.Forms.Database;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_Details;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_Inventory;
import ORM.Databases.MySQL.MySQL_Manager;
import ORM.Entitis.SQL_Database_Entity;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GUI_Database_Form extends GUI_Details<SQL_Database_Entity>
{

    public GUI_Database_Form(GUI_Inventory parent, String name)
    {
        super(parent, name, 5);

        this.onOpen.add((x,y,z)->
        {
            this.drawBorderBackground(Material.YELLOW_STAINED_GLASS_PANE);
            this.addItem(1,1,new GUI_Button(Material.PAPER,"§6Server","serwer", GUI_Acctions.CLICK,detail.server));
            this.addItem(1,3,new GUI_Button(Material.PAPER,"§6Database","database", GUI_Acctions.CLICK,detail.database));
            this.addItem(1,5,new GUI_Button(Material.PLAYER_HEAD,"§6User","user", GUI_Acctions.CLICK,detail.user));
            this.addItem(1,7,new GUI_Button(Material.NAME_TAG,"§6Password","password", GUI_Acctions.CLICK,detail.password));

            this.addItem(3,1,new GUI_Button(GUI_Button_Factory.BoolMaterial(detail.is_enabled,Material.GREEN_CONCRETE,Material.RED_CONCRETE),"§6Enabled","database_enable", GUI_Acctions.CLICK,String.valueOf(detail.is_enabled)));
            this.addItem(3,3,new GUI_Button(Material.CLOCK,"§6Synchornize time","Sync", GUI_Acctions.CLICK,String.valueOf(detail.sync_time)));

            this.addItem(0,8,new GUI_Button(GUI_Button_Factory.BoolMaterial(detail.is_connected,Material.REDSTONE_TORCH,Material.LEVER),"§6Click to check connection","connection", GUI_Acctions.CLICK,detail.is_connected_Str()));
        });

        this.onClickItem.add((a,b,c)->
        {

            if(a.tag.equalsIgnoreCase("connection"))
            {

                    MySQL_Manager.Check_Connection(detail,(x)->
                    {
                        Bukkit.getScheduler().runTask(Main.instance,()->
                        {
                            if(x==true)
                            {
                                this.setAlert(ChatColor.DARK_GREEN+" Connected");
                            }
                            else
                            {
                                this.setAlert(ChatColor.DARK_RED+" No connection");
                            }

                        detail.is_connected = x;
                        this.open(player);
                        });
                    });
                this.setAlert(ChatColor.YELLOW+" checking connection...");
                this.open(player);


            }
            if(a.tag.equalsIgnoreCase("database_enable"))
            {
                    detail.is_enabled = !detail.is_enabled;
                    this.open(player);
                    return;
            }
            if(a.tag.equalsIgnoreCase("Sync"))
            {
                this.openTextBoxINT("synchronize time [minutes]",String.valueOf(detail.sync_time),(x,y)->
                {
                    detail.sync_time = Integer.valueOf(x);
                    this.open(player);
                });
                return;
            }

            if(a.tag.equalsIgnoreCase("serwer"))
            {
                this.openTextbox("serwer",detail.server,(x,y)->
                {
                    detail.server = x;
                    this.open(player);
                });
                return;
            }
            if(a.tag.equalsIgnoreCase("database"))
            {
                this.openTextbox("database",detail.database,(x,y)->
                {
                    detail.database = x;
                    this.open(player);
                });
                return;
            }
            if(a.tag.equalsIgnoreCase("user"))
            {
                this.openTextbox("user",detail.user,(x,y)->
                {
                    detail.user = x;
                    this.open(player);
                });
                return;
            }
            if(a.tag.equalsIgnoreCase("password"))
            {
                this.openTextbox("password",detail.password,(x,y)->
                {
                    detail.password = x;
                    this.open(player);
                });
                return;
            }

        });
    }
}

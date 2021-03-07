package GUI.Inventory.Forms.Basic;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.GUI_List_CRUD;
import GUI.Inventory.Forms.GUI_SelectIcon;
import GUI.Inventory.Forms.GUI_Select_Player;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_Inventory;
import ORM.Entitis.I_Entity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class GUI_Details<T> extends GUI_Inventory
{

    public T detail;
    private Class type;
    protected  GUI_List_CRUD gui_list;
    protected GUI_SelectIcon gui_selectIcon;
    protected GUI_List gui_players_list;

    HashMap<GUI_Button,Field> buttons = new HashMap<>();

    private Field Find_Property(String property_name,Class property_type)
    {
        for (Field f : type.getFields())
        {
           if(f.getName().contains(property_name) && property_type == f.getType())
           {
               return f;
           }
        }
       return null;
    }

    public void Add_Button(GUI_Button button, int height,int width)
    {
        button.position = new Vector(width,height,0);
        this.buttons.put(button,null);
    }
    public void Add_ButtonText(String property, String description,Material icon, int height,int width)
    {
        Field field = Find_Property(property,String.class);

        if(field == null)
            return;

        GUI_Button button = new GUI_Button(icon,description,"Property_BTN", GUI_Acctions.CLICK," ");
        button.position = new Vector(width,height,0);
        button.OnClick = (a,b,c)->
            {
                try
                {
                    if(detail == null)
                        return;
                    String value = field.get(detail).toString();
                    this.openTextbox( "Set value",value,(x,y)->
                    {
                        try
                        {
                            field.setAccessible(true);
                            field.set(this.detail,x);
                        }
                        catch (IllegalAccessException ex)
                        {
                            Bukkit.getConsoleSender().sendMessage(ex.getMessage());
                        }
                        this.open(player);
                    });
                }
                catch (IllegalAccessException e)
                {

                }
            };
        buttons.put(button,field);
    }
    public void Add_ButtonNumber(String property, String description,Material icon, int height,int width)
    {
        Field field = Find_Property(property,int.class);

        if(field == null)
            return;

        GUI_Button button = new GUI_Button(icon,description,"Property_BTN", GUI_Acctions.CLICK," ");
        button.position = new Vector(width,height,0);
        button.OnClick = (a,b,c)->
        {
            try
            {
                if(detail == null)
                    return;
                String value = field.get(detail).toString();
                this.openTextBoxINT( "Set value",value,(x,y)->
                {
                    try
                    {
                        field.setAccessible(true);
                        field.set(this.detail,x);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Bukkit.getConsoleSender().sendMessage(ex.getMessage());
                    }
                    this.open(player);
                });
            }
            catch (IllegalAccessException e)
            {

            }
        };
        buttons.put(button,field);
    }
    public void Add_ButtonBool(String property, String name,Material icon ,int height,int width)
    {
        Field field = Find_Property(property,boolean.class);

        if(field == null)
            return;

        GUI_Button button = new GUI_Button(icon,name,"Property_BTN", GUI_Acctions.CLICK);
        button.position = new Vector(width,height,0);
        button.OnClick = (a,b,c)->
        {
            try
            {
                if(detail == null)
                    return;
                boolean value = field.getBoolean(detail);
                field.set(detail,!value);
                button.Set_Highlighted(!value);

                this.open(b);
            }
            catch (IllegalAccessException e)
            {

            }
        };
        buttons.put(button,field);
    }

    public void Add_ButtonPlayerList(String property, String name,Material icon, int height,int width)
    {

        Field field = Find_Property(property,ArrayList.class);

        if(field == null)
            return;

        if(gui_list==null)
        {
            gui_players_list = new GUI_List(this,"GUI_List_player");
        }


        GUI_Button button = new GUI_Button(icon,name,"Property_BTN", GUI_Acctions.CLICK," ");
        button.position = new Vector(width,height,0);
        button.OnClick = (a,b,c)->
        {
            try
            {
                if(detail == null)
                    return;
                ArrayList<String> value =  (ArrayList<String>)field.get(detail);
                gui_list.setName(name);
                gui_list.open(b,value);
            }
            catch (IllegalAccessException e)
            {

            }
        };
        buttons.put(button,field);
    }

    public void Add_ButtonList(String property, String name,Material icon, int height,int width)
    {

        Field field = Find_Property(property,ArrayList.class);

        if(field == null)
            return;

          if(gui_list==null)
          {
              gui_list = new GUI_List_CRUD(this,"GUI_List");
          }

        GUI_Button button = new GUI_Button(icon,name,"Property_BTN", GUI_Acctions.CLICK," ");
        button.position = new Vector(width,height,0);
        button.OnClick = (a,b,c)->
        {
            try
            {
                if(detail == null)
                    return;
                ArrayList<String> value =  (ArrayList<String>)field.get(detail);
                gui_list.setName(name);
                gui_list.open(b,value);
            }
            catch (IllegalAccessException e)
            {

            }
        };
        buttons.put(button,field);
    }





    public GUI_Details(GUI_Inventory parent,String name)
    {
        super(name, 6);
        this.setParent(parent);
        Initialize();
    }
    public GUI_Details(GUI_Inventory parent,String name,Class type)
    {
        super(name, 6);
        this.type =type;
        this.setParent(parent);
        Initialize();
    }
    public GUI_Details(GUI_Inventory parent,String name,int size)
    {
        super(name, size);
        this.setParent(parent);
        Initialize();
    }
    public void Initialize()
    {
        this.onOpen.add((a,b,c)->
        {
            if(detail!=null)
            {
                buttons.forEach((btn,f) ->
                {
                    try
                    {
                        if(f!=null)
                        btn.Set_Description(f.get(detail).toString());

                        this.addItem(btn.position.getBlockY(),btn.position.getBlockX(),btn);
                    }
                    catch (IllegalAccessException e)
                    {

                    }
                });
            }

            this.addItem(this.height-1,8, GUI_Button_Factory.ExitButton());
        });


        this.onClickExit.add((a,b,c)->
        {
            if(this.getParent()!=null)
            {
                this.close();
                this.getParent().open(b);
            }
            else
            {
                this.close();
            }
        });
    }


    public void open(Player player, T detail)
    {
        this.detail = detail;
        this.open(player);
    }



}

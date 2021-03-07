package GUI.Inventory;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Languages.GUI_Language_Manager;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class GUI_Button_Factory
{

    public static GUI_Button EmptyButton()
    {
        return new GUI_Button(Material.SUNFLOWER," "," ",GUI_Acctions.EMPTY);
    }
    public static GUI_Button EditButton()
    {
        return new GUI_Button(Material.FEATHER,GUI_Language_Manager.lang().Edit, GUI_Acctions.EDIT);
    }
    public static GUI_Button RemoveButton()
    {
        return new GUI_Button(Material.BARRIER,GUI_Language_Manager.lang().Delete, GUI_Acctions.DELETE);
    }
    public static Material BoolMaterial(boolean state,Material _true,Material _false)
    {
      return state==true?_true:_false;
    }
    public static GUI_Button InsertButton()
    {
        return new GUI_Button(Material.LEGACY_BOOK_AND_QUILL,GUI_Language_Manager.lang().Insert, GUI_Acctions.INSERT);
    }
    public static GUI_Button CopyButton()
    {
        return new GUI_Button(Material.SLIME_SPAWN_EGG,GUI_Language_Manager.lang().Copy, GUI_Acctions.COPY);
    }

    public static GUI_Button BackGroundButton(Material material)
    {
        return new GUI_Button(material," ", GUI_Acctions.BACKGROUND);
    }
    public static GUI_Button SearchButton()
    {
        return new GUI_Button(Material.ENDER_EYE,GUI_Language_Manager.lang().Search, GUI_Acctions.SEARCH);
    }
    public static GUI_Button CancelButton()
    {
        return new GUI_Button(Material.WITHER_SKELETON_SKULL,GUI_Language_Manager.lang().Cancel, GUI_Acctions.CANCEL);
    }
    public static GUI_Button DeleteALLButton()
    {
        return new GUI_Button(Material.TNT,GUI_Language_Manager.lang().Delete_all,"deleteall", GUI_Acctions.DELETE);
    }
    public static GUI_Button ExitButton()
    {
        GUI_Button itemstack = new GUI_Button(Material.TIPPED_ARROW,GUI_Language_Manager.lang().Back, GUI_Acctions.EXIT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();

        PotionData data = new PotionData(PotionType.STRENGTH,false,false);
        meta.setBasePotionData(data);

        itemstack.setItemMeta(meta);
        return itemstack;
    }
    public static GUI_Button NextButton(PotionType color)
    {
        GUI_Button itemstack = new  GUI_Button(Material.TIPPED_ARROW, GUI_Language_Manager.lang().Next, GUI_Acctions.RIGHT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }
    public static GUI_Button BackButton(PotionType color)
    {
        GUI_Button itemstack = new GUI_Button(Material.TIPPED_ARROW, GUI_Language_Manager.lang().previous, GUI_Acctions.LEFT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }
    public static <T extends Enum<T>> ArrayList<GUI_Button> Get_Enum(Material icon,Class<T> enumData)
    {

        ArrayList<GUI_Button> icons = new ArrayList<>();
        for(T item :enumData.getEnumConstants())
        {
            icons.add(new GUI_Button(icon,item.name(),item.name(),GUI_Acctions.CLICK));
        }
        return icons;
    }
    public static ArrayList<GUI_Button> Get_Materials()
    {
        ArrayList<GUI_Button> icons = new ArrayList<>();
        for(GUI_Itemstack item :GUI_Itemstack.GetAllItems)
        {
            icons.add(new GUI_Button(item.getType(),item.getType().name(),item.getType().name(),GUI_Acctions.CLICK));
        }
        return icons;
    }
    public static ArrayList<GUI_Button> Get_Materials_spawners()
    {
        ArrayList<GUI_Button> icons = new ArrayList<>();
        for(GUI_Itemstack item :GUI_Itemstack.GetAllItems)
        {
            if(item.getType().name().contains("SPAWN_EGG"))
            {
                String name_item = item.getType().name();
                String name =  name_item.substring(0,name_item.indexOf("SPAWN")-1);
                icons.add(new GUI_Button(item.getType(),name,item.getType().name(),GUI_Acctions.CLICK));
            }

        }
        return icons;
    }
    public static ArrayList<GUI_Button> Get_Materials_food()
    {
        ArrayList<GUI_Button> icons = new ArrayList<>();
        for(GUI_Itemstack item :GUI_Itemstack.GetAllItems)
        {
            if(item.getType().isEdible())
              icons.add(new GUI_Button(item.getType(),item.getType().name(),item.getType().name(),GUI_Acctions.CLICK));
        }
        return icons;
    }

    public static GUI_Button Player_head(String name)
    {
        GUI_Button skull = new GUI_Button(Material.PLAYER_HEAD, name);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        skull.setItemMeta(meta);
        return skull;
    }
}

package GUI.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GUI_Itemstack extends ItemStack
{

    public static  ArrayList<GUI_Itemstack> GetAllItems = new ArrayList<>();

    public static void Load_Items()
    {

        List<String> materials_string = new ArrayList<>();

        try
        {

           Material[] materials = Material.values();
            for(int i=0;i<materials.length;i++)
            {
                String name = materials[0].toString().toLowerCase().trim();
                if(name.contains("LEGACY"))
                    continue;
                String display_name = name.substring(name.indexOf(":")+1,name.length());
                if(materials[i]!=Material.AIR)
                    GetAllItems.add(new GUI_Itemstack(materials[i],display_name,display_name));
            }


        }
        catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage("Wczytywalnie prbola "+e.getMessage());
        }
    }


    public GUI_Itemstack(Material material)
    {
        super(material);
    }
    public GUI_Itemstack(Material material,String text)
    {
        super(material);
        Set_Text(text);
        remove_Lore();
    }
    public GUI_Itemstack(Material material,String text,String tag)
    {
        super(material);
        Set_Text(text);
        this.tag = tag;
        remove_Lore();
    }
    public static   ArrayList<GUI_Itemstack> find_Items(String name)
    {
        ArrayList<GUI_Itemstack> result = new ArrayList<>();
        for (GUI_Itemstack item : GUI_Itemstack.GetAllItems)
        {
         if(item.getType().name().contains(name))
         {
             result.add(item);
         }
        }


       return result;
    }
    public String tag="";


    public GUI_Itemstack Set_Text(String text)
    {
        ItemMeta Roles_itemMeta = this.getItemMeta();

        Roles_itemMeta.setDisplayName(text.replace("&","§"));
        this.setItemMeta(Roles_itemMeta);
        return this;
    }

    public GUI_Itemstack Set_Metadata(List<String> text)
    {
        ItemMeta Roles_itemMeta = this.getItemMeta();
        Roles_itemMeta.setLore(text);
        this.setItemMeta(Roles_itemMeta);
        return this;
    }


    private void remove_Lore()
    {
     // this.set_Lore(" ");
    }
    private void set_Lore( String... lore) {
        ItemMeta im = this.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.setItemMeta(im);
    }
}

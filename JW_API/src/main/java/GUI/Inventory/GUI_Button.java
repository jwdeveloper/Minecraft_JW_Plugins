package GUI.Inventory;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Events.GUI_ClickEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI_Button extends GUI_Itemstack
{
    public GUI_Acctions acction = GUI_Acctions.EMPTY;

    public Vector position = new Vector(0,0,0);

    public GUI_ClickEvent OnClick;

    public GUI_Button(Material material) {
        super(material);
    }

    public GUI_Button(Material material, String text)
    {
        super(material, text);
    }

    public GUI_Button(Material material, String text, String tag,GUI_Acctions acction) {
        super(material, text, tag);
        Hide_Atributes();
        this.acction = acction;
    }
    public GUI_Button(Material material, String text, String tag,GUI_Acctions acction,List<String> metda)
    {
        super(material, text, tag);
        Set_Metadata_(metda);
        Hide_Atributes();
        this.acction = acction;
    }
    public GUI_Button(Material material, String text, String tag,GUI_Acctions acction,String metda)
    {
        super(material, text, tag);
        List<String> strings = new ArrayList<>();
        strings.add(metda);
        Set_Metadata_(strings);
        Hide_Atributes();
        this.acction = acction;
    }
    public GUI_Button(Material material, String text,GUI_Acctions acction) {
        super(material, text);
        Hide_Atributes();
        this.acction = acction;
    }

    public void Set_acction(GUI_Acctions acctions)
    {
        this.acction = acctions;
    }

    public  void Rename(String name)
    {
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(name);
        this.setItemMeta(im);
    }


    public void Set_Highlighted(boolean value)
    {

        ItemMeta testEnchantMeta = this.getItemMeta();
        if(value)
        testEnchantMeta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
         testEnchantMeta.removeEnchant(Enchantment.ARROW_FIRE);
        this.setItemMeta(testEnchantMeta);
    }

    public void Set_Position(int width,int height)
    {
        this.position = new Vector(width,height,0);
    }


    public  void Set_Description(String desc)
    {
        List<String> strings = new ArrayList<>();
        strings.add(desc);
        Set_Metadata_(strings);
    }

    public void Hide_Atributes()
    {
        ItemMeta Roles_itemMeta = this.getItemMeta();
        Arrays.asList(ItemFlag.values()).forEach(i -> Roles_itemMeta.addItemFlags(i));
        this.setItemMeta(Roles_itemMeta);
    }
    public void Set_Metadata_(List<String> metda)
    {
        ItemMeta Roles_itemMeta = this.getItemMeta();

        for(int i=0;i<metda.size();i++)
        {
         metda.set(i,metda.get(i).replaceAll("&","§"));
        }
        Roles_itemMeta.setLore(metda);
        this.setItemMeta(Roles_itemMeta);
    }


    public GUI_Button(GUI_Itemstack item,GUI_Acctions acction)
    {

        super(item.getType(), item.getItemMeta().getDisplayName());
        this.tag = item.tag;
        this.acction = acction;
    }
}

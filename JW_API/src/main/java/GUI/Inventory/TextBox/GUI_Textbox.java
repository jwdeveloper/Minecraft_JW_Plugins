package GUI.Inventory.TextBox;

import GUI.Components.GUI_Anival;
import GUI.Inventory.GUI_Itemstack;
import GUI.Inventory.Events.GUI_TextEvent;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GUI_Textbox
{

    public String name;
    public GUI_TextEvent onText = (a,b) ->{};
    public GUI_TextEvent onExit = (a,b) ->{};
    public GUI_Inventory parent;
    private String displayed_text = "";
    private ArrayList<I_Validator> validators = new ArrayList<>();
    public GUI_Textbox(String name,GUI_Inventory parent)
    {
        this.name =name;
        this.parent = parent;
    }
    public GUI_Textbox(String name,String displayed_text,GUI_Inventory parent)
    {
        this.name =name;
        this.parent = parent;
        this.displayed_text = displayed_text;
    }
    public void add_validation(I_Validator validator)
    {
        validators.add(validator);
    }
    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_value(String value)
    {
        this.displayed_text = value;
    }
    public void Open(Player player)
    {
        GUI_Anival guiTextinput =  new GUI_Anival(player, new GUI_Anival.AnvilClickEventHandler() {

            @Override
            public void onAnvilClick(GUI_Anival.AnvilClickEvent e) {
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0],() ->
                {

                    if(e.getSlot()==GUI_Anival.AnvilSlot.OUTPUT && e.hasText()==true )
                    {

                        for(I_Validator validator:validators)
                        {
                            if(validator.validate(e.getText())==false)
                              return;
                        }

                        parent.open(player);
                        onText.Execute(e.getText(),player);

                        e.setWillClose(true);
                        e.setWillDestroy(true);
                    }
                    if(e.getSlot()==GUI_Anival.AnvilSlot.INPUT_LEFT)
                    {
                        parent.open(player);
                        onExit.Execute(" ",player);

                        e.setWillClose(true);
                        e.setWillDestroy(true);
                    }
                });
            }});




        guiTextinput.setSlot(GUI_Anival.AnvilSlot.INPUT_LEFT,new GUI_Itemstack(Material.NAME_TAG, ChatColor.BLACK+displayed_text));
        guiTextinput.setSlot(GUI_Anival.AnvilSlot.OUTPUT,new GUI_Itemstack(Material.NAME_TAG,displayed_text));
        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0],() ->
        {
            guiTextinput.open(name);
        });
    }





}

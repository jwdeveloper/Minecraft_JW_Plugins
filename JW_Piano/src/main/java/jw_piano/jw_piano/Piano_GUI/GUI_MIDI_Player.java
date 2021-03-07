package jw_piano.jw_piano.Piano_GUI;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Forms.Basic.GUI_Details;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;

import org.bukkit.Material;

public class GUI_MIDI_Player extends GUI_Details<String>
{

    public String file_path;

    public GUI_MIDI_Player(GUI_Inventory parent)
    {
        super(parent, "Piano player");


        this.onOpen.add((a,b,c)->
        {
            this.addItem(2,2,new GUI_Button(Material.MUSIC_DISC_FAR,"§6Select MIDI file","MIDI_Select", GUI_Acctions.CLICK));

            this.addItem(3,2,new GUI_Button(Material.GREEN_WOOL,"§Play","Play", GUI_Acctions.CLICK));
            this.addItem(3,5,new GUI_Button(Material.YELLOW_WOOL,"§Pause","Pause", GUI_Acctions.CLICK));
            this.addItem(3,7,new GUI_Button(Material.RED_WOOL,"§Stop","Stop", GUI_Acctions.CLICK));
        });

        this.onClickItem.add((a,b,c)->
        {
            if (a.tag.equalsIgnoreCase("MIDI_Select"))
            {

                return;
            }
            if (a.tag.equalsIgnoreCase("Play"))
            {
              //  Main.instance.minecraftPiano.Play_File(file_path);
                return;
            }
            if (a.tag.equalsIgnoreCase("Pause"))
            {

                return;
            }
            if (a.tag.equalsIgnoreCase("Stop"))
            {

                return;
            }


        });
    }
}

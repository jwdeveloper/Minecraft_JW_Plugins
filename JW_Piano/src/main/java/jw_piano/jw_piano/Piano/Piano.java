package jw_piano.jw_piano.Piano;

import MIDI.MIDI_Helper;
import MIDI.MIDI_Piano;
import MIDI.MIDI_Player;
import MIDI.MIDI_Reciever;
import jw_piano.jw_piano.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;


public class Piano implements Listener
{
    Location location;

    Piano_Pedal pedal_sustain;
    Piano_Pedal pedal_sustaino;
    Piano_Pedal pedal_soft;
    boolean Is_spawned;
    boolean light_enabled;

    MIDI_Player midi_player;
    MIDI_Reciever midi_reciever;
    MIDI_Piano midi_piano;


    ArrayList<Piano_Key> piano_keys = new ArrayList<>();


    @EventHandler
    public void OnKeyPressed(BlockPlaceEvent e)
    {

    }
    @EventHandler
    public void OnSerwerClose(PluginDisableEvent e)
    {
        DestoryPiano();
    }

    public Piano()
    {
        Bukkit.getPluginManager().registerEvents(this, Main.Instance);
        midi_reciever = MIDI_Helper.Get_Test_Reciever();
        midi_player = new MIDI_Player(midi_reciever);
        Load_Events();
    }

    public void Load_Events()
    {
        midi_reciever.OnPedalOn = (a,b,c)->
        {
            switch (a)
            {
                case 64:
                    pedal_sustain.Press(a,b,c);
                    break;
                case 65:
                    pedal_sustaino.Press(a,b,c);
                    break;
                case 67:
                    pedal_soft.Press(a,b,c);
                    break;
            }

        };
        midi_reciever.OnPedalOff = (a,b,c)->
        {

            switch (a)
            {
                case 64:
                    pedal_sustain.Relese(a,b,c);
                    break;
                case 65:
                    pedal_sustaino.Relese(a,b,c);
                    break;
                case 67:
                    pedal_soft.Relese(a,b,c);
                    break;
            }

        };

        midi_reciever.OnNoteOn = (a,b,c)->
        {
            if(Is_spawned)
                piano_keys.get(a-21).Press(a,b,c);
        };
        midi_reciever.OnNoteOff = (a,b,c)->
        {
            if(Is_spawned)
                piano_keys.get(a-21).Relese(a,b,c);
        };
    }


    public void SpawnPiano(Location location)
    {

        if(Is_spawned)
            return;


        this.location = location;
        pedal_sustain = new Piano_Pedal(location.clone().add(0,5,0));
        pedal_sustaino = new Piano_Pedal(location.clone().add(0,5,0));
        pedal_soft = new Piano_Pedal(location.clone().add(0,5,0));

        int key=1;
        int octave =0;
        for(int i=1;i<=88;i++)
        {
            if(i>3 && i<88)
            {
                key = (i-4)%12;
                octave = 1+(i-4)/12;
            }
            if(i<=3)
            {
                key = i + 8;
            }
            piano_keys.add(new Piano_Key(new Location(location.getWorld(),location.getX(),location.getY(),location.getZ()+i),key,octave));
        }
        Is_spawned = true;
    }



    public void DestoryPiano()
    {
        if(midi_piano!=null)
        midi_piano.Close();

        if(midi_player!=null )
        midi_player.Stop();

        if(midi_reciever!=null)
        midi_reciever.close();

        if(Is_spawned)
        {
          for(Piano_Key pk : piano_keys)
          {
             pk.SetMaterial(Material.AIR);
          }

          pedal_soft.SetMaterial(Material.AIR);
          pedal_sustain.SetMaterial(Material.AIR);
          pedal_sustaino.SetMaterial(Material.AIR);
        }
        piano_keys.clear();
        Is_spawned = false;
    }

    public void Connect_Keyboard(String name)
    {
        midi_piano = new MIDI_Piano("Roland",midi_reciever);
        midi_piano.Open();
    }


    public void Enable_Lights(boolean light)
    {
        light_enabled= light;

        for(int i=0;i<piano_keys.size();i++)
        {

                piano_keys.get(i).Set_light(light);
        }

    }

    public void Stop_Playing()
    {
        if( midi_player.Is_Playing())
        {
            midi_player.Stop();
        }
    }


    public boolean Play_File(String path)
    {
        if(Is_spawned == false)
            return  false;

        //Stop_Playing();
        midi_player.loadFile(path);
        midi_player.Start();
     return true;
    }




}
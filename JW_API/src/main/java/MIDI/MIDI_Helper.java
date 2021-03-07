package MIDI;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import java.util.ArrayList;

public class MIDI_Helper
{


    public static ArrayList<String> Get_MIDI_Devices()
    {
        ArrayList<String> result = new ArrayList<>();

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++)
        {
             result.add(infos[i].getName());
        }
        return  result;
    }


    public static MIDI_Reciever Get_Test_Reciever()
    {
        MIDI_Reciever midi_reciever = new MIDI_Reciever("Test_Reciever");


        midi_reciever.OnPedalOn = (note,velocity,track)->
        {
            System.out.println("PedalOn: "+note+"/"+velocity+" - "+track);
        };
        midi_reciever.OnPedalOff = (note,velocity,track)->
        {
            System.out.println("PedalOFF: "+note+"/"+velocity+" - "+track);
        };


        midi_reciever.OnNoteOn = (note,velocity,track)->
        {
            System.out.println("NoteON: "+note+"/"+velocity+" - "+track);
        };
        midi_reciever.OnNoteOff = (note,velocity,track)->
        {
            System.out.println("NoteOFF: "+note+"/"+velocity+" - "+track);
        };

        return  midi_reciever;
    }


}

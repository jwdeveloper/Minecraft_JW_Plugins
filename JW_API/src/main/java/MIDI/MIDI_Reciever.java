package MIDI;



import javax.sound.midi.*;

public class MIDI_Reciever implements Receiver
{

    public MIDI_Event OnNoteOn;
    public MIDI_Event OnNoteOff;
    public MIDI_Event OnPedalOn;
    public MIDI_Event OnPedalOff;
    public boolean is_pressed =false;

    public String name;
    public MIDI_Reciever(String name) {
        this.name = name;
    }
    @Override
    public void send(MidiMessage midiMessage, long l)
    {
        if (midiMessage instanceof ShortMessage)
        {
            ShortMessage sm = (ShortMessage) midiMessage;
            switch (sm.getCommand())
            {
                case ShortMessage.NOTE_OFF:
                    OnNoteOff.on_event(sm.getData1(),sm.getData2(),sm.getChannel());
                    break;
                case ShortMessage.NOTE_ON:
                    if(sm.getData2() ==0)
                     OnNoteOff.on_event(sm.getData1(),sm.getData2(),sm.getChannel());
                    else
                    OnNoteOn.on_event(sm.getData1(),sm.getData2(),sm.getChannel());
                    break;
                case ShortMessage.CONTROL_CHANGE:
                case ShortMessage.POLY_PRESSURE:
                    if(sm.getData2() ==0)
                    {
                        is_pressed =false;
                        OnPedalOff.on_event(sm.getData1(),sm.getData2(),sm.getChannel());
                    }
                    else if(is_pressed == false && sm.getData2() != 0)
                    {
                        is_pressed =true;
                        OnPedalOn.on_event(sm.getData1(),sm.getData2(),sm.getChannel());
                    }
                    break;
            }

            //  System.out.println("ShortMessage: "+sm.getStatus()+" "+sm.getCommand()+" "+sm.getChannel()+" "+sm.getData1()+" "+sm.getData2()+" "+l);
            return;
        }
        if (midiMessage instanceof MetaMessage)
        {
            MetaMessage sm = (MetaMessage) midiMessage;

            System.out.println("MetaMessage: "+sm.getStatus());
            return;
        }
        if (midiMessage instanceof SysexMessage)
        {
            SysexMessage sm = (SysexMessage) midiMessage;

            System.out.println("Systex: "+sm.getStatus());
            return;
        }


        //   System.out.println("Transmiter works "+midiMessage.getStatus()+" " + midiMessage.getMessage()[0]+" " +midiMessage.getMessage()[1]+l);
    }

    @Override
    public void close() {

    }


}

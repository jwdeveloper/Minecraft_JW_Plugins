package MIDI;

import javax.sound.midi.*;
import java.util.function.Consumer;

public class MIDI_Piano
{


       private Synthesizer synthesizer;
       private  MidiChannel channel;
       private MidiDevice midiDevice;
       private MIDI_Reciever reciever;
       private MIDI_Reciever sound_reciever;
       private String name;
       public Consumer<String> OnError;


        public MIDI_Piano(String name,MIDI_Reciever reciever)
        {
            this.reciever = reciever;
            this.name = name;
        }

        public void Set_Sound(int i)
        {
         channel.programChange(i);
        }
        public void Play_sound(int node,int velocity)
        {
         channel.noteOn(node, velocity+30);
        }

        public Instrument[] Get_Instruments()
        {
          return  synthesizer.getAvailableInstruments();
        }
        private void Load_Piano()
        {

                for(MidiDevice.Info i :MidiSystem.getMidiDeviceInfo())
                {
                    if(i.getName().contains(name)  && !i.getDescription().contains("MIDI Port") )
                    {
                        try {
                            midiDevice = MidiSystem.getMidiDevice(i);
                            midiDevice.getTransmitter().setReceiver(reciever);
                           sound_reciever = new MIDI_Reciever("Dsd");
                          //  midiDevice.getTransmitter().setReceiver(sound_reciever);
                            }
                          catch (MidiUnavailableException e)
                            {
                                OnError.accept(e.getMessage());
                            }
                    }
                }
            }



            int i=1;
        public void Open()
        {
            try
            {
                Load_Piano();
                 synthesizer = MidiSystem.getSynthesizer();
                 channel = synthesizer.getChannels()[0];
                 channel.setMono(true);
                 midiDevice.open();
                 synthesizer.open();

                sound_reciever.OnNoteOn = (a,b,c)->
                {
                    channel.noteOn(a, b+30);
                };

                sound_reciever.OnNoteOff = (a,b,c)->
                {
                    if(reciever.is_pressed ==false)
                    channel.noteOff(a, b+30);
                };

                sound_reciever.OnPedalOff = (a,b,c)->
                {
                    channel.allNotesOff();
                };


            }
            catch (MidiUnavailableException ex)
            {
                OnError.accept(ex.getMessage());
            }

        }

        public void Close()
        {
            sound_reciever.close();
            if(midiDevice.isOpen())
            this.midiDevice.close();
            if(synthesizer.isOpen())
            synthesizer.close();

        }





}

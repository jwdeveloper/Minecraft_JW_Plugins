package MIDI;




import javax.sound.midi.*;
import java.util.List;

public class MIDI_TEST
{


    private static List<Receiver> receivers;


    public static void TestPiano()
    {
        MIDI_Piano midi_piano = new MIDI_Piano("Roland", MIDI_Helper.Get_Test_Reciever());
        midi_piano.OnError = (e) ->
        {
            System.out.println(e);
        };
        midi_piano.Open();

        int i=0;
        while (true)
        {
            try
            {
                midi_piano.Set_Sound(i);
                midi_piano.Play_sound(44,100);
                Thread.sleep(1000);
                i++;
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }


    }

    public static void TestPlayer()
    {
        MIDI_Player midi_player = new MIDI_Player(MIDI_Helper.Get_Test_Reciever());
        midi_player.OnError = (m)->
        {
            System.out.println(m);
        };


        midi_player.Start();
    }



    public  static void Test2()
    {
        int[] notes = new int[]{60, 62, 64, 65, 67, 69, 71, 72, 72, 71, 69, 67, 65, 64, 62, 60};
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel channel = synthesizer.getChannels()[0];

            for (int note : notes) {
                channel.noteOn(note, 50);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                } finally {
                    channel.noteOff(note);
                }
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }






    public static void  Test()
    {
        try
        {

        MidiDevice.Info[] devices;
	/*
	MidiDevice.Info[] info = p.getDeviceInfo();
	for (int m = 0; m < info.length; m++) {
	    System.out.println(info[m].toString());
	}
	*/

            System.out.print("MIDI devices:");
        devices = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info: devices) {
            System.out.print("    Name: " + info.toString() +
                    ", Decription: " +
                    info.getDescription() +
                    ", Vendor: " +
                    info.getVendor());
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (! device.isOpen()) {
                device.open();
            }
            if (device instanceof Sequencer) {
                System.out.print("        Device is a sequencer");
            }
            if (device instanceof Synthesizer) {
                System.out.print("        Device is a synthesizer");
            }
            System.out.print("        Open receivers:");
             receivers = device.getReceivers();

               /* Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        for (Receiver r: receivers)
                        {
                            Bukkit.getConsoleSender().sendMessage("            " + r.toString());
                        }
                    }
                }, 0L, 20L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
*/


            try {
                System.out.print("\n        Default receiver: " +
                        device.getReceiver().toString());

                System.out.print("\n        Open receivers now:");
                receivers = device.getReceivers();
                for (Receiver r: receivers) {
                    System.out.print("            " + r.toString());
                }
            } catch(MidiUnavailableException e) {
                System.out.print("        No default receiver");
            }

            System.out.print("\n        Open transmitters:");
            List<Transmitter> transmitters = device.getTransmitters();
            for (Transmitter t: transmitters) {
                System.out.print("            " + t.toString());
            }
            try {
                System.out.print("\n        Default transmitter: " +
                        device.getTransmitter().toString());

                System.out.print("\n        Open transmitters now:");
                transmitters = device.getTransmitters();
                for (Transmitter t: transmitters) {
                    System.out.print("            " + t.toString());
                }
            } catch(MidiUnavailableException e) {
                System.out.print("        No default transmitter");
            }
            device.close();
        }


        Sequencer sequencer = MidiSystem.getSequencer();
            System.out.print("Default system sequencer is " +
                sequencer.getDeviceInfo().toString() +
                " (" + sequencer.getClass() + ")");

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
            System.out.print("Default system synthesizer is " +
                synthesizer.getDeviceInfo().toString() +
                " (" + synthesizer.getClass() + ")");

        }
        catch (MidiUnavailableException e) {
        }

    }
}

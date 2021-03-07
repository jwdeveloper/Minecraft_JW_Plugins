package MIDI;

public class MIDI_JACK
{

    static
    {
        System.loadLibrary("MidiJackPlugin");
        System.loadLibrary("winmm.dll");
    }


    public MIDI_JACK()
    {


    }
    public native void accessZ(char a_uint, String b_string, char[] c_vector);

   // [DllImport("MidiJackPlugin", EntryPoint = "MidiJackDequeueIncomingData")]
    public native long DequeueIncomingData();
       // [DllImport("winmm.dll")]
    public native int midiInGetNumDevs();


      //  [DllImport("winmm.dll")]
    public native int midiInGetDevCaps(int handle,  MidiInCaps caps, int sizeOfmidiInCaps);


    public static final MIDI_JACK getInstance() {
        return INSTANCE;
    }

    private static final MIDI_JACK INSTANCE = new MIDI_JACK();


    public class MidiInCaps
    {
        /// <summary>
        /// Manufacturer identifier of the device driver for the Midi output
        /// device.
        /// </summary>
        public short mid;

        /// <summary>
        /// Product identifier of the Midi output device.
        /// </summary>
        public short pid;

        /// <summary>
        /// Version number of the device driver for the Midi output device. The
        /// high-order byte is the major version number, and the low-order byte
        /// is the minor version number.
        /// </summary>
        public int driverVersion;

        /// <summary>
        /// Product name.
        /// </summary>
       // [MarshalAs(UnmanagedType.ByValArray, SizeConst = 32)]
        public byte[] name;
        /// <summary>
        /// Optional functionality supported by the device.
        /// </summary>
        public int support;
    }

}

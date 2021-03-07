// JPortMidiException -- thrown by JPortMidi methods

package MIDI.MIDI_PORT.jportmidi;

public class JPortMidiException extends Exception {
    public int error = 0;
    public JPortMidiException(int err, String msg) {
        super(msg);
        error = err;
    }
}


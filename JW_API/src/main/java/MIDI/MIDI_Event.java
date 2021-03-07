package MIDI;

public interface MIDI_Event
{

    public void on_event(int note, int velocity, int channel);
}

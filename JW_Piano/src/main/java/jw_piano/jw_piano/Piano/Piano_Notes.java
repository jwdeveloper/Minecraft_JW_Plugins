package jw_piano.jw_piano.Piano;

import org.bukkit.Note;

public class Piano_Notes
{

    private static Note.Tone[] notes;



    public static Note.Tone get_note(int id)
    {
        if(notes==null)
        {
           notes = new Note.Tone[12];
            notes[0] = Note.Tone.C;
            notes[1] = Note.Tone.C;
            notes[2] = Note.Tone.D;
            notes[3] = Note.Tone.D;
            notes[4] = Note.Tone.E;
            notes[5] = Note.Tone.F;
            notes[6] = Note.Tone.F;
            notes[7] = Note.Tone.G;
            notes[8] = Note.Tone.G;
            notes[9] = Note.Tone.A;
            notes[10] = Note.Tone.A;
            notes[11] = Note.Tone.B;
        }

        return notes[id%notes.length];
    }

    public static boolean is_flat(int id)
    {
        switch (id)
        {
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return false;
            case 4:
                return true;
            case 5:
                return true;
            case 6:
                return false;
            case 7:
                return true;
            case 8:
                return false;
            case 9:
                return true;
            case 10:
                return false;
            case 11:
                return true;
        }
        return true;
    }



}

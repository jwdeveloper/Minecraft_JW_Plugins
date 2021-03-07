package MIDI;

public class Piano_Key
{
    int number;
    String name;
    boolean pressed = false;
    Object model;

    Piano_Key(Object model, int number, String name)
    {
        this.model = model;
        this.number = number;
        this.name = name;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}

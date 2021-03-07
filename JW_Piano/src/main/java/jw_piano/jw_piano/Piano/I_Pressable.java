package jw_piano.jw_piano.Piano;

import org.bukkit.Material;

public interface I_Pressable
{
    public Material MaterialRelese();
    public Material MaterialPress();
    public boolean Is_Pressed();
    public void Press(int id,int velocity,int channel);
    public void Relese(int id,int velocity,int channel);
    public void SetMaterial(Material material);
}

package jw_piano.jw_piano.Data;

import Managers.File_Manager;
import jw_piano.jw_piano.Main;
import org.bukkit.util.Vector;

public class Piano_settings
{
    public static Piano_settings instnace;
    public Vector location = new Vector(0,0,0);
    public String world_location = new String();
    public boolean is_placed = false;

    public static boolean Load()
    {
        try
        {
            instnace= File_Manager.Load(Main.GetPath(),"data.json",Piano_settings.class);

            if(instnace == null)
                instnace = new Piano_settings();

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public static boolean Save()
    {
        try
        {


           File_Manager.Save(instnace,Main.GetPath(),"data.json");
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


}

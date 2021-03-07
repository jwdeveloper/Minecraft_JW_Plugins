package jw_piano.jw_piano;

import Commands.OLD.Command_Manager_OLD;
import Commands.Test.Command_Manager;
import Commands.Test.Test_CommandV2;
import Managers.File_Manager;
import jw_piano.jw_piano.Commands.Main_Command;
import jw_piano.jw_piano.Data.Piano_settings;
import jw_piano.jw_piano.Piano.Piano;
import jw_piano.jw_piano.Piano.Piano_Key;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;

public final class Main extends JavaPlugin {


    public static Main Instance;

    public static String GetPath()
    {
        return File_Manager.PluginPath("JW_Piano");
    }

    private Command_Manager command_manager;

    public Piano piano;

    @Override
    public void onEnable()
    {
        Piano_settings.Load();
        Instance  = this;
        command_manager= new Command_Manager(this);
        command_manager.Add_Command(new Main_Command());
        piano = new Piano();

        if(Piano_settings.instnace.is_placed)
        {
            try
            {
                World world = Bukkit.getWorld(Piano_settings.instnace.world_location);
                Vector loc = Piano_settings.instnace.location;
                piano.SpawnPiano(new Location(world,loc.getX(),loc.getY(),loc.getZ()));
            }
            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void onDisable()
    {
        Piano_settings.Save();
    }
}

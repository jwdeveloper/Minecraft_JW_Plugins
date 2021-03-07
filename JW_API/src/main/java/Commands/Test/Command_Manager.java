package Commands.Test;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Command_Manager
{


    private JavaPlugin plugin;
    public Command_Manager(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }


    private ArrayList<JW_Command> commands = new ArrayList<>();


    public void Add_Command(JW_Command command)
    {
        Register_Commands(command);
        commands.add(command);
    }



    public void Register_Commands(JW_Command jw_command)
    {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(jw_command.getName(), jw_command);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

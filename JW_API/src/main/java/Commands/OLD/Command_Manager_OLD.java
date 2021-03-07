package Commands.OLD;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command_Manager_OLD implements CommandExecutor  , TabCompleter
{
    private JavaPlugin plugin;
    public Command_Manager_OLD(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }


    private ArrayList<JW_Command_OLD> sub_commands = new ArrayList<>();


    public void Add_Command(JW_Command_OLD command)
    {
        plugin.getCommand(command.Get_Command()).setExecutor(this);
        plugin.getCommand(command.Get_Command()).setTabCompleter(this);
        sub_commands.add(command);
    }



    @Override
    public boolean onCommand(CommandSender commandSender,  Command command,  String s, String[] args)
    {



        for(int i=0;i<sub_commands.size();i++)
        {
            if(command.getName().equalsIgnoreCase(sub_commands.get(i).Get_Command()));
            {
                JW_Command_OLD target = sub_commands.get(i);
                String[] arguments = args;

                if(args.length!=0)
                {
                    //null nie jest najlepszym rozwiazaniem, ale komu by sie chcialo robic opcjonala
                    //magia połaczona z rekurencją
                    JW_Command_OLD child_invoked = target.Is_Child_invoked(args);
                    if(child_invoked!=null)
                    {
                        target = child_invoked;


                        for(int j=0;j<args.length;j++)
                        {
                            if(args[j].equalsIgnoreCase(target.Get_Command()))
                            {
                                arguments =  Arrays.copyOfRange(args, j+1, args.length);
                                break;
                            }
                        }
                    }

                }
                if(commandSender instanceof Player)
                {
                    if(target.Player_Can_User((Player)commandSender))
                    {
                        target.Invoke((Player)commandSender,arguments);
                    }
                }
                else
                {
                  target.Invoke((ConsoleCommandSender)commandSender,arguments);
                }
                break;
            }
        }


        return false;
    }

    @Override
    public  List<String> onTabComplete( CommandSender commandSender,  Command command, String s, String[] strings)
    {
        for(int i=0;i<sub_commands.size();i++)
        {
            if (command.getName().equalsIgnoreCase(sub_commands.get(i).Get_Command())) ;
            {
                JW_Command_OLD target = sub_commands.get(i);
                if(strings.length!=0)
                {
                    JW_Command_OLD child_invoked = target.Is_Child_invoked(strings);
                    if(child_invoked!=null)
                        target = child_invoked;
                }
                return target.Get_TabCompleter(strings.length);
            }
        }
        return null;
    }





}

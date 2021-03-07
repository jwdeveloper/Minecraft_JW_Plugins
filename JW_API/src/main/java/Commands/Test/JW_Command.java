package Commands.Test;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class JW_Command extends BukkitCommand
{
    public JW_Command Parent = null;
    public ArrayList<String> Premissions = new ArrayList<>();
    public ArrayList<JW_Command> Child_commands = new ArrayList<>();
    public HashMap<Integer, Supplier<ArrayList<String>>> tabCompletes = new HashMap<>();
    private ArrayList<String> empty_tabCompletes = new ArrayList<>();

    public abstract void Invoke(Player player_sender, String[] args);
    public abstract void Invoke(ConsoleCommandSender server_sender, String[] args);
    public abstract void OnInitialize();
    public Consumer<CommandSender> OnNoArguments;

    public JW_Command(String name)
    {
        super(name);
        OnInitialize();
    }

    @Override
    public List<String> tabComplete( CommandSender sender,String alias,   String[] args) throws IllegalArgumentException
    {
        JW_Command target =this;
        String[] arguments = args;
        if(args.length!=0)
        {
            JW_Command child_invoked = target.Is_Child_invoked(args);
            if(child_invoked!=null)
            {
                target = child_invoked;
                for(int j=0;j<args.length;j++)
                {
                    if(args[j].equalsIgnoreCase(target.getName()))
                    {
                        arguments =  Arrays.copyOfRange(args, j+1, args.length);
                        break;
                    }
                }
            }

        }
        return target.Get_TabCompleter(arguments.length);
    }
    @Override
    public boolean execute(CommandSender commandSender,  String s,   String[] args)
    {
        JW_Command target = this;
        String[] arguments = args;

        if(args.length!=0)
        {
            //null nie jest najlepszym rozwiazaniem, ale komu by sie chcialo robic opcjonala
            //magia połaczona z rekurencją
            JW_Command child_invoked = target.Is_Child_invoked(args);
            if(child_invoked!=null)
            {
                target = child_invoked;

                for(int j=0;j<args.length;j++)
                {
                    if(args[j].equalsIgnoreCase(target.getName()))
                    {
                        arguments =  Arrays.copyOfRange(args, j+1, args.length);
                        break;
                    }
                }
            }

        }
        if(target.OnNoArguments!=null && arguments.length==0)
        {
            target.OnNoArguments.accept(commandSender);
            return true;
        }

        if(commandSender instanceof Player)
        {

            target.Invoke((Player)commandSender,arguments);
        }
        else
        {
            target.Invoke((ConsoleCommandSender)commandSender,arguments);
        }
        return true;
    }



    public void Permission_Error(Player p,String permission)
    {
      p.sendMessage(ChatColor.RED+" You dont have permission: "+permission);
    }

    //rekurencja bejbe
    public JW_Command Is_Child_invoked(String[] args)
    {
        JW_Command result = null;
        for (JW_Command c: Child_commands)
        {
            //szukanie komendy wsrod dzieci
            if(args.length >1)
            {
                String[] part = Arrays.copyOfRange(args, 1, args.length);

                result = c.Is_Child_invoked(part);

                if(result!=null)
                {
                    break;
                }
            }
            // jesli nie znaleziono udzieci to moze rodzic jest wlasicielm komendy
                if(c.getName().equalsIgnoreCase(args[0]))
                {
                    result  = c;
                    break;
                }
        }
        return result;
    }

    public boolean Player_Can_User(Player player)
    {
     for(String p : this.Premissions)
     {
         if(player.hasPermission(p)==false)
         {
             Permission_Error(player,p);
          return  false;
         }
     }
      return true;
    }

    public ArrayList<String> Get_TabCompleter(int argument)
    {
         return tabCompletes.getOrDefault(argument,() ->{ return empty_tabCompletes;}).get();
    }

    public void Add_TabCompleter(int argument, String... acction)
    {
        ArrayList<String> complieters = new ArrayList<>();

        for(int i=0;i<acction.length;i++)
        {
            complieters.add(acction[i]);
        }

        tabCompletes.putIfAbsent(argument,() -> {return complieters;});
    }


    public void Add_ChildNamesTabCompleter()
    {
        tabCompletes.putIfAbsent(1,() ->
        {
            ArrayList<String> names = new ArrayList<>();

            this.Child_commands.forEach(c -> { names.add(c.getName());});

            return names;
        });
    }


    public void Set_ErrorIfNoArgs(Consumer<CommandSender> acction)
    {
        OnNoArguments = acction;
    }
    public void Add_TabCompleter(int argument, Supplier<ArrayList<String>> acction)
    {
        tabCompletes.putIfAbsent(argument,acction);
    }
    public void Add_Child(JW_Command child)
    {
        Child_commands.add(child);
    }
    public void Remove_Child(JW_Command child)
    {
        Child_commands.remove(child);
    }


}

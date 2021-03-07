package Commands.OLD;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Supplier;

public abstract class JW_Command_OLD
{

    public JW_Command_OLD()
    {

        Set_Childs();
        Set_Permission();
        Set_TabCompleter();

    }



    public JW_Command_OLD Parent = null;
    public ArrayList<String> Premissions = new ArrayList<>();
    public ArrayList<JW_Command_OLD> Child_commands = new ArrayList<>();

    public HashMap<Integer, Supplier<ArrayList<String>>> command_hints = new HashMap<>();


    public abstract String Get_Command();
    public abstract void Invoke(Player player_sender, String[] args);
    public abstract void Invoke(ConsoleCommandSender server_sender, String[] args);

    //klasy dziedziczace bede to napisywac
    public void Set_Childs()
    {

    }
   //to tez
    public void Set_Permission()
    {

    }

    public void Set_TabCompleter()
    {

    }
    // a to zalerzy 0_0
    public  String Get_Description()
    {
        return "Description: ";
    }

    public String Get_Usage()
    {
        return "Usage: /"+this.Get_Command();
    }

    public void Permission_Error(Player p,String permission)
    {
      p.sendMessage(ChatColor.RED+" You dont have permission: "+permission);
    }

    //rekurencja bejbe
    public JW_Command_OLD Is_Child_invoked(String[] args)
    {
        JW_Command_OLD result = null;
        for (JW_Command_OLD c: Child_commands)
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
                if(c.Get_Command().equalsIgnoreCase(args[0]))
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


    public ArrayList<String> empty_TabCompleter = new ArrayList<>();

    public ArrayList<String> Get_TabCompleter(int argument)
    {
         return command_hints.getOrDefault(argument,() ->{ return empty_TabCompleter;}).get();
    }



    public void Add_TabCompleter(int argument, Supplier<ArrayList<String>> acction)
    {
        command_hints.putIfAbsent(argument,acction);
    }
    public void Add_Premission(String premission)
    {
        Premissions.add(premission);
    }
    public void Remove_Premission(String premission)
    {
        Premissions.remove(premission);
    }
    public void Add_Child(JW_Command_OLD child)
    {
        Child_commands.add(child);
    }
    public void Remove_Child(JW_Command_OLD child)
    {
        Child_commands.remove(child);
    }


}

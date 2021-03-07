package Commands.OLD;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Test_Command_OLD extends JW_Command_OLD
{

    @Override
    public String Get_Description() {
        return "very usefull command that makes your life better";
    }

    @Override
    public String Get_Usage() {
        return "use /jump to make a jump";
    }

    @Override
    public String Get_Command() {
        return "jump";
    }



    @Override
    public void Invoke(Player player_sender, String[] args)
    {
        player_sender.sendMessage("Jump normal");
        player_sender.setVelocity(new Vector(0,10,0));
    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {
        Bukkit.getConsoleSender().sendMessage("Hello my server");
    }

    @Override
    public void Set_Childs() {
        this.Add_Child(new Test2_Command_OLD());
        this.Add_Child(new Dynamic_Command_OLD("test",(a) ->{ Bukkit.getConsoleSender().sendMessage("Sub command 1"); }));
        this.Add_Child(new Dynamic_Command_OLD("test2",(a) ->{ Bukkit.getConsoleSender().sendMessage("Sub command 2"); }));
        this.Add_Child(new Dynamic_Command_OLD("test3",(a) ->{ Bukkit.getConsoleSender().sendMessage("Sub command 3"); }));

    }

    @Override
    public void Set_Permission()
    {
        this.Add_Premission("Pawel_Jumper");
        this.Add_Premission("Player_Jumper");
    }

    @Override
    public void Set_TabCompleter()
    {
        this.Add_TabCompleter(1, () ->
        {
         ArrayList<String> player = new ArrayList<>();
         player.add("Marka");
         player.add("Adam");
         return player;
        });


        this.Add_TabCompleter(2, () ->
        {
            ArrayList<String> player = new ArrayList<>();
            player.add("woood");
            player.add("pickaxe");
            return player;
        });
    }
}

package Commands.OLD;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Test2_Command_OLD extends JW_Command_OLD
{

    @Override
    public String Get_Command()
    {
        return "upper";
    }

    @Override
    public void Invoke(Player player_sender, String[] args) {
        player_sender.sendMessage("Jump upper");
        player_sender.setVelocity(new Vector(0,30,0));
    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {
        Bukkit.getConsoleSender().sendMessage("Hello sub sommand: args");
        for(String s : args)
        {
            Bukkit.getConsoleSender().sendMessage(s);
        }


    }

    @Override
    public void Set_Permission()
    {
        this.Add_Premission("Uppper_Jumper");
    }


    @Override
    public void Set_Childs()
    {
        this.Add_Child(new Dynamic_Command_OLD("test",(a) ->{ Bukkit.getConsoleSender().sendMessage("Sub upper command 1"); }));
    }
}

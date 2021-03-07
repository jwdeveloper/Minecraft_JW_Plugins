package Commands.OLD;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class Dynamic_Command_OLD extends JW_Command_OLD
{


    public Dynamic_Command_OLD(String command, Consumer<Player> acction)
    {
        this.Set_Command(command);
        this.Set_Acction(acction);
    }


    private Consumer acction;
    private String command;

    public void Set_Command(String command)
    {
        this.command = command;
    }
    public void Set_Acction(Consumer<Player> acction)
    {
        this.acction = acction;
    }

    @Override
    public String Get_Command()
    {
        return command;
    }

    @Override
    public void Invoke(Player player_sender, String[] args)
    {
        if(acction!=null)
          acction.accept(null);
    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {
        if(acction!=null)
          acction.accept(null);
    }


}

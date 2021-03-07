package Commands.Test;

import Commands.OLD.JW_Command_OLD;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Dynamic_Command extends JW_Command
{


    public Dynamic_Command(String command, Consumer<Player> acction)
    {
        super(command);
        this.Add_Acction(acction);
    }


    private ArrayList<Consumer> acctions = new ArrayList<>();
    public void Add_Acction(Consumer<Player> acction)
    {
        acctions.add(acction);
    }

    @Override
    public void Invoke(Player player_sender, String[] args)
    {
        for (Consumer a: acctions)
        {
            a.accept(player_sender);
        }

    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {
        for (Consumer a: acctions)
        {
            a.accept(null);
        }
    }

    @Override
    public void OnInitialize()
    {

    }


}

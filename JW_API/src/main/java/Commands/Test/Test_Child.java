package Commands.Test;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Test_Child extends JW_Command
{

    public Test_Child()
    {
        super("child");
    }

    @Override
    public void Invoke(Player player_sender, String[] args)
    {
         player_sender.sendMessage("Hello world"+" "+args.length);
    }
    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {
        Bukkit.getConsoleSender().sendMessage("Hello console "+ args.length);
    }

    @Override
    public void OnInitialize()
    {
      this.Add_TabCompleter(1,"hello","siema","cotam?");

      this.Add_Child(new Dynamic_Command("sub",(p) -> {Bukkit.getConsoleSender().sendMessage("siema sub test");}));
    }
}

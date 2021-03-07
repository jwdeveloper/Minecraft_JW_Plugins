package Commands.Test;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Test_CommandV2 extends JW_Command {


    public Test_CommandV2() {
        super("TestV2");
    }

    @Override
    public void Invoke(Player player_sender, String[] args) {

    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args) {
        Bukkit.getConsoleSender().sendMessage("ITWorks");
    }

    @Override
    public void OnInitialize()
    {
         this.Add_TabCompleter(1, () -> { return Test_Complite(); });
         this.Add_TabCompleter(2,"joł","elo","ziomek");
         this.Add_TabCompleter(3, () -> { return Test_Complite(); });
         this.Add_TabCompleter(4,"Siema","Test","Siema2");


         this.Add_Child(new Test_Child());
         this.Add_Child(new Dynamic_Command("cmd",(p) -> {Bukkit.getConsoleSender().sendMessage("siema eniu");}));

         this.setDescription("OPIS musi byc");
         this.setLabel("Ustawiam lable");
         this.setUsage("Musisz uzywac w ten sposob");
         this.setPermission("test");
         this.setPermissionMessage("hello worlfds");
    }


    public ArrayList<String> Test_Complite()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("Test");
        list.add("Test2");


        return  list;
    }

}

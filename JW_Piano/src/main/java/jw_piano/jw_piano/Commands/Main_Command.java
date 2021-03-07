package jw_piano.jw_piano.Commands;

import Commands.OLD.JW_Command_OLD;
import Commands.Test.Dynamic_Command;
import Commands.Test.JW_Command;
import GUI.Inventory.GUI_Inventory;
import jw_piano.jw_piano.Data.Piano_settings;
import jw_piano.jw_piano.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Main_Command extends JW_Command
{

    HashMap<UUID, GUI_Inventory> players_gui = new HashMap<>();

    public Main_Command() {
        super("piano");
    }


    @Override
    public void Invoke(Player player_sender, String[] args) {

    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args) {

    }

    @Override
    public void OnInitialize()
    {
          this.Add_Child(new Spawn_Command());
          this.Add_Child(new Play_Command());


          this.Add_Child(new Dynamic_Command("destory",(p)->
          {
              Piano_settings.instnace.is_placed = false;
              p.sendMessage("Destoryed");
              Main.Instance.piano.DestoryPiano();
          }));

          this.Add_Child(new Dynamic_Command("stop",(p)->{
              Main.Instance.piano.Stop_Playing();
              p.sendMessage("Stoped :)");
          }));

        this.Add_Child(new Dynamic_Command("light_on",(p)->{
            Main.Instance.piano.Enable_Lights(true);
            p.sendMessage("light on :)");
        }));

        this.Add_Child(new Dynamic_Command("light_off",(p)->{
            Main.Instance.piano.Enable_Lights(false);
            p.sendMessage("light off :)");
        }));


          this.Add_ChildNamesTabCompleter();
    }
}

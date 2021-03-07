package jw_piano.jw_piano.Commands;

import Commands.Test.JW_Command;
import Managers.File_Manager;
import jw_piano.jw_piano.Data.Piano_settings;
import jw_piano.jw_piano.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Play_Command extends JW_Command
{

    public Play_Command()
    {
        super("play");
    }

    @Override
    public void Invoke(Player player_sender, String[] args)
    {

        if(Piano_settings.instnace.is_placed == false)
        {
            player_sender.sendMessage("You need to place the piano  /piano spawn" );
            return;
        }
       player_sender.sendMessage("Piano playing"+ args[0] +":)");
       Main.Instance.piano.Play_File(Main.GetPath()+args[0]);
    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {

    }

    @Override
    public void OnInitialize()
    {
      this.Add_TabCompleter(1,()->{
          return File_Manager.Get_FileNames(Main.GetPath(),"mid","midi");  });
      this.setUsage("piano <play> <midi_File>");
      this.Set_ErrorIfNoArgs((cs) ->
      {
          if(cs instanceof Player)
          {
              ((Player)(cs)).sendMessage("You need to sepcify midi file");
          }
      });
    }
}

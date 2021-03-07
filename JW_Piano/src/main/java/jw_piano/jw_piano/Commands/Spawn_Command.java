package jw_piano.jw_piano.Commands;

import Commands.Test.JW_Command;
import Managers.Block_Selector_Manager;
import jw_piano.jw_piano.Data.Piano_settings;
import jw_piano.jw_piano.Main;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Spawn_Command extends JW_Command
{

    public Spawn_Command()
    {
        super("spawn");
    }

    @Override
    public void Invoke(Player player_sender, String[] args)
    {
        player_sender.sendMessage("Hit block to spawning");

        Block_Selector_Manager.GetInstnace().SelectBlocks(1,player_sender,(blocks)->
        {
            if(Piano_settings.instnace.is_placed)
            {
                Main.Instance.piano.DestoryPiano();
            }


            Piano_settings.instnace.is_placed =true;
            Piano_settings.instnace.location =blocks.get(0).getLocation().toVector();
            Piano_settings.instnace.world_location =blocks.get(0).getWorld().getName();

            player_sender.sendMessage("Blocks selected");
            Main.Instance.piano.SpawnPiano(blocks.get(0).getLocation());
        });
    }

    @Override
    public void Invoke(ConsoleCommandSender server_sender, String[] args)
    {

    }

    @Override
    public void OnInitialize()
    {

    }
}

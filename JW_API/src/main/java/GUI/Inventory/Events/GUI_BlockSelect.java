package GUI.Inventory.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface GUI_BlockSelect
{
    public void Execute(Block item, Player sender);
}

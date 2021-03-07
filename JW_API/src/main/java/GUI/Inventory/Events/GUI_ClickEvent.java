package GUI.Inventory.Events;

import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Inventory;
import org.bukkit.entity.Player;

public interface GUI_ClickEvent<T extends GUI_Inventory>
{
    public void Execute(GUI_Button item, Player sender, T inventory);
}

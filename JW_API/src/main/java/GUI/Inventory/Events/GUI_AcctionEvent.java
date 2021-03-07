package GUI.Inventory.Events;

import org.bukkit.inventory.Inventory;

public interface GUI_AcctionEvent
{
    public void Execute(Object object, Inventory inventory, GUI_Acctions acction);
}

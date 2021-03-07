package CustomEvents;

import NPC.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNpcEvent extends Event implements Cancellable {

    public final static HandlerList handlerList = new HandlerList();

    public enum MouseClick{Left,Right}

    public NPC Npc_Clicked;

    public Player player;

    MouseClick mouseClick;

    public boolean canceled;

    public PlayerNpcEvent(Player player, NPC npc, MouseClick mouseClick)
    {
        this.player = player;
        this.Npc_Clicked = npc;
        this.mouseClick = mouseClick;
        Bukkit.getConsoleSender().sendMessage("Dsdsd");
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        canceled= b;
    }



    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public  HandlerList getHandlers() {
        return handlerList;
    }
}

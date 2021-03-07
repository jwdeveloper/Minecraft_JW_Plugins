package ORM.Entitis;

import org.bukkit.ChatColor;

public class SQL_Database_Entity
{
    public String server = new String();
    public String database = new String();
    public String user = new String();
    public String password  = new String();
    public boolean is_connected = false;

    public boolean is_enabled = false;
    public int sync_time = 1;

    public String is_connected_Str()
    {
        return  is_connected ==true? ChatColor.GREEN+"Connected":ChatColor.RED+"Disconnected";
    }

}

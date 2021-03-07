package Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManager
{


   public static HashMap<UUID, PermissionAttachment> players_Permissions = new HashMap<>();
   public static Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
   public static void Add_Permission(Player player,String permissoion)
   {
       PermissionAttachment attachment = player.getPlayer().addAttachment(plugin, "bukkit.command.setblock", true);
       player.recalculatePermissions();
       players_Permissions.put(player.getUniqueId(), attachment);
   }


   public static boolean Has_Permission_one(Player player, List<String> permissions)
   {
       if(player.isOp())
           return true;

       for (String p: permissions)
       {
         return player.hasPermission(p);
       }
       return false;
   }
    public static boolean Has_Permission_all(Player player, List<String> permissions)
    {
        if(player.isOp())
            return true;

        for (String p: permissions)
        {
            if(player.hasPermission(p)==false)
                return false;
        }
        return true;
    }

}

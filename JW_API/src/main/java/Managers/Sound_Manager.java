package Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Sound_Manager
{


    public void playerSound(Player p,String soundname)
    {

        Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
            try {
                p.playSound(p.getLocation(), org.bukkit.Sound.valueOf(soundname), 1.0F, 1.0F);
            } catch (IllegalArgumentException var3) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + plugin.getConfig().getString("OpenBookSoundEffect.sound-effect") + " is not a valid sound type!");
            }
    }
}

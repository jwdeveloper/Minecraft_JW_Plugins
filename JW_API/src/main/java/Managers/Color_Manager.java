package Managers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color_Manager implements Listener
{

        private Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

      public ChatColor getColor(int r, int g, int b)
      {
              return  ChatColor.of(new Color(r,g,b));
      }


      public static Color_Manager instance()
      {

              if(instance == null)
                      instance = new Color_Manager();

              return instance;
      }
      private static  Color_Manager instance;

      public Color_Manager()
      {
              Bukkit.getPluginManager().registerEvents(this,Bukkit.getPluginManager().getPlugins()[0]);
      }
      public String format(String msg)
      {

              Matcher matcher  = pattern.matcher(msg);
              while (matcher.find())
              {
                      String color =msg.substring(matcher.start(),matcher.end());
                     msg = msg.replace(color,ChatColor.of(color)+"");
                     matcher =pattern.matcher(msg);

              }
              return ChatColor.translateAlternateColorCodes('&',msg);
      }

      @EventHandler(priority  = EventPriority.HIGHEST)
      public void ChatEvent(AsyncPlayerChatEvent event)
      {
          if(event.isCancelled()==true)
              return;

          Bukkit.getConsoleSender().sendMessage(event.getFormat()+" "+event.getMessage());
          event.setFormat(format(event.getFormat().replaceAll("&","§")));
          event.setMessage(format(event.getMessage().replaceAll("&","§")));
      }

}

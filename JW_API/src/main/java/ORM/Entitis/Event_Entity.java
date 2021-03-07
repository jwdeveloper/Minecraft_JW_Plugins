package ORM.Entitis;

import Managers.CommandInvockerManager;
import Managers.File_Manager;
import Managers.I_Function;
import Managers.Object_Manager;
import com.google.gson.annotations.Expose;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class Event_Entity extends I_Entity
{
   public Event_Entity(String name,String desc)
   {
      this.id = UUID.randomUUID().toString();
      this.name = name;
      this.description = desc;
   }


   @File_Manager.GSON_Skip
   private ArrayList<Consumer<Player>> events_to_invoke = new ArrayList<>();

   public void OnEventInvoked(Consumer<Player> event)
   {
      if(events_to_invoke == null)
         events_to_invoke = new ArrayList<>();

      events_to_invoke.add(event);
   }


   public ArrayList<String> get_full_description()
   {
      ArrayList<String> strings = new ArrayList<>();
      strings.add("§f"+this.description);
      strings.add(" ");

      this.events.stream().forEach(e ->
      {
         strings.add("§f-"+e);
      });

      return strings;
   }

   public void events_invoke(String nick_or_uuid)
   {
      Player player = null;
      if(nick_or_uuid.length()<=16)
      {
         player =Bukkit.getPlayer(nick_or_uuid);
      }
      else
      {
         UUID uuid = UUID.fromString(nick_or_uuid);
         player =   Bukkit.getPlayer(uuid);
      }
      if(player!=null)
         events_invoke(player);
   }

   public void events_invoke(Player player)
   {
      this.events.stream().forEach(e ->
      {
         CommandInvockerManager.InvokeCommand(player,e);
      });
      this.events_to_invoke.forEach( (a) ->
      {
         a.accept(player);
      });
   }

   public ArrayList<String> events = new ArrayList<>();
}

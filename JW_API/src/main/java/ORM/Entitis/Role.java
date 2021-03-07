package ORM.Entitis;



import org.bukkit.Material;

import java.util.ArrayList;

public class Role
{
     public int id;
     public String name;
     public String prefix;
     public String suffix;
     public String description;
     public int wage;
     public boolean isDeafult;
     public Material icon;
     public ArrayList<String> parents;
     public ArrayList<String> permissions;
     public ArrayList<String> players;




     public Role(String name)
     {
          this.name = name;
          this.id=0;
          this.wage=0;
          this.prefix = "";
          this.suffix="";
          this.icon = Material.CREEPER_BANNER_PATTERN;
          this.parents = new ArrayList<>();
          this.permissions = new ArrayList<>();
          this.players = new ArrayList<>();
     }
}

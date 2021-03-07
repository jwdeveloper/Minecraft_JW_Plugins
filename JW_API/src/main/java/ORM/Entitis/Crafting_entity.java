package ORM.Entitis;

import org.bukkit.Material;

public class Crafting_entity
{
  public  Material material =Material.WHITE_STAINED_GLASS_PANE;
  public  int amount =0;

  public String get_name()
  {
    return material== Material.WHITE_STAINED_GLASS_PANE?new String():material.name();
  }
}

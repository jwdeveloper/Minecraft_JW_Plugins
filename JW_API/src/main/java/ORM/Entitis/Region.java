package ORM.Entitis;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Region
{
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Role> roles = new ArrayList<>();

    public int id=0;
    public String name="";

   public double minX=0;
   public double maxX=0;
   public double minY=0;
   public double maxY=0;
   public double minZ=0;
   public double maxZ=0;

}

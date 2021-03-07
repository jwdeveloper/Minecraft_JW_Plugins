package ORM.Entitis;

import ORM.Annotations.ORM_ID;
import org.bukkit.Material;

import java.util.UUID;

public class I_Entity
{
    @ORM_ID
    public String id= UUID.randomUUID().toString();
    public String name=  new String();
    public String description=  new String();
    public String content=  new String();
    public Material icon =Material.DIRT;



    public boolean is_null()
    {
        return  id.equalsIgnoreCase("-1") == true;
    }
}

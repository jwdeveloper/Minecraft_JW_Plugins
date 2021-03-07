package Managers;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Entity_Manager
{










    public static Material entityName_To_SpawnEgg(EntityType entity)
    {
        Material[] mats =  Material.values();
        String mat_name =new String();
        String entity_name = entity.name().toLowerCase();
        for(int i=0;i<mats.length;i++)
        {
            mat_name= mats[i].name().toLowerCase();

            if(mat_name.contains("spawn") && mat_name.contains(entity_name))
                return mats[i];
        }
        return Material.DIRT;
    }

}

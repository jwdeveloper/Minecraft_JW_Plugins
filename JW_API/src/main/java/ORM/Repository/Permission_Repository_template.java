package ORM.Repository;

import Managers.File_Manager;
import ORM.Entitis.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Permission_Repository_template implements I_Repository<Permission>
{

    public ArrayList<Permission> permissions = new ArrayList<>();

     String path;

    public Permission_Repository_template(String path)
    {
        this.path = path;
    }

    @Override
    public Permission Get_One(String id)
    {
        return permissions.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst().get();
    }

    @Override
    public ArrayList<Permission> Get_Many(HashMap<String, String> args) {
        return permissions;
    }


    public ArrayList<String> Get_Many()
    {
        ArrayList<String> result = new ArrayList<>();
        permissions.stream().forEach(p -> result.add(p.name));
        return result;
    }

    @Override
    public boolean Insert_One(Permission data)
    {
        data.id = UUID.randomUUID().toString();
        permissions.add(data);
        return true;
    }

    @Override
    public boolean Insert_Many(ArrayList<Permission> data)
    {
        data.stream().forEach(d -> {d.id = UUID.randomUUID().toString(); permissions.add(d);});
        return true;
    }

    @Override
    public boolean Update_One(String id, Permission data)
    {
        permissions.stream().filter(d -> d.id.equalsIgnoreCase(id)).findFirst().ifPresent(d -> d.name = data.name);
        return true;
    }

    @Override
    public boolean Update_Many(HashMap<String, Permission> data) {
        return false;
    }

    @Override
    public boolean Delete_One(String id, Permission data)
    {
        permissions.stream().filter(d -> d.id.equalsIgnoreCase(id)).findFirst().ifPresent(d -> permissions.remove(d));
        return true;
    }

    @Override
    public boolean Delete_Many(ArrayList<Permission> data)
    {
        return false;
    }

    @Override
    public boolean Load_Data()
    {
        try
        {
            permissions= File_Manager.Load_List(path,"permissions.json",Permission.class);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean Save_Data()
    {
        try
        {
            File_Manager.Save(permissions,path,"permissions.json");
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

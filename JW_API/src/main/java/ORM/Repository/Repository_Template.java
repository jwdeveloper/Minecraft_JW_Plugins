package ORM.Repository;

import Managers.File_Manager;
import ORM.Entitis.I_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Repository_Template<T extends I_Entity> implements I_Repository<T>
{


     public  ArrayList<T> content = new ArrayList<>();
     public String path;
     public   String filename;
     Class<T> entity_type;

    public Repository_Template(String path, Class<T> entity_type)
    {
        this.path = path;
        this.filename = entity_type.getSimpleName();
        this.entity_type = entity_type;
    }
    public Repository_Template(String path,String filename, Class<T> entity_type)
    {
        this.entity_type = entity_type;
        this.path = path;
        this.filename =filename;
    }
    @Override
    public  T Get_One(String id)
    {
        Optional<T> data = content.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst();
        if(data.isPresent())
        {
            return data.get();
        }
        return CreateEmpty();
    }

    public Repository_Template(String path)
    {
        this.path = path;
    }

    @Override
    public ArrayList<T> Get_Many(HashMap<String, String> args)
    {
      return content;
    }

    @Override
    public boolean Insert_One(T data)
    {
        if(data!=null)
        {
            data.id = UUID.randomUUID().toString();
            content.add(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean Insert_Many(ArrayList<T> data)
    {
        data.stream().forEach(a -> this.Insert_One(a));
        return true;
    }

    @Override
    public boolean Update_One(String id, T data)
    {
        return false;
    }

    @Override
    public boolean Update_Many(HashMap<String, T> data) {
        return false;
    }

    @Override
    public boolean Delete_One(String id, I_Entity data)
    {
        Optional<T> exist = content.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst();
        if(exist.isPresent())
        {
            content.remove(data);
            return true;
        }
        return false;
    }

    public void Delete_all()
    {
      content.clear();
    }

    public boolean Delete_One(String id)
    {
        Optional<T> exist = content.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst();
        if(exist.isPresent())
        {
            content.remove(exist.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean Delete_Many(ArrayList<T> data)
    {
        data.stream().forEach(a -> this.Delete_One(a.id,a));
        return true;
    }

    @Override
    public boolean Load_Data()
    {
        try
        {
            content= File_Manager.Load_List(path,filename+".json",entity_type);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean Save_Data() {
        try
        {
            File_Manager.Save(content,path,filename+".json");
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public T CreateEmpty()
    {
        try
        {
            T empty = entity_type.newInstance();
            empty.id = "-1";
            empty.name = "-1";
            return empty;
        }
        catch (IllegalAccessException e)
        {

        }
        catch (InstantiationException e2)
        {

        }
        return null;
    }
}

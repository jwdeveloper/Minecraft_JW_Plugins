package ORM.Repository;

import java.util.ArrayList;
import java.util.HashMap;

public interface I_Repository <T>
{
    public T Get_One(String id);

    public ArrayList<T> Get_Many(HashMap<String,String> args);

    public boolean Insert_One(T data);

    public boolean Insert_Many(ArrayList<T> data);

    public boolean Update_One(String id,T data);

    public boolean Update_Many(HashMap<String,T> data);

    public boolean Delete_One(String id,T data);

    public boolean Delete_Many(ArrayList<T> data);

    public boolean Load_Data();
    public boolean Save_Data();
}

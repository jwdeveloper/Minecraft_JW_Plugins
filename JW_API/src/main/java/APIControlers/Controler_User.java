package APIControlers;


import ORM.Entitis.User;
import HTTP.Callback;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controler_User extends API_Controller<User> {


    public Controler_User(String URL, Plugin plugin) {
        super(URL, User.class,plugin);
    }


   public void Find_Users_By_Role(String role, Callback<List<User>> callback)
   {
       Map<String, String> params = new HashMap<String,String>();
       params.put("Role",role);

       this.Get_Many(params,callback);
   }
    public void Find_User_By_UUID(String UUID, Callback<User> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);

        this.Get_One(params,callback);
    }
    public void Find_User_By_Name(String Name, Callback<User> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("Name",Name);

        this.Get_One(params,callback);
    }


}

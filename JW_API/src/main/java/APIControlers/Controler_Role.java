package APIControlers;

import ORM.Entitis.Role;
import ORM.Entitis.User;
import HTTP.Callback;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Controler_Role extends API_Controller<Role>
{

    public Controler_Role(String URL,  Plugin plugin) {


        super(URL, Role.class, plugin);
    }


    public void Add_Command(Role role,String command, Callback<User> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
      //  params.put("uuid",UUID);

       // this.Get_One(params,callback);
    }

    public void Delete_Command(Role role,String command, Callback<User> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
       // params.put("uuid",UUID);

      //  this.Get_One(params,callback);
    }

}

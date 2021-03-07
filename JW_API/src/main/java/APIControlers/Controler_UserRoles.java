package APIControlers;

import ORM.Entitis.Role;
import HTTP.Callback;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Controler_UserRoles extends Controler_User
{

    public Controler_UserRoles(String URL, Plugin plugin) {
        super(URL, plugin);
    }


    public void Users_Commands( Callback<String> callback)
    {
        String url = this.URL+"/roles/commands";
        this.Request(null,HTTP_Type.GET,url,callback);
    }
    public void User_ShowRole(String UUID,  Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);

        String url = this.URL+this.getParams(params);

        this.Request(null,HTTP_Type.GET,url,callback);
    }
    public void User_AddRole(String UUID, Role role, Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);

        String url = this.URL+this.getParams(params);

        this.Request(null,HTTP_Type.POST,url,callback);
    }

    public void User_DeleteRole(String UUID, Role role, Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);

        String url = this.URL+this.getParams(params);

        this.Request(null,HTTP_Type.DELETE,url,callback);
    }

}

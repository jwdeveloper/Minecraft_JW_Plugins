package APIControlers;

import ORM.Entitis.User;
import HTTP.Callback;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Controler_UserLogin extends Controler_User
{

    public Controler_UserLogin(String URL, Plugin plugin) {
        super(URL, plugin);
    }

    public void Login(String UUID,String password, Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);
        params.put("password",password);

        String url = this.URL+"/Login"+this.getParams(params);

        this.Request(null,HTTP_Type.GET,url,callback);
    }

    public void Register(User user, Callback<String> callback)
    {
        String url = this.URL+"/register";
        Gson gson = new Gson();
        Bukkit.getConsoleSender().sendMessage(url);

        this.Request(gson.toJson(user),HTTP_Type.POST,url,callback);
    }

    public void LogOut(String UUID, Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);

        String url = this.URL+"/logout"+this.getParams(params);

        this.Request(null,HTTP_Type.GET,url,callback);
    }
    public void LogOut_everyone(Callback<String> callback)
    {
        String url = this.URL+"/logout";
        this.Request(null,HTTP_Type.GET,url,callback);
    }
    public void NewPassword(String UUID,String oldpassword,String newPassword ,Callback<String> callback)
    {
        Map<String, String> params = new HashMap<String,String>();
        params.put("uuid",UUID);
        params.put("oldpassword",oldpassword);
        params.put("newpassword",newPassword);

        String url = this.URL+"/NewPassword"+this.getParams(params);

        this.Request(null,HTTP_Type.GET,url,callback);
    }

}

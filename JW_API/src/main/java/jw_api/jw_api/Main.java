package jw_api.jw_api;

import GUI.Inventory.GUI_Itemstack;
import GUI.Inventory.GUI_EventHandler;
import HTTP.HTTP_Manager;
import Languages.Language_Manager;
import Managers.Color_Manager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {


    public static Main instance;

    @Override
    public void onEnable() {
        Main.instance = this;

        HTTP_Manager.disableSslVerification();


       //   new NPC_EventManager(this);
      //  NPC_Manager.Instance().Load_NPC_Manager();
        Language_Manager.Instance();
        GUI_EventHandler.Instnace();
        GUI_Itemstack.Load_Items();
        Color_Manager.instance();


    }

    @Override
    public void onDisable() {

      //  NPC_Manager.Save();
     //   NPC_Manager.Instance().Close_NPC_Manager();
    }


}

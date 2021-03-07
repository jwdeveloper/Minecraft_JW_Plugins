package Managers;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

public class Skin_Manager
{
    private final Map<UUID, String[]> skins = Maps.newHashMap();

    public String[] getSkinDataByUUID(UUID uuid,boolean from_mojang)
    {
        if (this.skins.containsKey(uuid)) {
            return (String[])this.skins.get(uuid);
        } else {
            URLConnection var2;
            JsonElement var3;
            JsonElement var5;
            String var7;
            if (from_mojang)
            {
                try {
                    var2 = (new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false")).openConnection();
                    var2.setReadTimeout(3000);
                    var3 = (new JsonParser()).parse(new BufferedReader(new InputStreamReader(var2.getInputStream())));
                    JsonArray var4 = var3.getAsJsonObject().get("properties").getAsJsonArray();
                    var5 = var4.get(0);
                    String var6 = var5.getAsJsonObject().get("value").toString().replace("\"", "");
                    var7 = var5.getAsJsonObject().get("signature").toString().replace("\"", "");
                    String[] var8 = new String[]{var6, var7};
                    this.skins.put(uuid, var8);
                    return var8;
                } catch (Exception var10) {
                }
            } else {
                try {
                    var2 = (new URL("https://api.minetools.eu/profile/" + uuid)).openConnection();
                    var2.setReadTimeout(3000);
                    var3 = (new JsonParser()).parse(new BufferedReader(new InputStreamReader(var2.getInputStream())));
                    JsonElement var12 = var3.getAsJsonObject().get("raw");
                    var5 = var12.getAsJsonObject().get("properties");
                    JsonElement var13 = var5.getAsJsonArray().get(0);
                    var7 = var13.getAsJsonObject().get("value").toString().replace("\"", "");
                    String var14 = var13.getAsJsonObject().get("signature").toString().replace("\"", "");
                    String[] var9 = new String[]{var7, var14};
                    this.skins.put(uuid, var9);
                    return var9;
                } catch (Exception var11) {
                }
            }

            return new String[]{"NoValue", "NoSignature"};
        }
    }


}

package Managers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

public class ProfileLoader
{
    private final String uuid;
    private final String name;
    private final String skinOwner;

    public ProfileLoader(String uuid, String name) {
        this(uuid, name, name);
    }

    public ProfileLoader(String uuid, String name, String skinOwner) {
        uuid=null;
        this.uuid = uuid == null ? null : uuid.replaceAll("-", ""); //We add these later
        String displayName = ChatColor.translateAlternateColorCodes('&', name);
        this.name = ChatColor.stripColor(displayName);
        this.skinOwner = skinOwner;
    }

    public GameProfile loadProfile() {
        UUID id = uuid == null ? parseUUID(getUUID(name)) : parseUUID(uuid);
        GameProfile profile = new GameProfile(id, name);
        addProperties(profile);
        return profile;
    }

    @SuppressWarnings("resource")
    private void addProperties(GameProfile profile) {
        String uuid = getUUID(skinOwner);
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
            for (int i = 0; i < properties.size(); i++) {
                try {
                    JSONObject property = (JSONObject) properties.get(i);
                    String name = (String) property.get("name");
                    String value = (String) property.get("value");
                    String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
                    if (signature != null) {
                        profile.getProperties().put(name, new Property(name, value, signature));
                    } else {
                        profile.getProperties().put(name, new Property(value, name));
                    }
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
                }
            }
        } catch (Exception e) {
            //Failed loading skin, have in mind the Mojang API is public and it have restrictions
            //1 request of profile per minute and 600 requests per 10 minutes, may change
        }
    }

    @SuppressWarnings("deprecation")
    private String getUUID(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replaceAll("-", "");
    }

    private UUID parseUUID(String uuidStr) {
        String[] uuidComponents = new String[] { uuidStr.substring(0, 8),
                uuidStr.substring(8, 12), uuidStr.substring(12, 16),
                uuidStr.substring(16, 20),
                uuidStr.substring(20, uuidStr.length())
        };

        StringBuilder builder = new StringBuilder();
        for (String component : uuidComponents) {
            builder.append(component).append('-');
        }

        builder.setLength(builder.length() - 1);
        return UUID.fromString(builder.toString());
    }
}

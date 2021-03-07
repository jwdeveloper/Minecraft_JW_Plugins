package Managers;

import GUI.Inventory.GUI_Itemstack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class Player_Head_Manager
{
    private static String GetHeadValue(String playername){
        try {
            String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + playername);
            String uid = subString(result, "{\"id\":\"", "\",\"");
            String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
            String value = subString(signature, "\"value\":\"", "\"}]}");
            String decoded = new String(Base64.getDecoder().decode(value));
            String skinURL = subString(decoded, "\"url\":\"", "\"}}}");
            byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
            return new String(Base64.getEncoder().encode(skinByte));
        } catch (Exception ignored){ }
        return null;
    }
    private static String subString(String str, String strStart, String strEnd) {
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        return str.substring(strStartIndex, strEndIndex).substring(strStart.length());
    }
    private static String getURLContent(String urlStr) {
        URL url;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8) );
            String str;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ignored) { }
        finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch(IOException ignored) { }
        }
        return sb.toString();
    }

    private static String GetSkin(String playername)
    {
        try {
            String result = getURLContent("https://minotar.net/skin/" + playername);
            byte[] skinByte = (result).getBytes();
            return new String(Base64.getEncoder().encode(skinByte));
        } catch (Exception ignored){ }
        return null;
    }

    private static ItemStack GetSkill()
    {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1 , (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner("player name");
        item.setItemMeta(meta);
        return  item;
    }
    private static ItemStack getHead(String value) {
        ItemStack skull = GetSkill();
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }
    public static GUI_Itemstack Getplayerhead(String uuid)
    {
        UUID UUId = UUID.fromString(uuid);
        GUI_Itemstack prev = new GUI_Itemstack(Material.PLAYER_HEAD);
        SkullMeta pMeta = (SkullMeta) prev.getItemMeta();
        pMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUId));
        pMeta.setDisplayName("");   //Core.color("&8[&c« Seite «&8]")
        prev.setItemMeta(pMeta);
        return prev;
    }
    public static GUI_Itemstack Getplayerhead(String uuid,String name)
    {
        UUID UUId = UUID.fromString(uuid);
        GUI_Itemstack prev = new GUI_Itemstack(Material.PLAYER_HEAD);
        SkullMeta pMeta = (SkullMeta) prev.getItemMeta();
        try {
            OfflinePlayer p  = Bukkit.getOfflinePlayer(UUId);
            pMeta.setOwningPlayer( p);
            pMeta.setDisplayName(name);   //Core.color("&8[&c« Seite «&8]")
            prev.setItemMeta(pMeta);
        }catch (Exception e)
        {

        }

        return prev;
    }

    public static void GetHead(Inventory inventory,int slot,String player_name){


        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugins()[0], (Runnable) () -> {
            String value= GetHeadValue(player_name);
            String result  =  value==null?"":value;
            ItemStack item = getHead(result);
            inventory.setItem(slot,item);
        });

    }
}

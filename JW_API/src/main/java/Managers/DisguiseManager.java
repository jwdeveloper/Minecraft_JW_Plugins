package Managers;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DisguiseManager
{
    private Player player;
    private String realName;
    private String disguisedName;
    private UUID uuid;

    private GameProfile oldProfile;
    private GameProfile newProfile;

    public DisguiseManager(Player player, String disguisedName, GameProfile oldProfile, GameProfile newProfile)
    {
        this.player = player;
        this.realName = player.getName();
        this.disguisedName = disguisedName;
        this.uuid = player.getUniqueId();
        this.oldProfile = oldProfile;
        this.newProfile = newProfile;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public String getRealName()
    {
        return this.realName;
    }

    public String getDisguisedName()
    {
        return this.disguisedName;
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    public GameProfile getOldProfile()
    {
        return oldProfile;
    }

    public GameProfile getNewProfile()
    {
        return newProfile;
    }
}

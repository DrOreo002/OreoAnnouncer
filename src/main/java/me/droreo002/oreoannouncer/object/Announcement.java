package me.droreo002.oreoannouncer.object;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.manager.DataFile;
import me.droreo002.oreoannouncer.utils.CenterSender;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Announcement {

    private String plainText;
    private String name;
    private String json;
    private String title_message;
    private String title;
    private int title_time;
    private int title_fade_in;
    private int title_fade_out;
    private boolean needPermissionToSee;
    private boolean useTitle;
    private boolean useCustomSound;
    private boolean useJson;
    private Sound customSound;
    private float soundVolume;
    private float soundPitch;
    private boolean enabled;
    private boolean useCenteredMessage;
    private boolean useTotemAnimation;
    private boolean useHeadIcon;
    private Material icon;
    private String headIcon;
    private boolean isList;
    private List<String> message;

    public Announcement(String name) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name.toLowerCase());
        data.setupUpdate(); // For update
        this.json = data.getString("Data.json");
        this.useJson = data.getBoolean("Data.useJson");
        if (data.isList("Data.plainText")) {
            this.isList = true;
            message = data.getStringList("Data.plainText");
        } else {
            this.isList = false;
            this.plainText = data.getString("Data.plainText");
            message = new ArrayList<>();
        }
        this.title_message = data.getString("Data.title.title_sub");
        this.title = data.getString("Data.title.title_message");
        this.title_time = data.getInt("Data.title.title_stay");
        this.title_fade_in = data.getInt("Data.title.title_fade_in");
        this.title_fade_out = data.getInt("Data.title.title_fade_out");
        this.needPermissionToSee = data.getBoolean("Data.needPermissionToSee");
        this.name = name.toLowerCase();
        this.useTitle = data.getBoolean("Data.useTitle");
        this.useCustomSound = data.getBoolean("Data.sound.useCustomSound");
        this.customSound = Sound.valueOf(data.getString("Data.sound.sound"));
        this.soundPitch = (float) data.getInt("Data.sound.soundPitch");
        this.soundVolume = (float) data.getInt("Data.sound.soundVolume");
        this.enabled = data.getBoolean("Data.isEnabled");
        this.useCenteredMessage = data.getBoolean("Data.useCenteredMessage");
        this.useTotemAnimation = data.getBoolean("Data.useTotemAnimation");
        this.useHeadIcon = data.getBoolean("Data.guiSetting.useHeadIcon");
        try {
            this.icon = Material.valueOf(data.getString("Data.guiSetting.material"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on getting the material " + data.getString("Data.guiSetting.material"));
        }
        this.headIcon = data.getString("Data.guiSetting.headIcon");
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getSoundPitch() {
        return soundPitch;
    }

    public void send(Player player) {
        if (!isEnabled()) {
            return;
        }
        if (needPermissionToSee) {
            if (!player.hasPermission("oreoannounce.see." + name)) {
                return;
            }
        }
        if (!useJson) {
            List<String> result = new ArrayList<>();
            if (isList) {
                for (String s : message) {
                    if (PlaceholderAPI.containsPlaceholders(s)) {
                        s = PlaceholderAPI.setPlaceholders(player, s);
                    }
                    result.add(s);
                }
            } else {
                if (PlaceholderAPI.containsPlaceholders(plainText)) {
                    plainText = PlaceholderAPI.setPlaceholders(player, plainText);
                }
            }
            if (useCenteredMessage) {
                if (isList) {
                    for (String s : result) {
                        CenterSender.send(player, OreoAnnouncer.getInstance().color(s));
                    }
                } else {
                    CenterSender.send(player, OreoAnnouncer.getInstance().color(plainText));
                }
            } else {
                player.sendMessage(OreoAnnouncer.getInstance().color(plainText));
            }

            // Send title
            OreoAnnouncer main = OreoAnnouncer.getInstance();
            if (useTitle) {
                player.sendTitle(main.color(title), main.color(title_message), title_fade_in, title_time, title_fade_out);
            }

            // Send sound
            if (useCustomSound) {
                player.playSound(player.getLocation(), customSound, soundVolume, soundVolume);
            } else {
                Sound sound = Sound.valueOf(main.getConfigManager().getConfig().getString("Settings.sound"));
                float volume = (float) main.getConfigManager().getConfig().getInt("Settings.sound-volume");
                float pitch = (float) main.getConfigManager().getConfig().getInt("Settings.sound-pitch");
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
            if (useTotemAnimation) {
                EntityPlayer ep = ((CraftPlayer)player).getHandle();
                PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 35);
                ep.playerConnection.sendPacket(status);
            }
            return;
        }
        try {
            // Send text
            if (useCenteredMessage) {
                throw new IllegalStateException("useCenteredMessage cannot be enabled if announcer type is JSON!");
            }
            if (PlaceholderAPI.containsPlaceholders(json)) {
                json = PlaceholderAPI.setPlaceholders(player, json);
            }

            final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', json));
            final PacketPlayOutChat chat = new PacketPlayOutChat(icbc, ChatMessageType.CHAT);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);

            // Send title
            OreoAnnouncer main = OreoAnnouncer.getInstance();
            if (useTitle) {
                player.sendTitle(main.color(title), main.color(title_message), title_fade_in, title_time, title_fade_out);
            }

            // Send sound
            if (useCustomSound) {
                player.playSound(player.getLocation(), customSound, soundVolume, soundVolume);
            } else {
                Sound sound = Sound.valueOf(main.getConfigManager().getConfig().getString("Settings.sound"));
                float volume = (float) main.getConfigManager().getConfig().getInt("Settings.sound-volume");
                float pitch = (float) main.getConfigManager().getConfig().getInt("Settings.sound-pitch");
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
            if (useTotemAnimation) {
                EntityPlayer ep = ((CraftPlayer)player).getHandle();
                PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 35);
                ep.playerConnection.sendPacket(status);
            }
        }
        catch (Exception e) {
            // Ignore
            Bukkit.getLogger().warning("Cannot send title message with the name of : " + name + ", please check the json data!");
            e.printStackTrace();
        }
    }

    public String getPlainText() {
        return plainText;
    }

    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }

    public String getTitle_message() {
        return title_message;
    }

    public String getTitle() {
        return title;
    }

    public int getTitle_time() {
        return title_time;
    }

    public int getTitle_fade_in() {
        return title_fade_in;
    }

    public int getTitle_fade_out() {
        return title_fade_out;
    }

    public boolean isNeedPermissionToSee() {
        return needPermissionToSee;
    }

    public boolean isUseTitle() {
        return useTitle;
    }

    public boolean isUseCustomSound() {
        return useCustomSound;
    }

    public boolean isUseJson() {
        return useJson;
    }

    public Sound getCustomSound() {
        return customSound;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isUseCenteredMessage() {
        return useCenteredMessage;
    }

    public boolean isUseTotemAnimation() {
        return useTotemAnimation;
    }

    public boolean isUseHeadIcon() {
        return useHeadIcon;
    }

    public Material getIcon() {
        return icon;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setUseTotemAnimation(boolean bol) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.set("Data.useTotemAnimation", bol);
        data.save();
        save();
    }

    public void setUseCenteredMessage(boolean bol) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.set("Data.useCenteredMessage", bol);
        data.save();
        save();
    }

    public void setUseTitle(boolean bol) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.set("Data.useTitle", bol);
        data.save();
        save();
    }

    public void setUseCustomSound(boolean bol) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.set("Data.sound.useCustomSound", bol);
        data.save();
        save();
    }

    public void setEnabled(boolean bol) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.set("Data.isEnabled", bol);
        data.save();
        save();
    }

    private void save() {
        DataFile.remove(name);
        DataFile file = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        file.save();
        OreoAnnouncer.getInstance().getAnnouncerManager().reload(name);
    }
}

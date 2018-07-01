package me.droreo002.oreoannouncer.object;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.manager.DataFile;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

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

    public Announcement(String name) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name.toLowerCase());
        data.setup(name.toLowerCase());
        this.json = data.getString("Data.json");
        this.useJson = data.getBoolean("Data.useJson");
        this.plainText = data.getString("Data.plainText");
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
            if (PlaceholderAPI.containsPlaceholders(plainText)) {
                plainText = PlaceholderAPI.setPlaceholders(player, plainText);
            }
            player.sendMessage(OreoAnnouncer.getInstance().color(plainText));

            // Send title
            if (useTitle) {
                player.sendTitle(title, title_message, title_fade_in, title_time, title_fade_out);
            }

            // Send sound
            if (useCustomSound) {
                player.playSound(player.getLocation(), customSound, soundVolume, soundVolume);
            } else {
                OreoAnnouncer main = OreoAnnouncer.getInstance();
                Sound sound = Sound.valueOf(main.getConfigManager().getConfig().getString("Settings.sound"));
                float volume = (float) main.getConfigManager().getConfig().getInt("Settings.sound-volume");
                float pitch = (float) main.getConfigManager().getConfig().getInt("Settings.sound-pitch");
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
            return;
        }
        try {
            // Send text
            if (PlaceholderAPI.containsPlaceholders(json)) {
                json = PlaceholderAPI.setPlaceholders(player, json);
            }

            final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', json));
            final PacketPlayOutChat chat = new PacketPlayOutChat(icbc, ChatMessageType.CHAT);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);

            // Send title
            if (useTitle) {
                player.sendTitle(title, title_message, title_fade_in, title_time, title_fade_out);
            }

            // Send sound
            if (useCustomSound) {
                player.playSound(player.getLocation(), customSound, soundVolume, soundVolume);
            } else {
                OreoAnnouncer main = OreoAnnouncer.getInstance();
                Sound sound = Sound.valueOf(main.getConfigManager().getConfig().getString("Settings.sound"));
                float volume = (float) main.getConfigManager().getConfig().getInt("Settings.sound-volume");
                float pitch = (float) main.getConfigManager().getConfig().getInt("Settings.sound-pitch");
                player.playSound(player.getLocation(), sound, volume, pitch);
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

    private void save() {
        DataFile.remove(name);
        DataFile file = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        file.save();
        OreoAnnouncer.getInstance().getAnnouncerManager().reload(name);
    }
}

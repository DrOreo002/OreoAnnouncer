package me.droreo002.oreoannouncer.manager;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataFile extends YamlConfiguration {

    private static final Map<String, DataFile> configs = new HashMap<>();

    public static DataFile getConfig(OreoAnnouncer plugin, String name) {
        synchronized (configs) {
            return configs.computeIfAbsent(name, k -> new DataFile(plugin, name.toLowerCase()));
        }
    }

    public static void remove(String name) {
        configs.remove(name);
    }

    public static void removeAll() {
        synchronized (configs) {
            configs.clear();
        }
    }

    private final File file;
    private final Object saveLock = new Object();
    private final String name;
    private final OreoAnnouncer plugin;

    private DataFile(OreoAnnouncer plugin, String name) {
        super();
        this.name = name;
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data" + File.separator + name.toLowerCase() + ".yml");
        reload();
    }

    private void reload() {
        synchronized (saveLock) {
            try {
                load(file);
            } catch (Exception ignore) {
            }
        }
    }

    public void save() {
        synchronized (saveLock) {
            try {
                save(file);
            } catch (Exception ignore) {
            }
        }
    }

    public File getFile() {
        return file;
    }

    public void setupFromOld(String name) {
        FileConfiguration con = OreoAnnouncer.getInstance().getConfigManager().getConfig();
        if (!contains("Data.name")) {
            set("Data.name", name);
        }
        if (!contains("Data.useJson")) {
            set("Data.useJson", true);
        }
        if (!contains("Data.plainText")) {
            set("Data.plainText", "This is a text message!");
        }
        if (!contains("Data.json")) {
            set("Data.json", con.getString("Announcement." + name + ".message"));
        }
        if (!contains("Data.useTitle")) {
            set("Data.useTitle", con.getBoolean("Announcement." + name + ".title.use-title"));
        }
        if (!contains("Data.title.title_message")) {
            set("Data.title.title_message", con.getString("Announcement." + name + ".title.title-text"));
        }
        if (!contains("Data.title.title_sub")) {
            set("Data.title.title_sub", con.getString("Announcement." + name + ".title.title-bottom-text"));
        }
        if (!contains("Data.title.title_fade_in")) {
            set("Data.title.title_fade_in", con.getString("Announcement." + name + ".title.title-fade-in"));
        }
        if (!contains("Data.title.title_fade_out")) {
            set("Data.title.title_fade_out", con.getString("Announcement." + name + ".title.title-fade-out"));
        }
        if (!contains("Data.title.title_stay")) {
            set("Data.title.title_stay", con.getString("Announcement." + name + ".title.title-stay"));
        }
        if (!contains("Data.needPermissionToSee")) {
            set("Data.needPermissionToSee", con.getBoolean("Announcement." + name + ".needPermissionToSee"));
        }
        if (!contains("Data.sound.useCustomSound")) {
            set("Data.sound.useCustomSound", con.getBoolean("Announcement." + name + ".useCustomSound"));
        }
        if (!contains("Data.sound.sound")) {
            set("Data.sound.sound", con.getString("Announcement." + name + ".sound.type"));
        }
        if (!contains("Data.sound.soundVolume")) {
            set("Data.sound.soundVolume", con.getInt("Announcement." + name + ".sound.volume"));
        }
        if (!contains("Data.sound.soundPitch")) {
            set("Data.sound.soundPitch", con.getInt("Announcement." + name + ".sound.pitch"));
        }
        if (!contains("Data.useCenteredMessage")) {
            set("Data.useCenteredMessage", false);
        }
        if (!contains("Data.isEnabled")) {
            set("Data.isEnabled", true);
        }
        save();
    }

    public void setup(String name) {
        if (!contains("Data.name")) {
            set("Data.name", name);
        }
        if (!contains("Data.useJson")) {
            set("Data.useJson", true);
        }
        if (!contains("Data.plainText")) {
            set("Data.plainText", "This is a text message!");
        }
        if (!contains("Data.json")) {
            set("Data.json", "{\"text\":\"This is a test message!\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"This is a test message!\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click me!\"}}");
        }
        if (!contains("Data.useTitle")) {
            set("Data.useTitle", false);
        }
        if (!contains("Data.title.title_message")) {
            set("Data.title.title_message", "&7[ &cTest Title &7]");
        }
        if (!contains("Data.title.title_sub")) {
            set("Data.title.title_sub", "&fThis is a sub title!");
        }
        if (!contains("Data.title.title_fade_in")) {
            set("Data.title.title_fade_in", 20);
        }
        if (!contains("Data.title.title_fade_out")) {
            set("Data.title.title_fade_out", 20);
        }
        if (!contains("Data.title.title_stay")) {
            set("Data.title.title_stay", 40);
        }
        if (!contains("Data.needPermissionToSee")) {
            set("Data.needPermissionToSee", false);
        }
        if (!contains("Data.sound.useCustomSound")) {
            set("Data.sound.useCustomSound", false);
        }
        if (!contains("Data.sound.sound")) {
            set("Data.sound.sound", Sound.BLOCK_NOTE_PLING.toString());
        }
        if (!contains("Data.sound.soundVolume")) {
            set("Data.sound.soundVolume", 50f);
        }
        if (!contains("Data.sound.soundPitch")) {
            set("Data.sound.soundPitch", 0f);
        }
        if (!contains("Data.useCenteredMessage")) {
            set("Data.useCenteredMessage", false);
        }
        if (!contains("Data.isEnabled")) {
            set("Data.isEnabled", true);
        }
        save();
    }

    // Update things
    public void setupUpdate() {
        if (!contains("Data.useTotemAnimation")) {
            set("Data.useTotemAnimation", false);
        }
        if (!contains("Data.guiSetting")) {
            set("Data.guiSetting.useHeadIcon", false);
            set("Data.guiSetting.material", Material.BOOK.toString());
            set("Data.guiSetting.headIcon", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19\\");
        }
        save();
    }
}

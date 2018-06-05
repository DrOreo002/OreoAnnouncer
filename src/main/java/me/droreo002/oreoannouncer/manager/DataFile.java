package me.droreo002.oreoannouncer.manager;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import org.bukkit.Sound;
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

    public void setup(String name) {
        set("Data.name", name);
        set("Data.useJson", true);
        set("Data.plainText", "This is a text message!");
        set("Data.json", "{\"text\":\"This is a test message!\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"This is a test message!\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click me!\"}}");
        set("Data.useTitle", false);
        set("Data.title.title_message", "&7[ &cTest Title &7]");
        set("Data.title.title_sub", "&fThis is a sub title!");
        set("Data.title.title_fade_in", 20);
        set("Data.title.title_fade_out", 20);
        set("Data.title.title_stay", 40);
        set("Data.needPermissionToSee", false);
        set("Data.sound.useCustomSound", false);
        set("Data.sound.sound", Sound.BLOCK_NOTE_PLING.toString());
        set("Data.sound.soundVolume", 50f);
        set("Data.sound.soundPitch", 0f);
        save();
    }
}

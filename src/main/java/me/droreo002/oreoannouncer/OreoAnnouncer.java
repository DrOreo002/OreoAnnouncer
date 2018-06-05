package me.droreo002.oreoannouncer;

import me.droreo002.oreoannouncer.manager.AnnouncerManager;
import me.droreo002.oreoannouncer.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class OreoAnnouncer extends JavaPlugin {

    private ConfigManager configManager;
    private AnnouncerManager announcerManager;
    private static OreoAnnouncer instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        configManager = ConfigManager.getInstance(this);
        configManager.setup();
        announcerManager = AnnouncerManager.get(this);
        announcerManager.loadAll();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix() {
        return color(configManager.getConfig().getString("Prefix"));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public AnnouncerManager getAnnouncerManager() {
        return announcerManager;
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static OreoAnnouncer getInstance() {
        return instance;
    }
}

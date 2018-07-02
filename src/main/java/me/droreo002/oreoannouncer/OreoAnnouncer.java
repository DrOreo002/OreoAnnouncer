package me.droreo002.oreoannouncer;

import me.droreo002.oreoannouncer.command.MainCommand;
import me.droreo002.oreoannouncer.inventory.api.InventoryListener;
import me.droreo002.oreoannouncer.manager.AnnouncerManager;
import me.droreo002.oreoannouncer.manager.ConfigManager;
import me.droreo002.oreoannouncer.manager.RunnableManager;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;

public class OreoAnnouncer extends JavaPlugin {

    private ConfigManager configManager;
    private AnnouncerManager announcerManager;
    private static OreoAnnouncer instance;
    private int announcerID;

    // Todo : Continue the recode, its easy anyway
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        configManager = ConfigManager.getInstance(this);
        configManager.setup();
        announcerManager = AnnouncerManager.get(this);
        announcerManager.loadAll();
        if (configManager.getConfig().isSet("Announcement")) {
            ConfigurationSection cs = configManager.getConfig().getConfigurationSection("Announcement");
            if (cs != null) {
                for (String key : cs.getKeys(false)) {
                    File file = new File(getDataFolder(), "data" + File.separator + key.toLowerCase() + ".yml");
                    if (file.exists()) {
                        continue;
                    }
                    System.out.println("Creating an announcement from old file with the name of " + key);
                    Utils.createAnnouncement(key);
                }
            }
        }
        announcerID = Bukkit.getScheduler().runTaskTimer(this, new RunnableManager(this), 0, 20L * configManager.getConfig().getInt("Settings.announcer-countdown")).getTaskId();
        System.out.println("Announcement runnable started. ID : " + announcerID);


        // Command registering
        Bukkit.getPluginCommand("oreoannouncer").setExecutor(new MainCommand(this));

        // Listener registering
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
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

    public void setAnnouncerID(int announcerID) {
        this.announcerID = announcerID;
    }

    public int getAnnouncerID() {
        return announcerID;
    }
}

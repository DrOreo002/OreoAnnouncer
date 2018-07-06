package me.droreo002.oreoannouncer;

import me.droreo002.oreoannouncer.command.MainCommand;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.command.edit.objects.*;
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
import java.util.HashMap;
import java.util.Map;

public class OreoAnnouncer extends JavaPlugin {

    private ConfigManager configManager;
    private AnnouncerManager announcerManager;
    private static OreoAnnouncer instance;
    private int announcerID;
    private final Map<String, EditCommand> commands = new HashMap<>();

    // Todo : Continue the recode, its easy anyway
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        configManager = ConfigManager.getInstance(this);
        configManager.setup();
        updateConfig();
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
        registerEditCommand();

        // Listener registering
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    private void registerEditCommand() {
        /*
        Messages
         */
        new EditMessageCommand().register();
        new EditMessageJsonCommand().register();
        new EditUseJsonCommand().register();
        /*
        Titles
         */
        new EditUseTitleCommand().register();
        new EditTitleCommand().register();
        new EditTitleSubCommand().register();
        new EditTitleFadeInCommand().register();
        new EditTitleFadeOutCommand().register();
        new EditTitleStayCommand().register();
        /*
        Gui's
         */
        new EditGuiMaterialCommand().register();
        new EditUseCustomHeadCommand().register();
        new EditHeadTextureCommand().register();
        /*
        Sounds
         */
        new EditUseCustomSoundCommand().register();
        new EditCustomSoundCommand().register();
        new EditCustomSoundPitch().register();
        new EditCustomSoundVolume().register();
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

    public Map<String, EditCommand> getCommands() {
        return commands;
    }

    private void updateConfig() {
        if (!configManager.getConfig().isSet("Settings.enableForceSendInCommandAndGui")) {
            configManager.getConfig().set("Settings.enableForceSendInCommandAndGui", true);
        }
        if (!configManager.getConfig().isSet("Settings.enableForceSendInRunnable")) {
            configManager.getConfig().set("Settings.enableForceSendInRunnable", false);
        }
        configManager.reloadConfig();
    }
}

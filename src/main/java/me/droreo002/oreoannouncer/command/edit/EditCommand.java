package me.droreo002.oreoannouncer.command.edit;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public abstract class EditCommand {

    private String name;
    private String activator;
    private String permission;
    private boolean needPermission;

    public EditCommand(String name, String activator) {
        this.name = name;
        this.activator = activator;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setNeedPermission(boolean needPermission) {
        this.needPermission = needPermission;
    }

    public String getName() {
        return name;
    }

    public String getActivator() {
        return activator;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isNeedPermission() {
        return needPermission;
    }

    public void register() {
        OreoAnnouncer.getInstance().getCommands().put(name, this);
    }

    public void error(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
    }

    public void success(Player player) {
        player.playSound(player.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 100, 0);
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    protected abstract void execute(OreoAnnouncer main, Player player, String[] args, Announcement an);
}

package me.droreo002.oreoannouncer.command;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.inventory.AnnouncementList;
import me.droreo002.oreoannouncer.manager.DataFile;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class MainCommand implements CommandExecutor {

    private OreoAnnouncer main;

    public MainCommand(OreoAnnouncer main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Player only. Use /oanc for console command!");
            return true;
        }
        Player player = (Player) commandSender;
        if (args.length > 0) {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix() + "Usage : /oan reload <all | timer | announcement | config>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "create":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix() + "Usage : /oan create <name>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "save":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix() + "Usage : /oan save <name>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "setting":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix() + "Usage : /oan setting <name>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "delete":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix()  + "Usage : /oan delete <name>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "about":
                        player.sendMessage(main.color("&m&a-----------------------------"));
                        player.sendMessage(" ");
                        player.sendMessage(main.color("&fPlugin is created by &c@DrOreo002"));
                        player.sendMessage(" ");
                        player.sendMessage(main.color("&m&a-----------------------------"));
                        return true;
                    case "list":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        new AnnouncementList(main, player).build(player).open();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "help":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix() + "See the spigot page for more information");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    default:
                        player.sendMessage(main.getPrefix() + "Unknown command!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    String name = args[1].toLowerCase();
                    File file = new File(main.getDataFolder(), "data" + File.separator + name + ".yml");
                    if (file.exists()) {
                        player.sendMessage(main.getPrefix() + "Announcement with that name is already exists!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    Utils.createAnnouncement(name, player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    String name = args[1].toLowerCase();
                    File file = new File(main.getDataFolder(), "data" + File.separator + name + ".yml");
                    if (!file.exists()) {
                        player.sendMessage(main.getPrefix() + "There's no announcement with the name of " + name);
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    Utils.deleteAnnouncement(name, player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("save")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    String name = args[1].toLowerCase();
                    File file = new File(main.getDataFolder(), "data" + File.separator + name + ".yml");
                    if (!file.exists()) {
                        player.sendMessage(main.getPrefix() + "There's no announcement with the name of " + name);
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    DataFile.remove(name);
                    DataFile data = DataFile.getConfig(main, name);
                    data.save();
                    player.sendMessage(main.getPrefix() + "Data saved.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    return true;
                }
            }
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
            player.sendMessage(main.color("&7[ &aOreoAnnouncer &7] &f➟ Plugin made by &cDrOreo002"));
            return true;
        }
        return false;
    }
}

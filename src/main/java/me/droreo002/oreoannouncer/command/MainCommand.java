package me.droreo002.oreoannouncer.command;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.inventory.AnnouncementList;
import me.droreo002.oreoannouncer.inventory.EditorInventory;
import me.droreo002.oreoannouncer.manager.DataFile;
import me.droreo002.oreoannouncer.manager.RunnableManager;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Bukkit;
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
                    case "stop":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        if (main.getAnnouncerID() == 0) {
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            player.sendMessage(main.getPrefix() + "No announcement runnable is started. Please start it manually first!");
                            return true;
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        Bukkit.getScheduler().cancelTask(main.getAnnouncerID());
                        player.sendMessage(main.getPrefix() + "Announcement is now stopped. It will be enabled automatically after the server restarted or by using /oan start command.");
                        return true;
                    case "start":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        if (main.getAnnouncerID() != 0) {
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            player.sendMessage(main.getPrefix() + "Announcement runnable is already started!");
                            return true;
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        player.sendMessage(main.getPrefix() + "Started. You can stop it with /oan stop command if you want to.");
                        main.setAnnouncerID(Bukkit.getScheduler().runTaskTimer(main, new RunnableManager(main), 0, 20L * main.getConfigManager().getConfig().getInt("Settings.announcer-countdown")).getTaskId());
                        return true;
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
                    case "delete":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix()  + "Usage : /oan delete <name>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "send":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix()  + "Usage : /oan send <name> <player|all>");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "edit":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.sendMessage(main.getPrefix()  + "Usage : /oan edit <name>");
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
                        if (main.getAnnouncerManager().getAnnouncementMap().isEmpty()) {
                            player.sendMessage(main.getPrefix() + "There's no announcement available!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        new AnnouncementList(main, player).open(player);
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
                if (args[0].equalsIgnoreCase("edit")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    Announcement an = main.getAnnouncerManager().getAnnoucement(args[1].toLowerCase());
                    if (an == null) {
                        player.sendMessage(main.getPrefix() + "Cannot find that Announcement!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    player.openInventory(new EditorInventory(main, player, an).getInventory());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    String text = args[1];
                    if (text.equalsIgnoreCase("all")) {
                        long start = System.currentTimeMillis();
                        main.getConfigManager().reloadConfig();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        if (main.getAnnouncerID() == 0) {
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            main.setAnnouncerID(Bukkit.getScheduler().runTaskTimer(main, new RunnableManager(main), 0, 20L * main.getConfigManager().getConfig().getInt("Settings.announcer-countdown")).getTaskId());
                        }
                        Bukkit.getScheduler().cancelTask(main.getAnnouncerID());
                        main.setAnnouncerID(Bukkit.getScheduler().runTaskTimer(main, new RunnableManager(main), 0, 20L * main.getConfigManager().getConfig().getInt("Settings.announcer-countdown")).getTaskId());
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        main.getAnnouncerManager().reloadAll();
                        long end = System.currentTimeMillis();
                        player.sendMessage("Successfully reloaded all things!. Took : " + (end - start) + "ms!");
                        return true;
                    }
                    if (text.equalsIgnoreCase("config")) {
                        long start = System.currentTimeMillis();
                        main.getConfigManager().reloadConfig();
                        player.sendMessage(main.getPrefix() + "Config reloaded");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        long end = System.currentTimeMillis();
                        player.sendMessage("Successfully reloaded!. Took : " + (end - start) + "ms!");
                        return true;
                    }
                    if (text.equalsIgnoreCase("timer")) {
                        long start = System.currentTimeMillis();
                        if (main.getAnnouncerID() == 0) {
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            player.sendMessage(main.getPrefix() + "Error. Announcement ID is 0!. Recreating announcement ID");
                            main.setAnnouncerID(Bukkit.getScheduler().runTaskTimer(main, new RunnableManager(main), 0, 20L * main.getConfigManager().getConfig().getInt("Settings.announcer-countdown")).getTaskId());
                            long end = System.currentTimeMillis();
                            player.sendMessage("Successfully reloaded!. Took : " + (end - start) + "ms!");
                            return true;
                        }
                        Bukkit.getScheduler().cancelTask(main.getAnnouncerID());
                        main.setAnnouncerID(Bukkit.getScheduler().runTaskTimer(main, new RunnableManager(main), 0, 20L * main.getConfigManager().getConfig().getInt("Settings.announcer-countdown")).getTaskId());
                        player.sendMessage(main.getPrefix() + "Reloaded!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        long end = System.currentTimeMillis();
                        player.sendMessage("Successfully reloaded!. Took : " + (end - start) + "ms!");
                        return true;
                    }
                    if (text.equalsIgnoreCase("announcement")) {
                        long start = System.currentTimeMillis();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        main.getAnnouncerManager().reloadAll();
                        player.sendMessage(main.getPrefix() + "Reloaded. TIPS : To reload specific announcement. Please use /oan save <name> instead!");
                        long end = System.currentTimeMillis();
                        player.sendMessage("Successfully reloaded!. Took : " + (end - start) + "ms!");
                        return true;
                    }
                    player.sendMessage(main.getPrefix() + "Unknown reload type. Available : all, announcement, timer, config");
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                }
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
                    player.sendMessage(main.getPrefix() + "Data saved + reloaded!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    main.getAnnouncerManager().reload(name);
                    return true;
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("send")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    Announcement an = main.getAnnouncerManager().getAnnoucement(args[1].toLowerCase());
                    if (an == null) {
                        player.sendMessage(main.getPrefix() + "Cannot find that announcement!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("all")) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            an.send(online);
                        }
                        player.sendMessage(main.getPrefix() + "Sended to all online players!");
                        return true;
                    }
                    Player target = Bukkit.getPlayerExact(args[2]);
                    if (target == null) {
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        player.sendMessage(main.getPrefix() + "That player is not ONLINE!");
                        return true;
                    }
                    an.send(target);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    player.sendMessage(main.getPrefix() + "Sended to " + target.getName());
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

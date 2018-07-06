package me.droreo002.oreoannouncer.command;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditorCommandManager;
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
                        player.sendMessage(main.color("&7&m---------------------------------------"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7> &f/oan &7| &6Main command"));
                        player.sendMessage(main.color("&7> &f/oan help &7| &6Shows this command"));
                        player.sendMessage(main.color("&7> &f/oan stop &7| &6Stop the runnable"));
                        player.sendMessage(main.color("&7> &f/oan start &7| &6Start the runnable"));
                        player.sendMessage(main.color("&7> &f/oan create <name> &7| &6Create an Announcement"));
                        player.sendMessage(main.color("&7> &f/oan delete <name> &7| &6Delete an Announcement"));
                        player.sendMessage(main.color("&7> &f/oan list &7| &6Show announcement list"));
                        player.sendMessage(main.color("&7> &f/oan edit <name> &7| &6Edit an announcement (Full edit features use /oan editor command)"));
                        player.sendMessage(main.color("&7> &f/oan editor <name> <type> &7| &6A full editor for announcement (Not gui)"));
                        player.sendMessage(main.color("&7> &f/oan editor help &7| &6Shows an help page for editor things"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7&m---------------------------------------"));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        return true;
                    case "editor":
                        if (!player.hasPermission("oan.admin")) {
                            player.sendMessage(main.getPrefix() + "No permission!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        player.sendMessage(main.getPrefix() + "Usage : /oan editor <announcement> <type>");
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
                if (args[0].equalsIgnoreCase("editor")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("help")) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                        player.sendMessage(main.color("&7&m---------------------------------------"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7> &fUSE_TITLE &7| &6Edit the useTitle boolean"));
                        player.sendMessage(main.color("&7> &fTITLE &7| &6Edit the title message"));
                        player.sendMessage(main.color("&7> &fTITLE_SUB &7| &6Edit the sub title"));
                        player.sendMessage(main.color("&7> &fTITLE_FADE_IN &7| &6Edit the title fade in"));
                        player.sendMessage(main.color("&7> &fTITLE_FADE_OUT &7| &6Edit the title fade out"));
                        player.sendMessage(main.color("&7> &fTITLE_STAY &7| &6Edit the title stay"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7> &fMESSAGE &7| &6Edit the message"));
                        player.sendMessage(main.color("&7> &fUSE_JSON &7| &6Edit the useJson boolean"));
                        player.sendMessage(main.color("&7> &fMESSAGE_JSON &7| &6Edit the json message"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7> &fUSE_CUSTOMHEAD &7| &6Edit the useCustomHead boolean"));
                        player.sendMessage(main.color("&7> &fGUI_TEXTURE &7| &6Edit the gui head texture"));
                        player.sendMessage(main.color("&7> &fGUI_MATERIAL &7| &6Edit the gui head texture"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.color("&7> &fUSE_CUSTOMSOUND &7| &6Edit the useCustomSound boolean"));
                        player.sendMessage(main.color("&7> &fCUSTOM_SOUND &7| &6Edit the custom sound type"));
                        player.sendMessage(main.color("&7> &fCUSTOM_SOUND_VOLUME &7| &6Edit the custom sound volume"));
                        player.sendMessage(main.color("&7> &fCUSTOM_SOUND_PITCH &7| &6Edit the custom sound pitch"));
                        player.sendMessage(main.color("&7&m---------------------------------------"));
                        player.sendMessage(main.color("&r"));
                        player.sendMessage(main.getPrefix() + main.color("You can run the command for more information. This command is intended for editing some text, or important stuff. To edit the basic things you can use &6/oan edit <name> &fcommand or edit it directly on the data file"));
                        return true;
                    } else {
                        Announcement an = main.getAnnouncerManager().getAnnoucement(args[1]);
                        if (an == null) {
                            player.sendMessage(main.getPrefix() + "Cannot find that announcement!");
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                            return true;
                        } else {
                            player.sendMessage(main.getPrefix() + "Usage : /oan editor <announcement | help> <type>");
                        }
                    }
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
                        if (main.getConfigManager().getConfig().getBoolean("Settings.enableForceSendInCommandAndGui")) {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                an.send(online, true);
                            }
                            player.sendMessage(main.getPrefix() + "The announcement has been force sent to all online players!");
                        } else {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                an.send(online, false);
                            }
                            player.sendMessage(main.getPrefix() + "The announcement has been sent to all online player. Btw, for sent is disabled in config, so announcement will not be sent if its disabled.");
                        }
                        return true;
                    }
                    Player target = Bukkit.getPlayerExact(args[2]);
                    if (target == null) {
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        player.sendMessage(main.getPrefix() + "That player is not ONLINE!");
                        return true;
                    }
                    if (main.getConfigManager().getConfig().getBoolean("Settings.enableForceSendInCommandAndGui")) {
                        an.send(target, true);
                        player.sendMessage(main.getPrefix() + "The announcement has been force sent to " + target.getName());
                    } else {
                        an.send(target, false);
                        player.sendMessage(main.getPrefix() + "The announcement has been sent to " + target.getName() + ".Btw, for sent is disabled in config, so announcement will not be sent if its disabled.");
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    return true;
                }
            }
            if (args.length >= 3) {
                if (args[0].equalsIgnoreCase("editor")) {
                    if (!player.hasPermission("oan.admin")) {
                        player.sendMessage(main.getPrefix() + "No permission!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    Announcement an = main.getAnnouncerManager().getAnnoucement(args[1]);
                    if (an == null) {
                        player.sendMessage(main.getPrefix() + "Cannot find that announcement!");
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 1);
                        return true;
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
                    new EditorCommandManager(main, an).onCommand(commandSender, command, s, args);
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

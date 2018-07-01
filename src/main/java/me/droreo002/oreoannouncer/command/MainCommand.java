package me.droreo002.oreoannouncer.command;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                        player.sendMessage(main.getPrefix() + "Usage : /oan reload <all | timer | announcement | config>");
                        return true;
                    case "create":
                        player.sendMessage(main.getPrefix() + "Usage /oan create <name>");
                        return true;
                    case "help":
                        player.sendMessage(main.getPrefix() + "See the spigot page for more information");
                        return true;
                    default:
                        player.sendMessage(main.getPrefix() + "Unknown command!");
                        return true;
                }
            }

        }
        return false;
    }
}

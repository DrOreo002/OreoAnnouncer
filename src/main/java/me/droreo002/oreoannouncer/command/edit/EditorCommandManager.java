package me.droreo002.oreoannouncer.command.edit;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class EditorCommandManager implements CommandExecutor {

    private OreoAnnouncer main;
    private Announcement an;

    public EditorCommandManager(OreoAnnouncer main, Announcement an) {
        this.main = main;
        this.an = an;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // There will be no console command k?
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length >= 3) {
            int success = 0;
            for (Map.Entry ent : main.getCommands().entrySet()) {
                EditCommand clazz = (EditCommand) ent.getValue();
                if (clazz.getActivator().equalsIgnoreCase(args[2])) {
                    if (clazz.isNeedPermission()) {
                        if (!player.hasPermission(clazz.getPermission())) {
                            clazz.error(player);
                            player.sendMessage(main.getPrefix() + "No permission!");
                            return true;
                        }
                    }
                    List<String> tos = new ArrayList<>();
                    Collections.addAll(tos, args);
                    tos.remove(0);
                    tos.remove(0);
                    String[] add = new String[tos.size()];
                    add = tos.toArray(add);
                    clazz.execute(main, player, add, an);
                    success++;
                    break;
                }
            }
            if (success == 0) {
                player.sendMessage(main.getPrefix() + main.color("Editor command not found!. Type &6/oan editor help &fto see lists of edit command!"));
                return true;
            }
        }
        return false;
    }
}

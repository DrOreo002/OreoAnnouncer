package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EditTitleCommand extends EditCommand {

    public EditTitleCommand() {
        super("edittitle", "title");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " title " + " <title_message>");
            return;
        } else {
            StringBuilder build = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                build.append(args[i]).append(" ");
            }
            String msg = build.toString();
            an.setTitle(msg);
            player.sendMessage(main.getPrefix() + main.color("Announcement title message has been set to " + msg));
            player.sendMessage(main.color("&fpreviewing the current title in 5 second"));
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                player.sendMessage(main.getPrefix() + "Sending...");
                an.sendTitle(player);
            }, 5 * 20L);
            success(player);
        }
    }
}

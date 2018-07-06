package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EditTitleFadeInCommand extends EditCommand {

    public EditTitleFadeInCommand() {
        super("edittitlefadein", "title_fade_in");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " title_fade_in " + " <integer>");
            return;
        }
        int number = 0;
        try {
            number = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(main.getPrefix() + "Please type integer only!");
            error(player);
            return;
        }
        success(player);
        an.setTitleFadeIn(number);
        player.sendMessage(main.getPrefix() + "Title fade in has been set to " + number + " (In ticks)");
        player.sendMessage(main.color("&fpreviewing the current title in 5 second"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            player.sendMessage(main.getPrefix() + "Sending...");
            an.sendTitle(player);
        }, 5 * 20L);
    }
}

package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.entity.Player;

public class EditUseTitleCommand extends EditCommand {

    public EditUseTitleCommand() {
        super("editusetitle", "use_title");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " use_title " + " <true|false> ");
            return;
        }
        boolean bol;
        try {
            bol = Boolean.parseBoolean(args[1]);
        } catch (Exception e) {
            player.sendMessage(main.getPrefix() + "Please type boolean!. <true|false>");
            error(player);
            return;
        }
        success(player);
        if (bol) {
            player.sendMessage(main.getPrefix() + "The announcement is now using Title!");
            an.setUseTitle(true);
        } else {
            player.sendMessage(main.getPrefix() + "The announcement is now no longer using Title");
            an.setUseTitle(false);
        }
    }
}

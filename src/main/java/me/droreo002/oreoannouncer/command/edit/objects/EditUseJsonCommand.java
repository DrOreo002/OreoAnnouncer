package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.entity.Player;

public class EditUseJsonCommand extends EditCommand {

    public EditUseJsonCommand() {
        super("editusejson", "use_json");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " use_json " + " <true|false> ");
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
            player.sendMessage(main.getPrefix() + "The announcement is now using JSON!");
            an.setUseJson(true);
        } else {
            player.sendMessage(main.getPrefix() + "The announcement is now using plain text!");
            an.setUseJson(false);
        }
    }
}

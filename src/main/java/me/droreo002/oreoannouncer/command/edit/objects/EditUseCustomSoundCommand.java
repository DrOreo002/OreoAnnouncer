package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EditUseCustomSoundCommand extends EditCommand {

    public EditUseCustomSoundCommand() {
        super("editusecustomsound", "use_customsound");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " use_customsound " + " <true|false> ");
            return;
        }
        boolean bol;
        try {
            bol = Boolean.valueOf(args[1]);
        } catch (Exception e) {
            player.sendMessage(main.getPrefix() + "Please type boolean!. <true|false>");
            error(player);
            return;
        }
        if (bol) {
            player.sendMessage(main.getPrefix() + "Announcement is now using CustomSound!");
            an.setUseCustomSound(true);
        } else {
            player.sendMessage(main.getPrefix() + "Announcement is now no longer using CustomSound!");
            an.setUseCustomSound(false);
        }
    }
}

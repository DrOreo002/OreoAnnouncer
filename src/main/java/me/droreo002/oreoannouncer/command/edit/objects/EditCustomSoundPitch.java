package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EditCustomSoundPitch extends EditCommand {

    public EditCustomSoundPitch() {
        super("editcustomsoundpitch", "custom_sound_pitch");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " custom_sound_pitch" + " <integer>");
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
        an.setCustomSoundPitch(number);
        player.sendMessage(main.getPrefix() + "Costom sound pitch has been set to " + number + ". Preview will begin in 5 second!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            player.sendMessage(main.getPrefix() + "Playing...");
            an.playSound(player);
        }, 5L * 20L);
    }
}

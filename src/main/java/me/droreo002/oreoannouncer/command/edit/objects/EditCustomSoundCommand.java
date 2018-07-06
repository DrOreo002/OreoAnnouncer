package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EditCustomSoundCommand extends EditCommand {

    public EditCustomSoundCommand() {
        super("editcustomsound", "custom_sound");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " custom_sound " + " <sound> ");
            return;
        } else {
            Sound sound = null;
            try {
                sound = Sound.valueOf(args[1]);
            } catch (Exception e) {
                player.sendMessage(main.getPrefix() + "Your sound type is WRONG!");
                error(player);
                return;
            }
            an.setCustomSound(sound.toString());
            player.sendMessage(main.getPrefix() + "CustomSound type has been set to " + sound.toString() + ". Previewing in 5 second..");
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                player.sendMessage(main.getPrefix() + "Playing...");
                an.playSound(player);
            }, 5L * 20L);
            success(player);
        }
    }
}

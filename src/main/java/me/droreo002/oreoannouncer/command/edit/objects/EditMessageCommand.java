package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.entity.Player;

public class EditMessageCommand extends EditCommand {

    public EditMessageCommand() {
        super("editmessage","message");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            player.sendMessage(main.getPrefix() + color("This will edit the message on the data (not JSON). Please put the message.., example : &6/oan edit hello editmessage &7This is a test message!"));
            error(player);
        } else {
            StringBuilder build = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                build.append(args[i]).append(" ");
            }
            String msg = build.toString();
            an.setMessage(msg);
            player.sendMessage(main.getPrefix() + main.color("Announcement message has been set to " + msg));
            success(player);
        }
    }
}

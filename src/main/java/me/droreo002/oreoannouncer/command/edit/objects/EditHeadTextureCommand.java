package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.pagination.CustomItem;
import me.droreo002.pagination.outside_api.ItemBuilder;
import org.bukkit.entity.Player;

public class EditHeadTextureCommand extends EditCommand {

    public EditHeadTextureCommand() {
        super("editheadtexture", "gui_texture");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " gui_texture " + " <texture> ");
            return;
        } else {
            an.setHeadTexture(args[1]);
            player.sendMessage(main.getPrefix() + main.color("Announcement head texture has been set to " + args[1]));
            player.sendMessage(main.color("&fThe head icon will be given to your inventory. If its not working then your custom texture is wrong"));
            try {
                player.getInventory().addItem(new CustomItem("&7[ &cHead &7]", new String[] {"&fThis is ur head"}, args[1]));
            } catch (Exception e) {
                player.sendMessage(main.getPrefix() + "Error on getting the custom head. The custom head ID is prob wrong, please recheck");
                error(player);
                return;
            }
            success(player);
        }
    }
}

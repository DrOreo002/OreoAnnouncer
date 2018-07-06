package me.droreo002.oreoannouncer.command.edit.objects;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.pagination.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EditGuiMaterialCommand extends EditCommand {

    public EditGuiMaterialCommand() {
        super("editguimaterial", "gui_material");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            error(player);
            player.sendMessage(main.getPrefix() + "Usage : /oan editor " + an.getName() + " gui_material " + " <material> ");
            return;
        } else {
            Material mat = null;
            try {
                mat = Material.valueOf(args[1]);
            } catch (Exception e) {
                player.sendMessage(main.getPrefix() + "Your material type is WRONG!");
                error(player);
                return;
            }
            an.setMaterial(mat.toString());
            player.sendMessage(main.getPrefix() + "GUI Material has been set to " + mat.toString());
            success(player);
        }
    }
}

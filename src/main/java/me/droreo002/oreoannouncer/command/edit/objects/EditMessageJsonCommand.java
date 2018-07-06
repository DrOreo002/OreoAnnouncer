package me.droreo002.oreoannouncer.command.edit.objects;

import com.google.gson.JsonParser;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.command.edit.EditCommand;
import me.droreo002.oreoannouncer.object.Announcement;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
public class EditMessageJsonCommand extends EditCommand {

    public EditMessageJsonCommand() {
        super("editjson", "message_json");
        setNeedPermission(true);
        setPermission("oan.admin");
    }

    // TODO : Continue this
    @Override
    protected void execute(OreoAnnouncer main, Player player, String[] args, Announcement an) {
        if (args.length == 1) {
            player.sendMessage(main.getPrefix() + color("This will edit the json data. Will throw an error if the json is invalid. Usage : &6/oan editor <announcement> message_json <json>"));
            error(player);
        } else {
            StringBuilder build = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                build.append(args[i]).append(" ");
            }
            String msg = build.toString();
            if (!isJson(msg)) {
                player.sendMessage(main.getPrefix() + "That json is invalid!");
                error(player);
            } else {
                player.sendMessage(main.getPrefix() + "JSON Message has been set to : ");
                BaseComponent[] base = ComponentSerializer.parse(msg);
                player.spigot().sendMessage(base);
                success(player);
            }
        }
    }

    public boolean isJson(String json) {
        try {
            return new JsonParser().parse(json).getAsJsonObject() != null;
        } catch (Throwable ignored) {}
        return false;
    }
}

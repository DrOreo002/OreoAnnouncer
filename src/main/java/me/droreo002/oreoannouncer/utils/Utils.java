package me.droreo002.oreoannouncer.utils;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.manager.DataFile;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static void createAnnouncement(String name, Player player) {
        name = name.toLowerCase();
        DataFile file = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        file.setup(name);

        OreoAnnouncer.getInstance().getAnnouncerManager().add(name);
        player.sendMessage(OreoAnnouncer.getInstance().getPrefix() + "Created an announcement with the name of " + name);
    }

    public static void createAnnouncement(String name) {
        name = name.toLowerCase();
        DataFile file = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        file.setupFromOld(name);

        OreoAnnouncer.getInstance().getAnnouncerManager().add(name);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt((max - min) + 1) + min;
    }
}

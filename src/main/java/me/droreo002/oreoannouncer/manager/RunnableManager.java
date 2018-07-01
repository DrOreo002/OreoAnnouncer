package me.droreo002.oreoannouncer.manager;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RunnableManager implements Runnable {

    private OreoAnnouncer main;

    public RunnableManager(OreoAnnouncer main) {
        this.main = main;
    }

    @Override
    public void run() {
        if (!main.getAnnouncerManager().getAnnouncementMap().isEmpty()) {
            List<Announcement> all = new ArrayList<>(main.getAnnouncerManager().getAnnouncementMap().values());
            Announcement an = all.get(Utils.random(0, all.size() - 1));
            for (Player player : Bukkit.getOnlinePlayers()) {
                an.send(player);
            }
        } else {
            System.out.println("Announcement is empty!");
        }
    }
}

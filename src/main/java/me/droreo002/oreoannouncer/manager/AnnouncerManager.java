package me.droreo002.oreoannouncer.manager;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.object.Announcement;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AnnouncerManager {

    private final Map<String, Announcement> announcementMap = new HashMap<>();
    private static AnnouncerManager instance;
    private OreoAnnouncer main;

    private AnnouncerManager(OreoAnnouncer main) {
        this.main = main;
    }

    public static AnnouncerManager get(OreoAnnouncer main) {
        if (instance == null) {
            instance = new AnnouncerManager(main);
            return instance;
        }
        return instance;
    }

    public void loadAll() {
        File folder = new File(main.getDataFolder(), "data");
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String name = file.getName().replaceFirst("[.][^.]+$", "");
            add(name);
        }
    }

    public Announcement getAnnoucement(String name) {
        return announcementMap.get(name.toLowerCase());
    }

    public void add(String name) {
        synchronized (announcementMap) {
            announcementMap.computeIfAbsent(name, k -> new Announcement(name));
        }
    }

    public void reload(String name) {
        if (!announcementMap.containsKey(name.toLowerCase())) {
            return;
        }
        announcementMap.remove(name.toLowerCase());
        announcementMap.put(name.toLowerCase(), new Announcement(name));
    }

    public void reloadAll() {
        announcementMap.clear();
        loadAll();
    }

    public void remove(String name) {
        announcementMap.remove(name.toLowerCase());
    }

    public Map<String, Announcement> getAnnouncementMap() {
        return announcementMap;
    }
}

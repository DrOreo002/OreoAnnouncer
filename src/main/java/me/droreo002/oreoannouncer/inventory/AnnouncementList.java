package me.droreo002.oreoannouncer.inventory;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.manager.RunnableManager;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Utils;
import me.droreo002.pagination.*;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementList extends PaginatedInventory {

    private OreoAnnouncer main;
    private Player player;

    // TODO : Use PaginationOreo for the api
    public AnnouncementList(OreoAnnouncer main, Player player) {
        super(main.color("&7[ &cAnnouncement List &7]"), 36, Pagination.getInstance());
        this.main = main;
        this.player = player;
        setItemRowWithSkip(1,1, new CustomItem(Material.STAINED_GLASS_PANE, " ", 7));
        setBorder(0, new CustomItem(Material.STAINED_GLASS_PANE, " ", 7));
        setBorder(2, new CustomItem(Material.STAINED_GLASS_PANE, " ", 7));
        setSearchRow(3);
        setNormal(false);

        // Button adding
        List<Announcement> ann = new ArrayList<>(main.getAnnouncerManager().getAnnouncementMap().values());
        for (Announcement an : ann) {
            if (!an.isUseHeadIcon()) {
                addButton(new GUIButton(new CustomItem(an.getIcon(), "&7[ &f" + an.getName().toUpperCase() + " &7]", new String[] {"&r","&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER", "&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"}))
                .setAction(inventoryClickEvent -> {
                    if (inventoryClickEvent.getClick().equals(ClickType.LEFT)) {
                        closeInventory(player);
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            an.send(online);
                        }
                        player.sendMessage(main.getPrefix() + "Sended to all online player");
                        return;
                    }
                    if (inventoryClickEvent.getClick().equals(ClickType.RIGHT)) {
                        closeInventory(player);
                        openInventory(player, new EditorInventory(main, player, an).getInventory());
                    }
                }));
            } else {
                try {
                    addButton(new GUIButton(new CustomItem("&7[ &f" + an.getName().toUpperCase() + " &7]", new String[] {"&r", "&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER", "&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"}, an.getHeadIcon()))
                    .setAction(inventoryClickEvent -> {
                        if (inventoryClickEvent.getClick().equals(ClickType.LEFT)) {
                            closeInventory(player);
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                an.send(online);
                            }
                            player.sendMessage(main.getPrefix() + "Sended to all online player");
                            return;
                        }
                        if (inventoryClickEvent.getClick().equals(ClickType.RIGHT)) {
                            closeInventory(player);
                            openInventory(player, new EditorInventory(main, player, an).getInventory());
                        }
                    }));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on getting head icon for announcement " + an.getName() + ", while adding it on the AnnouncementList!. Please check the texture!");
                    addButton(new GUIButton(new CustomItem(an.getIcon(), "&7[ &f" + an.getName().toUpperCase() + " &7]", new String[] {"&r","&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER", "&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"}))
                            .setAction(inventoryClickEvent -> {
                                if (inventoryClickEvent.getClick().equals(ClickType.LEFT)) {
                                    closeInventory(player);
                                    for (Player online : Bukkit.getOnlinePlayers()) {
                                        an.send(online);
                                    }
                                    player.sendMessage(main.getPrefix() + "Sended to all online player");
                                    return;
                                }
                                if (inventoryClickEvent.getClick().equals(ClickType.RIGHT)) {
                                    closeInventory(player);
                                    openInventory(player, new EditorInventory(main, player, an).getInventory());
                                }
                            }));
                }
            }
        }
    }
}

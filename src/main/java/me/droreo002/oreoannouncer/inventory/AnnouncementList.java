package me.droreo002.oreoannouncer.inventory;

import com.gamerbah.inventorytoolkit.ClickEvent;
import com.gamerbah.inventorytoolkit.GameInventory;
import com.gamerbah.inventorytoolkit.ItemBuilder;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementList extends GameInventory {

    private OreoAnnouncer main;
    private Player player;

    public AnnouncementList(OreoAnnouncer main, Player player) {
        super(main.color("&7[ &cAnnouncement List &7]"), 36);
        this.main = main;
        this.player = player;
        setAllowCreative(true);
        setSearchRows(1,1);
        addBorder(0, DyeColor.GRAY);
        setSearchOffset(1);
        addBorder(2, DyeColor.GRAY);
        addBorder(3, DyeColor.GRAY);
        addButton(9, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(7));
        addButton(17, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(7));
        addButton(31, new ItemBuilder(Material.WOOD_DOOR).name(main.color("&7[ &cExit &7")).lore(main.color("&6CLICK TO CLOSE")));
        setShowPageNumbers(true);
        setPageRow(3);
        // TODO : Continue, see his discord for the damn updates

        // Button adding
        List<Announcement> ann = new ArrayList<>(main.getAnnouncerManager().getAnnouncementMap().values());
        for (Announcement an : ann) {
            if (!an.isUseHeadIcon()) {
                addItem(new ItemBuilder(an.getIcon()).name(main.color("&7[ &f" + an.getName().toUpperCase() + " &7]")).lore(main.color("&r")).lore(main.color("&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER"))
                        .lore(main.color("&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"))
                .onClick(new ClickEvent(() -> {
                    closeInventory(player);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        an.send(online);
                    }
                    player.sendMessage(main.getPrefix() + "Sended to all online player");
                }, ClickEvent.Type.LEFT))
                .onClick(new ClickEvent(() -> {
                    closeInventory(player);
                    openInventory(player, new EditorInventory(main, player, an).getInventory());
                }, ClickEvent.Type.RIGHT)));
            } else {
                try {
                    addItem(new ItemBuilder(Utils.getSkull(an.getHeadIcon())).name(main.color("&7[ &f" + an.getName().toUpperCase() + " &7]")).lore(main.color("&r")).lore(main.color("&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER"))
                            .lore(main.color("&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"))
                            .onClick(new ClickEvent(() -> {
                                closeInventory(player);
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    an.send(online);
                                }
                                player.sendMessage(main.getPrefix() + "Sended to all online player");
                            }, ClickEvent.Type.LEFT))
                            .onClick(new ClickEvent(() -> {
                                closeInventory(player);
                                openInventory(player, new EditorInventory(main, player, an).getInventory());
                            }, ClickEvent.Type.RIGHT)));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error on getting head icon for announcement " + an.getName() + ", while adding it on the AnnouncementList!. Please check the texture!");
                    addItem(new ItemBuilder(an.getIcon()).name(main.color("&7[ &f" + an.getName().toUpperCase() + " &7]")).lore(main.color("&r")).lore(main.color("&6LEFT CLICK TO SEND THIS ANNOUNCEMENT TO ALL PLAYER"))
                            .lore(main.color("&6RIGHT CLICK TO EDIT THIS ANNOUNCEMENT"))
                            .onClick(new ClickEvent(() -> {
                                closeInventory(player);
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    an.send(online);
                                }
                                player.sendMessage(main.getPrefix() + "Sended to all online player");
                            }, ClickEvent.Type.LEFT))
                            .onClick(new ClickEvent(() -> {
                                closeInventory(player);
                                openInventory(player, new EditorInventory(main, player, an).getInventory());
                            }, ClickEvent.Type.RIGHT)));
                }
            }
        }
    }

    private void closeInventory(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OreoAnnouncer.getInstance(), player::closeInventory, 1L);
    }

    private void openInventory(Player player, Inventory inventory) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OreoAnnouncer.getInstance(), () -> player.openInventory(inventory), 1L);
    }
}

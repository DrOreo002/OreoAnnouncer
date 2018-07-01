package me.droreo002.oreoannouncer.inventory;

import com.gamerbah.inventorytoolkit.ItemBuilder;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.inventory.api.CustomInventory;
import me.droreo002.oreoannouncer.object.Announcement;
import me.droreo002.oreoannouncer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditorInventory extends CustomInventory {

    private OreoAnnouncer main;
    private Player player;
    private Announcement announcement;

    public EditorInventory(OreoAnnouncer main, Player player, Announcement announcement) {
        this.main = main;
        this.player = player;
        this.announcement = announcement;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return;
        String name = meta.getDisplayName();

        // Disable useTotem
        if (name.equals(main.color("&7[ &6useTotem &7| &atrue &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Disabled");
            announcement.setUseTotemAnimation(false);
            return;
        }
        if (name.equals(main.color("&7[ &6useTotem &7| &cfalse &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Enabled");
            announcement.setUseTotemAnimation(true);
            return;
        }
        if (name.equals(main.color("&7[ &6useCenteredText &7| &atrue &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Disabled");
            announcement.setUseCenteredMessage(false);
            return;
        }
        if (name.equals(main.color("&7[ &6useCenteredText &7| &cfalse &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Enabled");
            announcement.setUseCenteredMessage(true);
            return;
        }
        if (name.equals(main.color("&7[ &6useTitle &7| &atrue &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Disabled");
            announcement.setUseTitle(false);
            return;
        }
        if (name.equals(main.color("&7[ &6useTitle &7| &cfalse &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Enabled");
            announcement.setUseTitle(true);
            return;
        }
        if (name.equals(main.color("&7[ &6useCustomSound &7| &atrue &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Disabled");
            announcement.setUseCustomSound(false);
            return;
        }
        if (name.equals(main.color("&7[ &6useCustomSound &7| &afalse &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Enabled");
            announcement.setEnabled(true);
            return;
        }
        if (name.equals(main.color("&7[ &6isEnabled &7| &atrue &7]"))) {
            closeInventory(player);
            player.sendMessage(main.getPrefix() + "Disabled");
            announcement.setEnabled(false);
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        ((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 100, 0);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        ((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 100, 0);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, main.color("&7[ &cSetting Inventory &7]"));
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(7));
        }
        inventory.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(3));
        inventory.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(3));
        inventory.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(3));

        ItemStack toggle;
        ItemStack toggle_sound;
        ItemStack toggle_totem;
        ItemStack toggle_title;
        ItemStack toggle_centered_text;
        if (announcement.isUseTotemAnimation()) {
            toggle_totem = new ItemBuilder(Material.TOTEM)
                    .name(main.color("&7[ &6useTotem &7| &atrue &7]"))
                    .lore(main.color("&r"))
                    .lore(main.color("&7Click to &cDisable &7totem animation!"));
        } else {
            toggle_totem = new ItemBuilder(Material.TOTEM)
                    .name(main.color("&7[ &6useTotem &7| &cfalse &7]"))
                    .lore(main.color("&r"))
                    .lore(main.color("&7Click to &aEnable &7totem animation!"));
        }
        try {
            if (announcement.isUseCenteredMessage()) {
                toggle_centered_text = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTIxZjgzYTJmNzIxOTAzMjRjNDZiNzk2ZWQ5YmM3N2MxYTExMzgyZjU1OTAzMDhlNDQyNTUxMWFiOTI4MjE2MyJ9fX0="))
                        .name(main.color("&7[ &6useCenteredText &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7centered text feature!"));
            } else {
                toggle_centered_text = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTIxZjgzYTJmNzIxOTAzMjRjNDZiNzk2ZWQ5YmM3N2MxYTExMzgyZjU1OTAzMDhlNDQyNTUxMWFiOTI4MjE2MyJ9fX0="))
                        .name(main.color("&7[ &6useCenteredText &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7centered text feature!"));
            }
            if (announcement.isUseTitle()) {
                toggle_title = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ5YzYzYmM1MDg3MjMzMjhhMTllNTk3ZjQwODYyZDI3YWQ1YzFkNTQ1NjYzYWMyNDQ2NjU4MmY1NjhkOSJ9fX0="))
                        .name(main.color("&7[ &6useTitle &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7using title!"));
            } else {
                toggle_title = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxOTVmOWE0MzljNmQwZmZkMTk2MTY1N2Y2ZjBhYThlM2EyZjhhMjQ5M2FmYTY2MmFiNWU0MTkzZTAifX19"))
                        .name(main.color("&7[ &6useTitle &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7using title!"));
            }
            if (announcement.isUseCustomSound()) {
                toggle_sound = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlZWI3N2Q0ZDI1NzI0YTljYWYyYzdjZGYyZDg4Mzk5YjE0MTdjNmI5ZmY1MjEzNjU5YjY1M2JlNDM3NmUzIn19fQ==\\"))
                        .name(main.color("&7[ &6useCustomSound &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7custom sound!"));
            } else {
                toggle_sound = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlZWI3N2Q0ZDI1NzI0YTljYWYyYzdjZGYyZDg4Mzk5YjE0MTdjNmI5ZmY1MjEzNjU5YjY1M2JlNDM3NmUzIn19fQ==\\"))
                        .name(main.color("&7[ &6useCustomSound &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7custom sound!"));
            }
            if (announcement.isEnabled()) {
                toggle = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc0NzJkNjA4ODIxZjQ1YTg4MDUzNzZlYzBjNmZmY2I3ODExNzgyOWVhNWY5NjAwNDFjMmEwOWQxMGUwNGNiNCJ9fX0=\\"))
                        .name(main.color("&7[ &6isEnabled &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7this announcement!"));
            } else {
                toggle = new ItemBuilder(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk1M2IxMmEwOTQ2YjYyOWI0YzA4ODlkNDFmZDI2ZWQyNmZiNzI5ZDRkNTE0YjU5NzI3MTI0YzM3YmI3MGQ4ZCJ9fX0=\\"))
                        .name(main.color("&7[ &6isEnabled &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7this announcement!"));
            }
        } catch (Exception e) {
            if (announcement.isEnabled()) {
                toggle = new ItemBuilder(Material.WOOL)
                        .name(main.color("&7[ &6isEnabled &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7this announcement!"))
                        .durability(5);
            } else {
                toggle = new ItemBuilder(Material.WOOL)
                        .name(main.color("&7[ &6isEnabled &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7this announcement!"))
                        .durability(14);
            }
            if (announcement.isUseCustomSound()) {
                toggle_sound = new ItemBuilder(Material.NOTE_BLOCK)
                        .name(main.color("&7[ &6useCustomSound &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7custom sound!"));
            } else {
                toggle_sound = new ItemBuilder(Material.NOTE_BLOCK)
                        .name(main.color("&7[ &6useCustomSound &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7custom sound!"));
            }
            if (announcement.isUseTitle()) {
                toggle_title = new ItemBuilder(Material.NAME_TAG)
                        .name(main.color("&7[ &6useTitle &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7using title!"));
            } else {
                toggle_title = new ItemBuilder(Material.NAME_TAG)
                        .name(main.color("&7[ &6useTitle &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7using title!"));
            }
            if (announcement.isUseCenteredMessage()) {
                toggle_centered_text = new ItemBuilder(Material.TNT)
                        .name(main.color("&7[ &6useCenteredText &7| &atrue &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &cDisable &7centered text feature!"));
            } else {
                toggle_centered_text = new ItemBuilder(Material.TNT)
                        .name(main.color("&7[ &6useCenteredText &7| &cfalse &7]"))
                        .lore(main.color("&r"))
                        .lore(main.color("&7Click to &aEnable &7centered text feature!"));
            }
        }
        inventory.setItem(9, toggle);
        inventory.setItem(11, toggle_sound);
        inventory.setItem(15, toggle_totem);
        inventory.setItem(17, toggle_title);
        inventory.setItem(13, toggle_centered_text);
        return inventory;
    }
}

package me.droreo002.oreoannouncer.inventory.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        InventoryView view = e.getView();
        Inventory inven = view.getTopInventory();
        ItemStack item = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        if (item == null || item.getType().equals(Material.AIR)) return;
        if (inven == null) return;
        if (inven.getHolder() instanceof CustomInventory) {
            if (view.getBottomInventory().getType().equals(InventoryType.PLAYER)) {
                e.setCancelled(true);
            }
            e.setCancelled(true);
            CustomInventory custom = (CustomInventory) inven.getHolder();
            custom.onClick(e);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof CustomInventory) {
            CustomInventory custom = (CustomInventory) e.getInventory().getHolder();
            custom.onOpen(e);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof CustomInventory) {
            Player player = (Player) e.getPlayer();
            if (player.getItemOnCursor() != null && player.isSneaking()) {
                player.setItemOnCursor(new ItemStack(Material.AIR));
            }
            CustomInventory custom = (CustomInventory) e.getInventory().getHolder();
            custom.onClose(e);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(InventoryMoveItemEvent e) {
        Inventory first = e.getSource();
        if (first.getHolder() instanceof CustomInventory) {
            e.setCancelled(true);
        }
    }
}

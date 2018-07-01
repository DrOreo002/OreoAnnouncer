package me.droreo002.oreoannouncer.inventory.api;

import me.droreo002.oreoannouncer.OreoAnnouncer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class CustomInventory implements InventoryHolder {

    public void onClick(InventoryClickEvent e) {}
    public void onClose(InventoryCloseEvent e) {}
    public void onOpen(InventoryOpenEvent e) {}

    public void closeInventory(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OreoAnnouncer.getInstance(), player::closeInventory, 1L);
    }

    public void openInventory(Player player, Inventory inventory) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OreoAnnouncer.getInstance(), () -> player.openInventory(inventory), 1L);
    }
}

package me.droreo002.oreoannouncer.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import me.droreo002.oreoannouncer.OreoAnnouncer;
import me.droreo002.oreoannouncer.manager.DataFile;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static void createAnnouncement(String name, Player player) {
        name = name.toLowerCase();
        DataFile file = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        file.setup(name);

        OreoAnnouncer.getInstance().getAnnouncerManager().add(name);
        player.sendMessage(OreoAnnouncer.getInstance().getPrefix() + "Created an announcement with the name of " + name);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
    }

    public static void deleteAnnouncement(String name, Player player) {
        DataFile data = DataFile.getConfig(OreoAnnouncer.getInstance(), name);
        data.getFile().delete();
        DataFile.remove(name);

        OreoAnnouncer.getInstance().getAnnouncerManager().remove(name);
        player.sendMessage(OreoAnnouncer.getInstance().getPrefix() + "Announcement with the name of " + name + ", is now deleted.");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.2f);
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

    public static ItemStack getSkull(final String texture) throws Exception {
        final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta meta = item.getItemMeta();
        final Object skin = createGameProfile(texture, UUID.randomUUID());
        setField(meta, "profile", skin);
        item.setItemMeta(meta);
        return item;
    }

    private static Field getField(final Class<?> c, final String field) throws Exception {
        return c.getDeclaredField(field);
    }

    private static GameProfile createGameProfile(final String texture, final UUID id) {
        final GameProfile profile = new GameProfile(id, (String)null);
        final PropertyMap propertyMap = profile.getProperties();
        propertyMap.put("textures", new Property("textures", texture));
        return profile;
    }

    private static String getTextureValue(final String url) {
        return new String(Base64.encodeBase64(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes()), Charsets.UTF_8);
    }

    private static void setField(final Object obj, final String field, final Object value) throws Exception {
        final Field f = getField(obj.getClass(), field);
        f.setAccessible(true);
        f.set(obj, value);
    }
}

package net.okuri.qol;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperResource;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class PDCC {
    @Deprecated
    public static <T> T get(ItemStack item, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        if (!has(item, key)) throw new IllegalArgumentException("ItemStack does not have the key");
        return item.getItemMeta().getPersistentDataContainer().get(key.key, type);
    }

    public static <T> T get(ItemMeta meta, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        if (!has(meta, key)) throw new IllegalArgumentException("ItemMeta does not have the key");
        return meta.getPersistentDataContainer().get(key.key, type);
    }

    public static <T> T get(TileState tile, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.BLOCK) throw new IllegalArgumentException("PDCKey is not for TileState");
        if (!has(tile, key)) throw new IllegalArgumentException("TileState does not have the key");
        return tile.getPersistentDataContainer().get(key.key, type);
    }

    public static <T> T get(Player player, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.PLAYER) throw new IllegalArgumentException("PDCKey is not for Player");
        if (!has(player, key)) throw new IllegalArgumentException("Player does not have the key");
        return player.getPersistentDataContainer().get(key.key, type);
    }

    @Deprecated
    public static <T> boolean has(ItemStack item, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        return item.getItemMeta().getPersistentDataContainer().has(key.key, type);
    }

    public static <T> boolean has(ItemMeta meta, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        return meta.getPersistentDataContainer().has(key.key, type);
    }

    public static <T> boolean has(TileState tile, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.BLOCK) throw new IllegalArgumentException("PDCKey is not for TileState");
        return tile.getPersistentDataContainer().has(key.key, type);
    }

    public static <T> boolean has(Player player, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.PLAYER) throw new IllegalArgumentException("PDCKey is not for Player");
        return player.getPersistentDataContainer().has(key.key, type);
    }

    // 注意! ItemStackのやつを用いる時は、このあとにItemMetaを取得すること!予期せぬバグを防ぐため!
    // ItemMetaに統一することを推奨
    @Deprecated
    public static <T> void set(ItemStack item, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key.key, type, value);
        item.setItemMeta(meta);
    }

    public static <T> void set(ItemMeta meta, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        meta.getPersistentDataContainer().set(key.key, type, value);
    }

    public static <T> void set(TileState tile, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.BLOCK) throw new IllegalArgumentException("PDCKey is not for TileState");
        tile.getPersistentDataContainer().set(key.key, type, value);
        tile.update();
    }

    public static <T> void set(Player player, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.PLAYER) throw new IllegalArgumentException("PDCKey is not for Player");
        player.getPersistentDataContainer().set(key.key, type, value);
    }

    @Deprecated
    public static <T> void remove(ItemStack item, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(key.key);
        item.setItemMeta(meta);
    }

    public static <T> void remove(ItemMeta meta, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        meta.getPersistentDataContainer().remove(key.key);
    }

    public static <T> void remove(TileState tile, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.BLOCK) throw new IllegalArgumentException("PDCKey is not for TileState");
        tile.getPersistentDataContainer().remove(key.key);
        tile.update();
    }

    public static <T> void remove(Player player, PDCKey key) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.PLAYER) throw new IllegalArgumentException("PDCKey is not for Player");
        player.getPersistentDataContainer().remove(key.key);
    }

    public static <T> void setAll(ItemStack item, Map<PDCKey, T> map) {
        ItemMeta meta = item.getItemMeta();
        for (Map.Entry<PDCKey, T> entry : map.entrySet()) {
            meta.getPersistentDataContainer().set(entry.getKey().key, entry.getKey().type, entry.getValue());
        }
        item.setItemMeta(meta);
    }

    public static <T> void setAll(TileState tile, Map<PDCKey, T> map) {
        for (Map.Entry<PDCKey, T> entry : map.entrySet()) {
            tile.getPersistentDataContainer().set(entry.getKey().key, entry.getKey().type, entry.getValue());
        }
        tile.update();
    }

    public static <T> void setAll(Player player, Map<PDCKey, T> map) {
        for (Map.Entry<PDCKey, T> entry : map.entrySet()) {
            player.getPersistentDataContainer().set(entry.getKey().key, entry.getKey().type, entry.getValue());
        }
    }

    public static <T> void setLiquor(ItemMeta meta, SuperItemType type, double alcoholAmount, double alcoholPer, double x, double y, double z, double divLine, double quality, int rarity, double temp, double humid, double maturation) {
        meta.getPersistentDataContainer().set(PDCKey.TYPE.key, PDCKey.TYPE.type, type.toString());
        meta.getPersistentDataContainer().set(PDCKey.ALCOHOL.key, PDCKey.ALCOHOL.type, true);
        meta.getPersistentDataContainer().set(PDCKey.ALCOHOL_AMOUNT.key, PDCKey.ALCOHOL_AMOUNT.type, alcoholAmount);
        meta.getPersistentDataContainer().set(PDCKey.ALCOHOL_PERCENTAGE.key, PDCKey.ALCOHOL_PERCENTAGE.type, alcoholPer);
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, x);
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, y);
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, z);
        meta.getPersistentDataContainer().set(PDCKey.DIVLINE.key, PDCKey.DIVLINE.type, divLine);
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, quality);
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, rarity);
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, temp);
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, humid);
        meta.getPersistentDataContainer().set(PDCKey.MATURATION.key, PDCKey.MATURATION.type, maturation);
    }

    public static <T> void setSuperItem(ItemMeta meta, SuperItemType type, double x, double y, double z, double quality, int rarity, double temp, double humid) {
        meta.getPersistentDataContainer().set(PDCKey.TYPE.key, PDCKey.TYPE.type, type.toString());
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, x);
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, y);
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, z);
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, quality);
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, rarity);
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, temp);
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, humid);
    }
    public static <T> void setSuperItem(ItemMeta meta, SuperResource r){
        meta.getPersistentDataContainer().set(PDCKey.TYPE.key, PDCKey.TYPE.type, r.getSuperItemType().toString());
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, r.getX());
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, r.getY());
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, r.getZ());
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, r.getQuality());
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, r.getRarity());
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, r.getTemp());
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, r.getHumid());
    }
}

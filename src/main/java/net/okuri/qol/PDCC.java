package net.okuri.qol;

import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.adapter.AdapterID;
import net.okuri.qol.superItems.factory.resources.SuperResource;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (key.isProtected) {
            setProtectedKey(pdc, key, value);
        } else {
            pdc.set(key.key, type, value);
        }
        item.setItemMeta(meta);
    }

    public static <T> void set(ItemMeta meta, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.ITEM) throw new IllegalArgumentException("PDCKey is not for ItemStack");
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (key.isProtected) {
            setProtectedKey(pdc, key, value);
        } else {
            pdc.set(key.key, type, value);
        }
    }

    public static <T> void set(TileState tile, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.BLOCK) throw new IllegalArgumentException("PDCKey is not for TileState");
        PersistentDataContainer pdc = tile.getPersistentDataContainer();
        if (key.isProtected) {
            setProtectedKey(pdc, key, value);
        } else {
            pdc.set(key.key, type, value);
        }
        tile.update();
    }

    public static <T> void set(Player player, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        if (key.apply != PDCKey.ApplyType.PLAYER) throw new IllegalArgumentException("PDCKey is not for Player");
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (key.isProtected) {
            setProtectedKey(pdc, key, value);
        } else {
            pdc.set(key.key, type, value);
        }
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

    private static <T> void setProtectedKey(PersistentDataContainer pdc, PDCKey key, T value) {
        PersistentDataType<?, T> type = key.type;
        // すでに設定されている場合、上書きしない
        if (pdc.has(key.key, type)) throw new IllegalArgumentException("This key is protected.");
        pdc.set(key.key, type, value);
    }

    public static void setProducerInfo(ItemMeta meta, ProducerInfo info) {
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(PDCKey.PRODUCER_INFO.key, PDCKey.PRODUCER_INFO.type, getProducerContainer(pdc, info));

    }

    private static PersistentDataContainer getProducerContainer(PersistentDataContainer parentPdc, ProducerInfo info) {
        PersistentDataContainer pdc = parentPdc.getAdapterContext().newPersistentDataContainer();
        pdc.set(PDCKey.PRODUCER_INFO_ID.key, PDCKey.PRODUCER_INFO_ID.type, info.getPlayerID());
        pdc.set(PDCKey.PRODUCER_INFO_QUALITY.key, PDCKey.PRODUCER_INFO_QUALITY.type, info.getPlayerQuality());
        pdc.set(PDCKey.PRODUCER_INFO_TYPE.key, PDCKey.PRODUCER_INFO_TYPE.type, info.getItemData().getType().getStringType());
        ArrayList<ProducerInfo> children = info.getChildren();
        PersistentDataContainer[] childrenPdc = new PersistentDataContainer[children.size()];
        for (int i = 0; i < children.size(); i++) {
            childrenPdc[i] = getProducerContainer(pdc, children.get(i));
        }
        pdc.set(PDCKey.PRODUCER_CHILDREN.key, PDCKey.PRODUCER_CHILDREN.type, childrenPdc);
        return pdc;
    }

    public static ProducerInfo getProducerInfo(ItemMeta meta) {
        assert has(meta, PDCKey.PRODUCER_INFO);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        PersistentDataContainer producerInfoPdc = (PersistentDataContainer) pdc.get(PDCKey.PRODUCER_INFO.key, PDCKey.PRODUCER_INFO.type);
        return getProducerInfo(producerInfoPdc);
    }

    private static ProducerInfo getProducerInfo(PersistentDataContainer pdc) {
        String playerID = (String) pdc.get(PDCKey.PRODUCER_INFO_ID.key, PDCKey.PRODUCER_INFO_ID.type);
        double playerQuality = (double) pdc.get(PDCKey.PRODUCER_INFO_QUALITY.key, PDCKey.PRODUCER_INFO_QUALITY.type);
        SuperItemType itemType = SuperItemType.valueOf((String) pdc.get(PDCKey.PRODUCER_INFO_TYPE.key, PDCKey.PRODUCER_INFO_TYPE.type));
        SuperItemData data = new SuperItemData(itemType);
        ProducerInfo info = new ProducerInfo(playerID, playerQuality, data);
        PersistentDataContainer[] childrenPdc = (PersistentDataContainer[]) pdc.get(PDCKey.PRODUCER_CHILDREN.key, PDCKey.PRODUCER_CHILDREN.type);
        if (childrenPdc != null) {
            for (PersistentDataContainer childPdc : childrenPdc) {
                if (childPdc == null) continue;
                info.addChild(getProducerInfo(childPdc));
            }
        }
        return info;
    }


    public static <T> void setLiquor(ItemMeta meta, double alcoholAmount, double alcoholPer, double x, double y, double z, double divLine, double quality, int rarity, double temp, double humid, double maturation) {
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

    public static <T> void setSuperItem(ItemMeta meta, double x, double y, double z, double quality, int rarity, double temp, double humid) {
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, x);
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, y);
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, z);
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, quality);
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, rarity);
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, temp);
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, humid);
    }

    public static <T> void setSuperResource(ItemMeta meta, SuperResource r) {
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, r.getX());
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, r.getY());
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, r.getZ());
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, r.getQuality());
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, r.getRarity());
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, r.getTemp());
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, r.getHumid());
        meta.getPersistentDataContainer().set(PDCKey.BIOME_ID.key, PDCKey.BIOME_ID.type, r.getBiomeId());
        meta.getPersistentDataContainer().set(PDCKey.PRODUCER.key, PDCKey.PRODUCER.type, r.getProducerName());
    }

    public static SuperItemData getItemData(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        SuperItemType type;
        if (has(meta, PDCKey.TYPE)) {
            type = SuperItemType.valueOf(get(meta, PDCKey.TYPE));
            if (!(type == SuperItemType.DEFAULT)) {
                return new SuperItemData(type);
            }
        }
        type = SuperItemType.DEFAULT;
        return new SuperItemData(stack.getType());
    }

    public static ArrayList<AdapterID> getAdapters(ItemMeta meta) {
        ArrayList<AdapterID> result = new ArrayList<>();
        if (has(meta, PDCKey.ADAPTERS)) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            ArrayList<Integer> ids = (ArrayList<Integer>) pdc.get(PDCKey.ADAPTERS.key, PDCKey.ADAPTERS.type);
            for (int id : ids) {
                result.add(AdapterID.getAdapterID(id));
            }
        }
        return result;
    }

    public static void setAdapters(ItemMeta meta, ArrayList<AdapterID> adapters) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (AdapterID adapter : adapters) {
            ids.add(adapter.getID());
        }
        meta.getPersistentDataContainer().set(PDCKey.ADAPTERS.key, PDCKey.ADAPTERS.type, ids);
    }
}

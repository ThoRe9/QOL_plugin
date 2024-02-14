package net.okuri.qol;

import net.okuri.qol.alcohol.LiquorIngredient;
import net.okuri.qol.alcohol.resources.LiquorResource;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.add;

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

    public static <T> void setSuperItem(ItemMeta meta, double x, double y, double z, double quality, int rarity, double temp, double humid) {
        meta.getPersistentDataContainer().set(PDCKey.X.key, PDCKey.X.type, x);
        meta.getPersistentDataContainer().set(PDCKey.Y.key, PDCKey.Y.type, y);
        meta.getPersistentDataContainer().set(PDCKey.Z.key, PDCKey.Z.type, z);
        meta.getPersistentDataContainer().set(PDCKey.QUALITY.key, PDCKey.QUALITY.type, quality);
        meta.getPersistentDataContainer().set(PDCKey.RARITY.key, PDCKey.RARITY.type, rarity);
        meta.getPersistentDataContainer().set(PDCKey.TEMP.key, PDCKey.TEMP.type, temp);
        meta.getPersistentDataContainer().set(PDCKey.HUMID.key, PDCKey.HUMID.type, humid);
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

    public static Map<Taste, Double> getTastes(ItemMeta meta) {
        Map<Taste, Double> result = new HashMap<>();
        if (has(meta, PDCKey.TASTES)) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            for (PersistentDataContainer p : (PersistentDataContainer[]) pdc.get(PDCKey.TASTES.key, PDCKey.TASTES.type)) {
                result.put(TasteController.getController().getTaste((String) p.get(PDCKey.TASTE_ID.key, PDCKey.TASTE_ID.type)),
                        (Double) p.get(PDCKey.TASTE_PARAM.key, PDCKey.TASTE_PARAM.type));
            }
        }
        return result;
    }

    public static void setTastes(ItemMeta meta, Map<Taste, Double> set) {
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        PersistentDataContainer[] tastes = new PersistentDataContainer[0];
        for (Map.Entry<Taste, Double> entry : set.entrySet()) {
            PersistentDataContainer taste = pdc.getAdapterContext().newPersistentDataContainer();
            taste.set(PDCKey.TASTE_ID.key, PDCKey.TASTE_ID.type, entry.getKey().getID());
            taste.set(PDCKey.TASTE_PARAM.key, PDCKey.TASTE_PARAM.type, entry.getValue());
            tastes = add(tastes, taste);
        }
        pdc.set(PDCKey.TASTES.key, PDCKey.TASTES.type, tastes);
    }

    public static int[] getPosition(ItemMeta meta) {
        int[] pos = get(meta, PDCKey.RESOURCE_POS);
        return pos;
    }

    public static void setPosition(ItemMeta meta, int x, int y, int z) {
        int[] pos = {x, y, z};
        set(meta, PDCKey.RESOURCE_POS, pos);
    }

    public static void setLiquorResource(ItemMeta meta, LiquorResource resource) {
        setPosition(meta, resource.getPosX(), resource.getPosY(), resource.getPosZ());
        setTastes(meta, resource.getTastes());
        set(meta, PDCKey.TEMP, resource.getTemp());
        set(meta, PDCKey.HUMID, resource.getHumid());
        set(meta, PDCKey.BIOME_ID, resource.getBiomeId());
        set(meta, PDCKey.DELICACY, resource.getDelicacy());
        set(meta, PDCKey.LIQUOR_EFFECT_RATIO, resource.getEffectRate());
    }

    public static void setLiquorIngredient(ItemMeta meta, LiquorIngredient ingredient) {
        set(meta, PDCKey.LIQUOR_AMOUNT, ingredient.getLiquorAmount());
        set(meta, PDCKey.ALCOHOL_AMOUNT, ingredient.getAlcoholAmount());
        setTastes(meta, ingredient.getTastes());
        set(meta, PDCKey.DELICACY, ingredient.getDelicacy());
        set(meta, PDCKey.FERMENTATION_DEGREE, ingredient.getFermentationDegree());
        set(meta, PDCKey.INGREDIENT_COUNT, ingredient.getIngredientCount());
        set(meta, PDCKey.LIQUOR_EFFECT_RATIO, ingredient.getEffectRate());
        set(meta, PDCKey.ALCOHOL, true);
    }
}

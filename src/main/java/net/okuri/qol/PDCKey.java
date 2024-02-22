package net.okuri.qol;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum PDCKey {
    // ここにPDCのキーを列挙する

    TYPE("super_item_type", PersistentDataType.STRING, PDCKey.ApplyType.ITEM, true),
    // TYPE: SuperItemTypeを記憶する
    PROTECTED("qol_protected", PersistentDataType.BOOLEAN, PDCKey.ApplyType.BLOCK),
    // PROTECTED: 保護されているかどうかを記憶する
    ALCOHOL_LEVEL("alcohol_level", PersistentDataType.DOUBLE, PDCKey.ApplyType.PLAYER),
    // ALCOHOL_LEVEL: プレイヤーの血中アルコール濃度を記憶する
    LIQUOR_AMOUNT("liquor_amount", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // LIQUOR_AMOUNT: アルコール飲料の量を記憶する
    ALCOHOL_AMOUNT("alcohol_amount", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // ALCOHOL_AMOUNT: アルコール飲料のアルコール量(L)を記憶する
    ALCOHOL("alcohol", PersistentDataType.BOOLEAN, PDCKey.ApplyType.ITEM),
    // ALCOHOL: アルコール飲料かどうかを記憶する
    CONSUMABLE("consumable", PersistentDataType.BOOLEAN, PDCKey.ApplyType.ITEM),
    // CONSUMABLE: 飲食可能かどうかを記憶する。ふつう食糧と扱われるアイテム用。falseの場合は飲食できない。
    X("x", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // X: パラメータ計算用(1)
    Y("y", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // Y: パラメータ計算用(2)
    Z("z", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // Z: パラメータ計算用(3)
    TEMP("temp", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // TEMP: 生産地の気温を記憶する
    HUMID("humid", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // HUMID: 生産地の湿度を記憶する
    BIOME_ID("biome_id", PersistentDataType.INTEGER, PDCKey.ApplyType.ITEM),
    // BIOME_ID: 生産地のバイオームIDを記憶する
    QUALITY("quality", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // QUALITY: 品質。生産者の技量によって決まる
    RARITY("rarity", PersistentDataType.INTEGER, PDCKey.ApplyType.ITEM),
    // RARITY: 希少性。乱数によって決まる
    RICE_POLISHING_RATIO("rice_polishing_ratio", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // RICE_POLISHING_RATIO: 精米歩合。
    FARMER_TOOL("farmer_tool", PersistentDataType.BOOLEAN, ApplyType.ITEM),
    // FARMER_TOOL: 農業ツールとして登録されているか。
    MINER_TOOL("miner_tool", PersistentDataType.BOOLEAN, ApplyType.ITEM),
    // MINER_TOOL: 採掘ツールとして登録されているか。
    HAS_ALC_BAR("has_alc_bar", PersistentDataType.BOOLEAN, ApplyType.PLAYER),
    // HAS_ALC_BAR: アルコールのボスバー表示をonにしているかどうか。
    MATURATION_TOOL_AMOUNT("maturation_tool_amount", PersistentDataType.INTEGER, ApplyType.ITEM),
    // MATURATION_TOOL_AMOUNT: maturation toolの進める日数。
    RESOURCE_POS("resource_pos", PersistentDataType.INTEGER_ARRAY, ApplyType.ITEM),
    // RESOURCE_POS: リソースが生まれた位置を記憶する(x,y,z)
    TASTE_ID("taste_id", PersistentDataType.STRING, ApplyType.ITEM),
    // TASTE_ID: 味のID
    TASTE_PARAM("taste_param", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // TASTE_PARAM: 味のパラメータ
    TASTES("tastes", PersistentDataType.TAG_CONTAINER_ARRAY, ApplyType.ITEM),
    // TASTES: 味の情報
    DELICACY("delicacy", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // DELICACY: 味の癖のつよさ。
    FERMENTATION_DEGREE("fermentation_degree", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // FERMENTATION_DEGREE: 発酵度
    FERMENTATION_RATE("fermentation_rate", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // FERMENTATION_RATE: 発酵速度の倍率
    FERMENTATION_ALC_RATE("fermentation_alc_rate", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // FERMENTATION_ALC_RATE: 発酵のアルコール生成倍率
    INGREDIENT_COUNT("ingredient_count", PersistentDataType.INTEGER, ApplyType.ITEM),
    // INGREDIENT_COUNT: 原料の数
    LIQUOR_EFFECT_RATIO("liquor_effect_ratio", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // LIQUOR_EFFECT_RATIO: 飲料の効果倍率
    DRINK_COST("drink_cost", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // DRINK_COST: 飲むのにかかるコスト
    GLASS_VOLUME("glass_volume", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // GLASS_VOLUME: グラスの容量
    DRINK_COST_CAPABILITY("drink_cost_capability", PersistentDataType.DOUBLE, ApplyType.PLAYER);
    // DRINK_COST_CAPABILITY: drink costの許容量


    public final NamespacedKey key;
    public final PersistentDataType type;
    public final PDCKey.ApplyType apply;
    // isProtected : これがtrueのものは、PDCCで上書きできない。finalみたいなもん
    public final boolean isProtected;

    <T,Z> PDCKey(String key, PersistentDataType<T,Z> type, PDCKey.ApplyType apply) {
        this.key = new NamespacedKey("qol", key);
        this.type = type;
        this.apply = apply;
        this.isProtected = false;
    }

    <T, Z> PDCKey(String key, PersistentDataType<T, Z> type, PDCKey.ApplyType apply, boolean isProtected) {
        this.key = new NamespacedKey("qol", key);
        this.type = type;
        this.apply = apply;
        this.isProtected = isProtected;
    }

    public enum ApplyType {
        ITEM,
        BLOCK,
        PLAYER
    }
}
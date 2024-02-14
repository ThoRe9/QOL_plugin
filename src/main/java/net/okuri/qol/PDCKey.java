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
    // ALCOHOL_AMOUNT: アルコール飲料のアルコール量(ml)を記憶する
    ALCOHOL_PERCENTAGE("alcohol_percentage", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // ALCOHOL_PERCENTAGE: アルコール飲料のアルコール度数を記憶する
    ALCOHOL("alcohol", PersistentDataType.BOOLEAN, PDCKey.ApplyType.ITEM),
    // ALCOHOL: アルコール飲料かどうかを記憶する
    FOOD_LEVEL("food_level", PersistentDataType.INTEGER, PDCKey.ApplyType.ITEM),
    // FOOD_LEVEL: 食料の満腹度回復量を記憶する
    FOOD_SATURATION("food_saturation", PersistentDataType.FLOAT, PDCKey.ApplyType.ITEM),
    // FOOD_SATURATION: 食料の隠し満腹度回復量を記憶する
    FOOD_SOUND("food_sound", PersistentDataType.STRING, PDCKey.ApplyType.ITEM),
    // FOOD_SOUND: 食料の食べたときの音を記憶する
    EATABLE("eatable", PersistentDataType.BOOLEAN, PDCKey.ApplyType.ITEM),
    // EATABLE: 食べられるかどうかを記憶する。ふつう食糧と扱われないアイテム用。
    CONSUMABLE("consumable", PersistentDataType.BOOLEAN, PDCKey.ApplyType.ITEM),
    // CONSUMABLE: 飲食可能かどうかを記憶する。ふつう食糧と扱われるアイテム用。falseの場合は飲食できない。
    X("x", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // X: パラメータ計算用(1)
    Y("y", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // Y: パラメータ計算用(2)
    Z("z", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // Z: パラメータ計算用(3)
    PRODUCER("producer", PersistentDataType.STRING, PDCKey.ApplyType.ITEM),
    // producer: 1次産業での生産者の名前を記憶する
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
    DISTILLATION("distillation", PersistentDataType.INTEGER, PDCKey.ApplyType.ITEM),
    // DISTILLATION: 蒸留の回数。蒸留酒の場合のみ記憶する
    MATURATION("maturation", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // MATURATION: 熟成の日数。熟成酒の場合のみ記憶する
    MATURATION_START("maturation_start", PersistentDataType.LONG, PDCKey.ApplyType.ITEM),
    // MATURATION_START: 熟成開始日時。熟成酒の場合のみ記憶する
    MATURATION_END("maturation_end", PersistentDataType.LONG, PDCKey.ApplyType.ITEM),
    // MATURATION_END: 熟成終了日時。熟成酒の場合のみ記憶する
    RICE_POLISHING_RATIO("rice_polishing_ratio", PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // RICE_POLISHING_RATIO: 精米歩合。
    INGREDIENT_TYPE("ingredient_type", PersistentDataType.STRING, PDCKey.ApplyType.ITEM),
    // INGREDIENT_TYPE: 醸造原料の種類を記憶する
    TASTE_RICHNESS("taste_richness",PersistentDataType.DOUBLE, PDCKey.ApplyType.ITEM),
    // TASTE_RICHNESS: 味のパラメータ
    SMELL_RICHNESS("smell_richness", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // SMELL_RICHNESS: 香のパラメータ
    COMPATIBILITY("compatibillity", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // COMPATIBILITY: 相性のパラメータ
    SAKE_TYPE("sake_type", PersistentDataType.STRING, ApplyType.ITEM),
    // SAKE_TYPE: 日本酒のタイプ
    SAKE_TASTE_TYPE("sake_taste_type", PersistentDataType.STRING, ApplyType.ITEM),
    // SAKE_TASTE_TYPE: 日本酒の味のタイプ
    SAKE_ALC_TYPE("sake_alc_type", PersistentDataType.STRING, ApplyType.ITEM),
    // SAKE_ALC_TYPE: 日本酒のアルコールのタイプ(甘口、辛口)
    X_EFFECT("x_effect", PersistentDataType.STRING, ApplyType.ITEM),
    // X_EFFECT: X効果の種類
    Y_EFFECT("y_effect", PersistentDataType.STRING, ApplyType.ITEM),
    // Y_EFFECT: Y効果の種類
    Z_EFFECT("z_effect", PersistentDataType.STRING, ApplyType.ITEM),
    // Z_EFFECT: Z効果の種類
    PRODUCER_INFO("producer_info", PersistentDataType.TAG_CONTAINER, ApplyType.ITEM),
    // PRODUCER_INFO: 生産者情報
    PRODUCER_INFO_ID("producer_info_id", PersistentDataType.STRING, ApplyType.ITEM),
    // PRODUCER_INFO_ID: 生産者情報のID
    PRODUCER_INFO_QUALITY("producer_info_quality", PersistentDataType.DOUBLE, ApplyType.ITEM),
    // PRODUCER_INFO_QUALITY: 生産者情報の品質
    PRODUCER_INFO_TYPE("producer_info_type", PersistentDataType.STRING, ApplyType.ITEM),
    // PRODUCER_INFO_TYPE: 生産者情報の種類
    PRODUCER_CHILDREN("producer_children", PersistentDataType.TAG_CONTAINER_ARRAY, ApplyType.ITEM),
    // PRODUCER_CHILDREN: 生産者情報の子供
    FARMER_TOOL("farmer_tool", PersistentDataType.BOOLEAN, ApplyType.ITEM),
    // FARMER_TOOL: 農業ツールとして登録されているか。
    MINER_TOOL("miner_tool", PersistentDataType.BOOLEAN, ApplyType.ITEM),
    // MINER_TOOL: 採掘ツールとして登録されているか。
    HAS_ALC_BAR("has_alc_bar", PersistentDataType.BOOLEAN, ApplyType.PLAYER),
    // HAS_ALC_BAR: アルコールのボスバー表示をonにしているかどうか。
    MATURATION_TOOL_AMOUNT("maturation_tool_amount", PersistentDataType.INTEGER, ApplyType.ITEM),
    // MATURATION_TOOL_AMOUNT: maturation toolの進める日数。
    ADAPTERS("adapters", PersistentDataType.INTEGER_ARRAY, ApplyType.ITEM),
    // ADAPTERS: アダプターのIDを記憶する
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
    LIQUOR_EFFECT_RATIO("liquor_effect_ratio", PersistentDataType.DOUBLE, ApplyType.ITEM);
    // LIQUOR_EFFECT_RATIO: 飲料の効果倍率


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
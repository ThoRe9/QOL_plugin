package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.potion.PotionEffectType;

public class StrongZero extends Liquor {

    // Liquor のサンプル用
    // Liquorを追加する場合の手順：
    // 1. コンストラクタでSuperItemType、baseDuration, amplifierLine, 各EffectType, alcoholAmount, alcoholPercentageを設定する
    // 2. Maturationを追加する場合は追加でdefaultMaturationDaysを設定する。
    // 3. Distillationを追加する場合は何も設定は必要ありません。
    // 4. SuperCraftを追加する場合はSubIngredientなどの設定を行う必要があります。各種BuffAmpの設定も必要です。
    // 6. display. loreなども設定してください。

    public StrongZero() {
        super(SuperItemType.STRONG_ZERO, 15, 0.5, 350, 0.12, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.HUNGER);
        super.addMainIngredient(SuperItemType.RICE);
        super.setDisplayName(Component.text("Strong Zero").color(NamedTextColor.AQUA));
        super.setInfoLore("Strong Zero is a Japanese alcoholic beverage that is a type of chūhai, made by Suntory. It is a vodka-based, carbonated, alcoholic drink with fruit flavors. It is sold in cans and bottles. It is one of the most popular alcoholic drinks in Japan, especially among young people.");
    }

}

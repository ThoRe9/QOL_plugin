package net.okuri.qol.superItems.factory.drinks;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.potion.PotionEffectType;

public class liquor extends SuperItem {
    // このクラスは酒類のアイテムのもとにできるクラスです。
    // 使用する場合はコンストラクタでSuperItemTypeを指定して生成してください。
    private PotionEffectType xEffect;
    private PotionEffectType yEffect;
    private PotionEffectType zEffect;
    private int maxDuration;
    private int minAmplifier;
    private SuperResourceStack resource;

    public liquor(SuperItemType type) {
        super(type);
    }

    @Override
    public SuperItemStack getSuperItem() {
        return null;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return null;
    }
}

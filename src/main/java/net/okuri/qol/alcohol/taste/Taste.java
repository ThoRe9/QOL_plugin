package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

/**
 * 酒類の味を表すインターフェースです。
 * これを継承したものがTasteControllerに登録できます。
 */
public abstract class Taste {
    private final String ID;
    private final String displayName;
    private final NamedTextColor color;
    /**
     * 味の効果の情報
     * effectType: ポーション効果の種類
     * effectAmplifier: パラメータ1のときのポーション効果の強さ
     * effectDurationPerAmount: パラメータ1のときの1mlあたりのポーション効果の持続時間
     */
    PotionEffectType effectType;
    double effectAmplifier;
    double effectDurationPerAmount;


    /**
     * 味を生成します。
     *
     * @param ID          味のID(半角英数字とアンダーバーのみで、すべて小文字かつ、空白なし)
     * @param displayName 味の表示名
     * @param color       味の表示色
     */
    public Taste(String ID, String displayName, NamedTextColor color) {
        assert ID.matches("[a-z0-9_]+");
        this.ID = ID;
        this.displayName = displayName;
        this.color = color;
    }

    public String getID() {
        return ID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public Component getComponent() {
        return Component.text(displayName, color);
    }

    public boolean hasPotionInfo() {
        return effectType != null && effectAmplifier != 0 && effectDurationPerAmount != 0;
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }

    public double getEffectAmplifier() {
        return effectAmplifier;
    }

    public double getEffectDurationPerAmount() {
        return effectDurationPerAmount;
    }
}

package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

/**
 * 味の効果としてのポーション効果を与えるTasteです。
 */
public abstract class PotionEffectTaste extends Taste {

    /**
     * 味の効果の情報
     *
     * @param ID                      味のID(半角英数字とアンダーバーのみで、すべて小文字かつ、空白なし)
     * @param displayName             味の表示名
     * @param color                   味の表示色
     * @param effectType              ポーション効果の種類
     * @param effectAmplifier         パラメータ1のときのポーション効果の強さ
     * @param effectDurationPerAmount パラメータ1のときの1mlあたりのポーション効果の持続時間
     */
    public PotionEffectTaste(String ID, String displayName, NamedTextColor color, PotionEffectType effectType, double effectAmplifier, double effectDurationPerAmount) {
        super(ID, displayName, color);
        this.effectType = effectType;
        this.effectAmplifier = effectAmplifier;
        this.effectDurationPerAmount = effectDurationPerAmount;
    }
}

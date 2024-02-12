package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffect;
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
     * @param degradability           発酵時の減少率
     * @param volatility              蒸留時の減少率
     * @param effectType              ポーション効果の種類
     * @param effectAmplifier         パラメータ1のときのポーション効果の強さ
     * @param effectDuration          パラメータ1のときのポーション効果の持続時間
     */
    public PotionEffectTaste(String ID, String displayName, NamedTextColor color, double degradability, double volatility, PotionEffectType effectType, double effectAmplifier, double effectDuration) {
        super(ID, displayName, color, degradability, volatility);
        this.effectType = effectType;
        this.effectAmplifier = effectAmplifier;
        this.effectDuration = effectDuration;
    }

    public PotionEffect getEffect(double param) {
        return new PotionEffect(effectType, (int) (effectDuration * param), (int) (effectAmplifier * param));
    }
}

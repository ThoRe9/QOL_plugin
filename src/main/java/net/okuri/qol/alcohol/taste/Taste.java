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
    // degradability: 1日の発酵で減少する割合。減少した分だけSugarが増加する
    private final double degradability;
    // volatility: 1回の蒸留で減少する割合。
    private final double volatility;
    /**
     * 味の効果の情報
     * effectType: ポーション効果の種類
     * effectAmplifier: パラメータ1のときのポーション効果の強さ
     * effectDuration: パラメータ1のときの1mlあたりのポーション効果の持続時間
     */
    PotionEffectType effectType;
    double effectAmplifier;
    double effectDuration;
    /**
     * durationAmp: パラメータが1の時の持続時間の増幅率。すべてのポーション効果に対し作用する
     * levelAmp: レベルの増幅率。すべてのポーション効果に対し作用する
     */
    double durationAmplifier;
    double levelAmplifier;


    /**
     * 味を生成します。
     *
     * @param ID          味のID(半角英数字とアンダーバーのみで、すべて小文字かつ、空白なし)
     * @param displayName 味の表示名
     * @param color       味の表示色
     * @param degradability 発酵時の減少率
     * @param volatility 蒸留時の減少率
     */
    public Taste(String ID, String displayName, NamedTextColor color, double degradability, double volatility) {
        assert ID.matches("[a-z0-9_]+");
        this.ID = ID;
        this.displayName = displayName;
        this.color = color;
        this.degradability = degradability;
        this.volatility = volatility;
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
        return effectType != null && effectAmplifier != 0 && effectDuration != 0;
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }

    public double getEffectAmplifier() {
        return effectAmplifier;
    }

    public double getEffectDuration() {
        return effectDuration;
    }

    public double getDurationAmplifier() {
        return durationAmplifier;
    }

    public double getLevelAmplifier() {
        return levelAmplifier;
    }
}

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

    private final double[] fermentationCoeffients;
    // fermentationCoeffients: 発酵度による効果の変化率。0:発行度が∞のときの倍率, 1:速度(0<c<∞)
    private final double volatility;
    /**
     * levelAmp: レベルの増幅率。すべてのポーション効果に対し作用する
     */
    double effectAmplifier;
    /**
     * effectDuration: パラメータ1のときのポーション効果の持続時間
     */
    double effectDuration;
    /**
     * 味の効果の情報
     * effectType: ポーション効果の種類
     */
    PotionEffectType effectType;
    /**
     * durationAmp: パラメータが1の時の持続時間の増幅率。すべてのポーション効果に対し作用する
     */
    double durationAmplifier;
    /**
     * levelAmp: レベルの増幅率。すべてのポーション効果に対し作用する
     */
    double levelAmplifier;
    // volatility: 1回の蒸留で減少する割合。
    double bestFermentation = -1;
    // bestMaturation: 最適な発酵度。負の値の場合は発酵度によって効果が変化しない。
    double fermentationLine = -1;
    // FermentationLine: 発酵度がこの値を超えると効果がよりよくなる。負の値の場合は発酵度によって効果が変化しない。


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
        this.fermentationCoeffients = new double[]{2, 0.5};
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

    public boolean hasBuffInfo() {
        return durationAmplifier != 0 && levelAmplifier != 0;
    }
    public double getDurationAmplifier() {
        return durationAmplifier;
    }

    public double getLevelAmplifier() {
        return levelAmplifier;
    }

    public double getDegradability() {
        return degradability;
    }

    public double getFermentationBuff(double fermentation) {
        if (bestFermentation > 0 && fermentationLine > 0) {
            return 0.4 * (bestFunc(fermentation) + 0.6 * growFunc(fermentation));
        } else if (bestFermentation > 0) {
            return bestFunc(fermentation);
        } else if (fermentationLine > 0) {
            return growFunc(fermentation);
        }
        return 1;
    }

    private double growFunc(double x) {
        double a = fermentationCoeffients[0];
        double b = fermentationLine;
        double c = fermentationCoeffients[1];
        return (((-1) * a * b * (a - 1)) / (Math.pow(x, c) + b * (a - 1))) + a;
    }

    private double bestFunc(double x) {
        return Math.exp((-1) * (x - bestFermentation) * (x - bestFermentation) / 2) + 0.5;
    }
}

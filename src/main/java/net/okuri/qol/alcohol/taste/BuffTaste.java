package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;

public class BuffTaste extends Taste {

    /**
     * 味を生成します。
     *
     * @param ID                味のID(半角英数字とアンダーバーのみで、すべて小文字かつ、空白なし)
     * @param displayName       味の表示名
     * @param color             味の表示色
     * @param degradability     発酵時の減少率
     * @param volatility        蒸留時の減少率
     * @param durationAmplifier 持続時間の増幅率
     * @param levelAmplifier    レベルの増幅率
     */
    public BuffTaste(String ID, String displayName, double degradability, double volatility, NamedTextColor color, double durationAmplifier, double levelAmplifier) {
        super(ID, displayName, color, degradability, volatility);
        this.durationAmplifier = durationAmplifier;
        this.levelAmplifier = levelAmplifier;
    }

    public double getDurationAmplifier(double param) {
        return (durationAmplifier - 1) * param + 1;
    }

    public double getLevelAmplifier(double param) {
        return (levelAmplifier - 1) * param + 1;
    }
}

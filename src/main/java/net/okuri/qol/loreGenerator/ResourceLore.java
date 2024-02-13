package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.alcohol.taste.Taste;

import java.util.HashMap;
import java.util.Map;

public class ResourceLore extends Lore {
    /**
     * Resource用のLoreを生成するためのベースとなるクラスです。
     */
    Map<Taste, Double> tasteParams = new HashMap<>();
    double delicacy;
    double effectRate = 0.5;

    public ResourceLore() {
        super(0, "Resource", NamedTextColor.GREEN);
    }

    @Override
    public void generateLore() {
        addLore(title);
        for (Map.Entry<Taste, Double> entry : tasteParams.entrySet()) {
            addParamBarLore(entry.getKey().getDisplayName(), entry.getKey().getColor(), entry.getValue());
        }
        addParamLore("くせの強さ", Lore.A_COLOR, delicacy);
        addPercentLore("傾向", Lore.B_COLOR, effectRate * 100);
    }

    public ResourceLore addTaste(Taste taste, double param) {
        tasteParams.put(taste, param);
        return this;
    }

    public void setDelicacy(double delicacy) {
        this.delicacy = delicacy;
    }

    public void setEffectRate(double effectRate) {
        this.effectRate = effectRate;
    }
}

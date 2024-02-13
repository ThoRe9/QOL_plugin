package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.alcohol.taste.Taste;

import java.util.HashMap;
import java.util.Map;

public class LiquorIngredientLore extends Lore {

    Map<Taste, Double> tasteParams = new HashMap<>();
    double amount = 0;
    double delicacy = 0;
    double alcoholAmount = 0;
    double fermentation = 0;
    double effectRate = 0.5;

    public LiquorIngredientLore(double amount, double alcoholAmount, double delicacy, double fermentation, double effectRate) {
        super(1, "LiquorIngredient", NamedTextColor.GREEN);
        this.amount = amount;
        this.delicacy = delicacy;
        this.alcoholAmount = alcoholAmount;
        this.fermentation = fermentation;
        this.effectRate = effectRate;
    }

    @Override
    public void generateLore() {
        addLore(title);
        for (Map.Entry<Taste, Double> entry : tasteParams.entrySet()) {
            addParamLore(entry.getKey().getDisplayName(), entry.getKey().getColor(), entry.getValue(), amount);
        }
        addSeparator();
        addParamLore("くせの強さ", Lore.A_COLOR, delicacy, amount);
        addPercentLore("傾向", Lore.B_COLOR, effectRate * 100);
        addPercentLore("アルコール度数", Lore.C_COLOR, alcoholAmount / amount * 100);
        addParamLore("量", Lore.D_COLOR, amount * 1000, "ml");
        addParamLore("発酵度", Lore.A_COLOR, fermentation);

    }

    public LiquorIngredientLore addTaste(Taste taste, double param) {
        tasteParams.put(taste, param);
        return this;
    }

}

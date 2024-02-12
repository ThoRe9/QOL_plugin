package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;

public class FermentationLore extends Lore {
    private double fermentationRate = 0;
    private double alcoholRate = 0;

    public FermentationLore(double fermentationRate, double alcoholRate) {
        super(2, "FermentationIngredient", NamedTextColor.GOLD);
        this.fermentationRate = fermentationRate;
        this.alcoholRate = alcoholRate;
    }

    @Override
    public void generateLore() {
        addLore(title);
        addParamLore("発酵速度", Lore.A_COLOR, fermentationRate, "倍");
        addParamLore("アルコール生成速度", Lore.B_COLOR, alcoholRate, "倍");
    }
}

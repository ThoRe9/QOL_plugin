package net.okuri.qol.superItems.factory.drinks.whisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;

public class HighBall extends Whisky {
    public HighBall() {
        super(SuperItemType.HIGHBALL);
        super.setAlcoholAmount(120);
        super.setUseAmount(30);
        super.setAlcoholPercentage(super.getAlcoholPercentage() / 4);
        super.addDistributionIngredient(SuperItemType.SODA, DistributionBuffType.COMPATIBILITY);
        super.setDisplayName(Component.text("High Ball").color(NamedTextColor.GOLD));
        super.setInfoLore("Whisky with soda.");
    }
}

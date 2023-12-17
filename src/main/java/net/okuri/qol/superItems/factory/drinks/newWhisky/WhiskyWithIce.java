package net.okuri.qol.superItems.factory.drinks.newWhisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;

public class WhiskyWithIce extends Whisky {
    public WhiskyWithIce() {
        super(SuperItemType.WHISKY_WITH_ICE);
        super.setAlcoholAmount(30);
        super.setDisplayName(Component.text("Rock Whisky").color(NamedTextColor.AQUA));
        super.setInfoLore("A cap of whisky.");
    }
}

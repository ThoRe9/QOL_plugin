package net.okuri.qol.superItems.factory.drinks.spirits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;

public class RumStraight extends Rum {
    public RumStraight() {
        super(SuperItemType.RUM_STRAIGHT);
        super.setAlcoholAmount(30);
        super.setDisplayName(Component.text("Rum(Straight)").color(NamedTextColor.AQUA));
        super.setInfoLore("A cap of rum.");
    }
}

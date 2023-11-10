package net.okuri.qol.superItems.factory.ingredient;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.superItems.SuperItemType;

public class Molasses extends Ingredient {
    public Molasses() {
        super(SuperItemType.MOLASSES, false);
        super.addMainIngredient(SuperItemType.SUGAR_CANE);
        super.setDisplayName(Component.text("Molasses").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
    }
}

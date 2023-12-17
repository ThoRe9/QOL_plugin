package net.okuri.qol.superItems.factory.drinks.spirits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import org.bukkit.Color;

public class RumIngredient extends LiquorIngredient {
    public RumIngredient() {
        super(SuperItemType.RUM_INGREDIENT, new Rum());
        super.addMainIngredient(SuperItemType.MOLASSES);
        super.setAlcoholAmount(600);
        super.setAlcoholPercentage(0.01);
        super.setDisplayName(Component.text("Ram Ingredient").color(NamedTextColor.AQUA));
        super.setInfoLore("You need to distill this to make Ram.");
        super.setPotionColor(Color.fromRGB(0xFFDB3F));
    }
}

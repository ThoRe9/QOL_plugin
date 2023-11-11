package net.okuri.qol.superItems.factory.drinks.spirits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import org.bukkit.Color;

public class RamIngredient extends LiquorIngredient {
    public RamIngredient() {
        super(SuperItemType.RAM_INGREDIENT, new Ram());
        super.addMainIngredient(SuperItemType.MOLASSES);
        super.setAlcoholAmount(1000);
        super.setAlcoholPercentage(0.01);
        super.setDisplayName(Component.text("Ram Ingredient").color(NamedTextColor.AQUA));
        super.setInfoLore("You need to distill this to make Ram.");
        super.setPotionColor(Color.fromRGB(0xFFDB3F));
    }
}

package net.okuri.qol.superItems.factory.drinks.newWhisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import org.bukkit.Color;

public class BeerIngredient extends LiquorIngredient {
    public BeerIngredient() {
        super(SuperItemType.BEER_INGREDIENT, new Beer(SuperItemType.BEER));
        super.addMainIngredient(SuperItemType.BARLEY);
        super.setDistillationAmp(0.01);
        super.setAlcoholAmount(600);
        super.setAlcoholPercentage(0.01);
        super.setDisplayName(Component.text("Beer Ingredient").color(NamedTextColor.AQUA));
        super.setInfoLore("You need to distill or maturate this to make Beer.");
        super.setPotionColor(Color.fromRGB(0xFFDB3F));
    }
}

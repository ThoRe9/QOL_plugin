package net.okuri.qol.superItems.factory.drinks.newWhisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import org.bukkit.Color;

public class WhiskyIngredient extends LiquorIngredient {
    public WhiskyIngredient() {
        super(SuperItemType.WHISKY_INGREDIENT, new Whisky());
        super.addDistillationIngredient(SuperItemType.UNDISTILLED_WHISKY_INGREDIENT);
        super.setDistillationAmp(0.01);
        super.setAlcoholAmount(600);
        super.setAlcoholPercentage(0.40);
        super.setDisplayName(Component.text("Whisky Ingredient").color(NamedTextColor.AQUA));
        super.setInfoLore("You need to distill or maturate this to make Whisky.");
        super.setPotionColor(Color.fromRGB(0xFFDB3F));
    }

}

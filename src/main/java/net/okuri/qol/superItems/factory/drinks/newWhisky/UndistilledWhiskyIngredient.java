package net.okuri.qol.superItems.factory.drinks.newWhisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import org.bukkit.Color;

public class UndistilledWhiskyIngredient extends LiquorIngredient {
    public UndistilledWhiskyIngredient() {
        super(SuperItemType.UNDISTILLED_WHISKY_INGREDIENT, new Whisky());
        super.addMainIngredient(SuperItemType.BARLEY);
        super.addMainBuffIngredient(SuperItemType.COAL);
        super.setAlcoholAmount(600);
        super.setAlcoholPercentage(0.01);
        super.setDisplayName(Component.text("Whisky Ingredient").color(NamedTextColor.AQUA));
        super.setInfoLore("You need to distill this to make Whisky.");
        super.setPotionColor(Color.fromRGB(0xFFDB3F));
    }
}

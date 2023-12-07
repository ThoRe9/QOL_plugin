package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;

public class Horoyoi extends Liquor {
    public Horoyoi() {
        super(SuperItemType.HOROYOI, 50, 0.9, 300, 0.03, PotionEffectType.SPEED);
        super.setDisplayName(Component.text("ほろよい").color(NamedTextColor.AQUA));
        super.setInfoLore("Delicious and refreshing.");
        super.setPotionColor(Color.fromRGB(0xEA5C03));
        super.addMainIngredient(SuperItemType.APPLE);
    }

}

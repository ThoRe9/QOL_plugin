package net.okuri.qol.superItems.factory.drinks.spirits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;

public class Rum extends Liquor {
    public Rum() {
        super(SuperItemType.RUM, 100, 0.5, 700, 0.40, PotionEffectType.WATER_BREATHING, PotionEffectType.DOLPHINS_GRACE, PotionEffectType.CONDUIT_POWER);
        super.setDisplayName(Component.text("Ram").color(NamedTextColor.AQUA));
        super.setInfoLore("Delicious and refreshing, Ram is a popular drink in the desert.");
        super.setPotionColor(Color.fromRGB(0xEA5C03));
    }

    public Rum(SuperItemType type) {
        super(type, 100, 0.5, 700, 0.40, PotionEffectType.WATER_BREATHING, PotionEffectType.DOLPHINS_GRACE, PotionEffectType.CONDUIT_POWER);
        super.setDisplayName(Component.text("Ram").color(NamedTextColor.AQUA));
        super.setInfoLore("Delicious and refreshing, Ram is a popular drink in the desert.");
        super.setPotionColor(Color.fromRGB(0xEA5C03));
    }
}

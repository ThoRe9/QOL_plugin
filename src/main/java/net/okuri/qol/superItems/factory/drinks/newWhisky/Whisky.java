package net.okuri.qol.superItems.factory.drinks.newWhisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;

public class Whisky extends Liquor {
    public Whisky() {
        super(SuperItemType.WHISKY, 80, 0.5, 420, 0.40, PotionEffectType.FAST_DIGGING, PotionEffectType.SPEED, PotionEffectType.NIGHT_VISION);
        super.addMaturationIngredient(SuperItemType.WHISKY_INGREDIENT);
        super.setMaturationType(MaturationType.DIVLINE);
        super.setBaseDurationAmp(0.9);
        super.setCompatibilityMax(1.0);
        super.setCompatibilityMin(0.5);
        super.setDisplayName(Component.text("Whisky").color(NamedTextColor.GOLD));
        super.setInfoLore("Barley whisky.");
        super.setPotionColor(Color.fromBGR(0, 80 / (1 + (int) super.getMaturationDays()), 255 / (1 + (int) super.getMaturationDays())));
    }

    public Whisky(SuperItemType type) {
        super(type, 80, 0.5, 420, 0.40, PotionEffectType.FAST_DIGGING, PotionEffectType.SPEED, PotionEffectType.NIGHT_VISION);
        super.addMaturationIngredient(SuperItemType.WHISKY_INGREDIENT);
        super.setMaturationType(MaturationType.DIVLINE);
        super.setBaseDurationAmp(0.1);
        super.setDisplayName(Component.text("Whisky").color(NamedTextColor.GOLD));
        super.setInfoLore("Barley whisky.");
        super.setPotionColor(Color.fromRGB(0xEA5C03));
    }
}

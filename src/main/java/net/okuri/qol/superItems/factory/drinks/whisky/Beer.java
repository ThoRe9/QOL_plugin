package net.okuri.qol.superItems.factory.drinks.whisky;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.potion.PotionEffectType;

public class Beer extends Liquor {
    public Beer(SuperItemType type) {
        super(type, 15, 0.5, 420, 0.06, PotionEffectType.FAST_DIGGING);
        super.addMaturationIngredient(SuperItemType.BEER_INGREDIENT);
        super.setMaturationType(MaturationType.COPY_AND_DIVLINE);
        super.setBaseDurationAmp(0.9);
        super.setDefaultMaturationDays(1);
        super.setCompatibilityMax(1.0);
        super.setCompatibilityMin(0.6);
    }
}

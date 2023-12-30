package net.okuri.qol.superItems.factory.drinks.whisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.potion.PotionEffectType;

public class LagerBeer extends Beer {
    public LagerBeer() {
        super(SuperItemType.LAGER_BEER);
        super.setxEffectType(null);
        super.setyEffectType(PotionEffectType.SPEED);
        super.setzEffectType(PotionEffectType.NIGHT_VISION);
        super.setDisplayName(Component.text("Lager Beer").color(NamedTextColor.GOLD));
        super.setInfoLore("Barley beer.");
        super.setPotionColor(org.bukkit.Color.fromRGB(246, 245, 19));
    }
}

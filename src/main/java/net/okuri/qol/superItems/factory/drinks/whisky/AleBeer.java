package net.okuri.qol.superItems.factory.drinks.whisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.potion.PotionEffectType;

public class AleBeer extends Beer {
    public AleBeer() {
        super(SuperItemType.ALE_BEER);
        super.setxEffectType(PotionEffectType.FAST_DIGGING);
        super.setyEffectType(null);
        super.setzEffectType(null);
        super.setDisplayName(Component.text("Ale Beer").color(NamedTextColor.GOLD));
        super.setInfoLore("Barley beer.");
        super.setPotionColor(org.bukkit.Color.fromRGB(246, 245, 19));
    }
}

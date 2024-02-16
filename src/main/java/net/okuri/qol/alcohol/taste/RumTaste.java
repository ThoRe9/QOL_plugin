package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class RumTaste extends PotionEffectTaste {
    public static final RumTaste instance = new RumTaste();

    private RumTaste() {
        super("rum", "ラムの香り", NamedTextColor.DARK_AQUA, 0.1, 1, PotionEffectType.WATER_BREATHING, 1.5, 1800 * 20);
    }
}

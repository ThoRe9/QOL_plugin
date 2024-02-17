package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class SkyTaste extends PotionEffectTaste {
    public static final SkyTaste instance = new SkyTaste();

    private SkyTaste() {
        super("sky", "空の味", NamedTextColor.AQUA, 1, 0.2, PotionEffectType.LEVITATION, 1.5, 1800 * 20);
    }
}

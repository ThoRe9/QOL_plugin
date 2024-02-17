package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class RiceTaste extends PotionEffectTaste {
    public static final RiceTaste instance = new RiceTaste();

    private RiceTaste() {
        super("rice", "コメのうまみ", NamedTextColor.DARK_GREEN, 0.4, 0.85, PotionEffectType.DAMAGE_RESISTANCE, 1.5, 1800 * 20);
        super.fermentationLine = 1;
    }
}

package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

/**
 * 麦の味を表すクラスです。(test)
 */
public class BarleyTaste extends PotionEffectTaste {
    public static final BarleyTaste instance = new BarleyTaste();

    private BarleyTaste() {
        super("barley", "麦の香り", NamedTextColor.GOLD, 0.2, 0.1, PotionEffectType.FAST_DIGGING, 1.5, 60 * 3600 * 20);
    }
}

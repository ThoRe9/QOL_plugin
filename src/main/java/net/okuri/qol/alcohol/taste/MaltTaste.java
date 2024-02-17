package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class MaltTaste extends PotionEffectTaste {
    public static final MaltTaste instance = new MaltTaste();
    private MaltTaste() {
        super("malt", "麦芽のコク", NamedTextColor.GOLD, 0.3, 0.9, PotionEffectType.FAST_DIGGING, 1.4, 1800 * 20);
        super.fermentationLine = 1;
    }
}

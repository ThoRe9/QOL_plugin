package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class RiceSweetness extends PotionEffectTaste {
    public static final RiceSweetness instance = new RiceSweetness();

    private RiceSweetness() {
        super("rice_sweetness", "コメの甘味", NamedTextColor.WHITE, 0.5, 0.8, PotionEffectType.INCREASE_DAMAGE, 1.5, 1800 * 20);
        super.delicacyBuff = 0.8;
        super.fermentationLine = 2;
        super.bestFermentation = 1;
    }
}

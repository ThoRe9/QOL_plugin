package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class GrapeSweetness extends PotionEffectTaste {
    public static final GrapeSweetness instance = new GrapeSweetness();

    private GrapeSweetness() {
        super("grape_sweetness", "葡萄の甘味", NamedTextColor.DARK_PURPLE, 0.4, 1, PotionEffectType.HEAL, 1.5, 0);
    }
}

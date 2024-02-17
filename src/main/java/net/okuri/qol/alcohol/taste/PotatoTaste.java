package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class PotatoTaste extends PotionEffectTaste {
    public static final PotatoTaste instance = new PotatoTaste();

    private PotatoTaste() {
        super("potato", "芋の風味", NamedTextColor.GRAY, 0.5, 0.9, PotionEffectType.HEAL, 2.0, 0);
        super.delicacyBuff = 1.1;
    }
}

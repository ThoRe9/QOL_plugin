package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class GrapeBitterness extends PotionEffectTaste {
    public static final GrapeBitterness instance = new GrapeBitterness();

    private GrapeBitterness() {
        super("grape_bitterness", "葡萄の苦み", NamedTextColor.DARK_PURPLE, 0, 0.95, PotionEffectType.REGENERATION, 1.5, 1800 * 20);
        super.delicacyBuff = 1.2;
    }
}

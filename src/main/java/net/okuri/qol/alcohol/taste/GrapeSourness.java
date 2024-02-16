package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

public class GrapeSourness extends PotionEffectTaste {
    public static GrapeSourness instance = new GrapeSourness();

    private GrapeSourness() {
        super("grape_sourness", "葡萄の酸味", NamedTextColor.DARK_PURPLE, 0, 0.5, PotionEffectType.ABSORPTION, 1.5, 1800 * 20);
        super.bestFermentation = 1;
        super.delicacyBuff = 1.5;
    }
}

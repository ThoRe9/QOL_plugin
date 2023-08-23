package net.okuri.qol.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.NamespacedKey;

public class BarleyBread extends Bread{
    private Component display = Component.text("Barley Bread").color(NamedTextColor.DARK_GREEN);
    private NamespacedKey wheatkey = SuperWheat.zkey;
}

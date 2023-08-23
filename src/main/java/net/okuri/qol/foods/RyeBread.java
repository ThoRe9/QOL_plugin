package net.okuri.qol.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.NamespacedKey;

public class RyeBread extends Bread{
    private Component display = Component.text("Rye Bread").color(NamedTextColor.DARK_GREEN);
    private NamespacedKey wheatkey = SuperWheat.xkey;
}

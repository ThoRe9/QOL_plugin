package net.okuri.qol.superItems.factory.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class SuperApple extends SuperResource {
    public SuperApple() {
        super(Component.text("SuperApple").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD),
                "This is a superApple",
                Material.OAK_LEAVES,
                SuperItemType.APPLE,
                5);
        this.base = 1.0;
    }
}

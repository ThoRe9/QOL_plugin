package net.okuri.qol.superItems.factory.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SugarCane extends SuperResource {
    public SugarCane() {
        super(Component.text("Super Sugar Cane").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD),
                "This is a super sugar cane!",
                Material.SUGAR_CANE,
                SuperItemType.SUGAR_CANE,
                3);
        this.base = 1.0;
    }

    @Override
    public void setResVariables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality, Player producer) {
        super.setResVariables(Px, Py, Pz, temp, humid, biomeId, quality, producer);
    }
}

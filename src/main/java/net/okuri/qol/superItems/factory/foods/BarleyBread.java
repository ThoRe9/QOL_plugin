package net.okuri.qol.superItems.factory.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemStack;

public class BarleyBread extends Bread {
    public BarleyBread() {
        super();
        super.display = Component.text("Barley Bread").color(NamedTextColor.DARK_GREEN);
        super.wheatkey = PDCKey.Z;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem();
    }
}

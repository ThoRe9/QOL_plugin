package net.okuri.qol.superItems.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemStack;

public class RyeBread extends Bread {

    public RyeBread() {
        super();
        super.display = Component.text("Rye Bread").color(NamedTextColor.DARK_GREEN);
        super.wheatkey = PDCKey.X;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem();
    }
}

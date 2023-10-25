package net.okuri.qol.superItems.resources;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

public class Rye extends SuperWheat {
    public Rye() {
        super(SuperItemType.RYE);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(0);
    }
}

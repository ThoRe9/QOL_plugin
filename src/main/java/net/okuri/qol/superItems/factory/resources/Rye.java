package net.okuri.qol.superItems.factory.resources;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;

public class Rye extends SuperWheat {
    public Rye() {
        super(SuperItemType.RYE);
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {
        return super.getDebugItem(0);
    }
}

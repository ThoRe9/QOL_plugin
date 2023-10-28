package net.okuri.qol.superItems.factory.resources;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;

public class Barley extends SuperWheat {
    public Barley() {
        super(SuperItemType.BARLEY);
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {
        return super.getDebugItem(1);
    }
}

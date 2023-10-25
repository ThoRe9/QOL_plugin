package net.okuri.qol.superItems.resources;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

public class Barley extends SuperWheat {
    public Barley() {
        super(SuperItemType.BARLEY);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(1);
    }
}

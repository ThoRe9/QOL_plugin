package net.okuri.qol.superItems.factory.resources;


import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;

public class Rice extends SuperWheat {
    public Rice() {
        super(SuperItemType.RICE);
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {
        return super.getDebugItem(3);
    }
}

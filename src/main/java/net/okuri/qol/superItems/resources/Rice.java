package net.okuri.qol.superItems.resources;


import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

public class Rice extends SuperWheat {
    public Rice() {
        super(SuperItemType.RICE);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(3);
    }
}

package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class SuperPotato implements SuperItem {
    private final SuperItemType superItemType = SuperItemType.POTATO;

    @Override
    public ItemStack getSuperItem() {
        return null;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        return null;
    }


}

package net.okuri.qol.superItems.drinks;

import org.bukkit.inventory.ItemStack;

public class AleBeer extends Beer {
    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(1);
    }
}

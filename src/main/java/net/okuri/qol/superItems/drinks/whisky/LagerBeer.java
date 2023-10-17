package net.okuri.qol.superItems.drinks.whisky;

import org.bukkit.inventory.ItemStack;

public class LagerBeer extends Beer {
    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(0);
    }
}

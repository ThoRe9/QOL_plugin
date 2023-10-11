package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class Rye extends SuperWheat {
    public Rye() {
        super(SuperItemType.RYE);
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(0);
    }
}

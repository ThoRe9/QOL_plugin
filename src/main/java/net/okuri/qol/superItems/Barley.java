package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class Barley extends SuperWheat {
    public Barley() {
        super(SuperItemType.BARLEY);
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(1);
    }
}

package net.okuri.qol.superItems;


import org.bukkit.inventory.ItemStack;

public class Rice extends SuperWheat {
    public Rice() {
        super(SuperItemType.RICE);
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(3);
    }
}

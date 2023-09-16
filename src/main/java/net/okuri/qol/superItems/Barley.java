package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class Barley extends SuperWheat {
    public Barley(){
        super(SuperItemType.BARLEY);
    }
    public Barley(int x, int y, int z, String name, double temp, int biomeID, double quality) {
        super(x, y, z, name, 0.25, biomeID, quality);
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem(1);
    }
}

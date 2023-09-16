package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class Wheat extends SuperWheat{
    public Wheat(){
        super(SuperItemType.WHEAT);
    }
    public Wheat(int x, int y, int z, String name, double temp, int biomeID, double quality) {
        super(x, y, z, name, 0.75, biomeID, quality);
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem(2);
    }
}

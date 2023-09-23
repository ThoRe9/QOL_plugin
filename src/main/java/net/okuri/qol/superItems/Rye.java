package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public class Rye extends SuperWheat{
    public Rye(){
        super(SuperItemType.RYE);
    }
    public Rye(int x, int y, int z, String name, double temp, int biomeID, double quality) {
        super(x, y, z, name, -0.25, 1.0, biomeID, quality);
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem(0);
    }
}

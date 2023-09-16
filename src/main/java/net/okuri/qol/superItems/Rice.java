package net.okuri.qol.superItems;


import org.bukkit.inventory.ItemStack;

public class Rice extends SuperWheat{
    public Rice(){
        super(SuperItemType.RICE);
    }
    public Rice(int x, int y, int z, String name, double temp, int biomeID, double quality) {
        super(x, y, z, name, 1.2, biomeID, quality);
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem(3);
    }
}

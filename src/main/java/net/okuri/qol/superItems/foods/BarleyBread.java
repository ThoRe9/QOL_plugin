package net.okuri.qol.superItems.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.inventory.ItemStack;

public class BarleyBread extends Bread{
    public BarleyBread(){
        super.display = Component.text("Barley Bread").color(NamedTextColor.DARK_GREEN);
        super.wheatkey = SuperWheat.zkey;
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem();
    }
}
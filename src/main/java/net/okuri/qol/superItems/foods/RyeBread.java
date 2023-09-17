package net.okuri.qol.superItems.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.inventory.ItemStack;

public class RyeBread extends Bread{

    public RyeBread(){
        super.display = Component.text("Rye Bread").color(NamedTextColor.DARK_GREEN);
        super.wheatkey = SuperWheat.xkey;
    }

    @Override
    public ItemStack getDebugItem(int... args){
        return super.getDebugItem();
    }
}

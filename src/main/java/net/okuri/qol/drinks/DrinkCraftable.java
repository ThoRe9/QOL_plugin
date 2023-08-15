package net.okuri.qol.drinks;

import net.okuri.qol.superItems.SuperItem;
import org.bukkit.inventory.ItemStack;

public abstract class DrinkCraftable extends SuperItem {
    // matrixは作業台の材料を記憶する
    private ItemStack[] matrix;

    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
    }
}

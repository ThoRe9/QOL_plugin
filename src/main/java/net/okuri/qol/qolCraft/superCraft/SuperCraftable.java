package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItem;
import org.bukkit.inventory.ItemStack;

public abstract class SuperCraftable extends SuperItem {
    // matrixは作業台の材料を記憶する
    // 注意!!
    // DrinkCraftで呼び出さられるのはsetMatrix,ののちにgetSuperItemが呼び出される
    //
    protected ItemStack[] matrix;

    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
    }
}

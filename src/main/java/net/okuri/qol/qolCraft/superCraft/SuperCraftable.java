package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemStack;

public interface SuperCraftable {
    // matrixは作業台の材料を記憶する
    // 注意!!
    // DrinkCraftで呼び出さられるのはsetMatrix,ののちにgetSuperItemが呼び出される
    //

    void setMatrix(SuperItemStack[] matrix, String id);
}

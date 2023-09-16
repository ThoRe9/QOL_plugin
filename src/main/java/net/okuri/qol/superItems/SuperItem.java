package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public abstract class SuperItem {
    /*
    SuperItemは、SuperItemの基底クラスです。
    これを継承して、SuperItemを作成します。
    getSuperItem()をオーバーライドして、SuperItemを作成してください。
    このメソッドでsuperItemTypeが設定されたItemStackを返すようにしてください。
     */

    private ItemStack itemStack;

    private SuperItemType superItemType;
    public abstract ItemStack getSuperItem();
    public abstract ItemStack getDebugItem(int... args);


}

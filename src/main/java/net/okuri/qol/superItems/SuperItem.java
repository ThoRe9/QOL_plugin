package net.okuri.qol.superItems;

import org.bukkit.inventory.ItemStack;

public interface SuperItem {
    /*
    SuperItemは、SuperItemの基底クラスです。
    これを継承して、SuperItemを作成します。
    getSuperItem()をオーバーライドして、SuperItemを作成してください。
    このメソッドでsuperItemTypeが設定されたItemStackを返すようにしてください。
     */

    ItemStack getSuperItem();
    ItemStack getDebugItem(int... args);


}

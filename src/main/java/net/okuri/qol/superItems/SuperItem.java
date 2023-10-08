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

    static int getRarity(double x, double y, double z) {
        // x+y+z=1以下が☆ (Common)
        // x+y+z=1.2が☆☆ (Uncommon)
        // x+y+z=1.4が☆☆☆ (Rare)
        // x+y+z=1.6が☆☆☆☆ (Epic)
        // x+y+z=1.8が☆☆☆☆☆ (Legendary)
        // x+y+z=2が☆☆☆☆☆☆ (Mythical)

        int rarity = 0;
        double sum = x + y + z;
        if (sum >= 2) rarity = 6;
        else if (sum >= 1.8) rarity = 5;
        else if (sum >= 1.6) rarity = 4;
        else if (sum >= 1.4) rarity = 3;
        else if (sum >= 1.2) rarity = 2;
        else if (sum >= 1) rarity = 1;
        else rarity = 1;
        return rarity;
    }

}

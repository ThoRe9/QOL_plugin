package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemType;

public interface Distributable extends SuperCraftable{
    // setMatrixの1番目の要素には分配前のボトルが記憶される
    // もしsmallBottleにsuperItemを用いる場合は、その情報がmatrixの2番目以降に任意の数入れられる。
    // その後、isDistributableが呼び出され、trueが返された場合、distributeの後、getSuperItemが呼び出される。
    // ただし、そのアイテムはプレイヤーに直接渡される。

    boolean isDistributable(double smallBottleAmount, int smallBottleCount);
    void distribute(double smallBottleAmount, int smallBottleCounts);
    SuperItemType getType();
}

package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemType;

public interface Distributable extends SuperCraftable{
    // setMatrixの1番目の要素には分配前のボトルが記憶される。(2番目以降は必ずnull)
    // その後、isDistributableが呼び出され、trueが返された場合、distributeの後、getSuperItemが呼び出される。
    // ただし、そのアイテムはプレイヤーに直接渡される。

    boolean isDistributable(double smallBottleAmount, int smallBottleCount);
    void distribute(double smallBottleAmount, int smallBottleCounts);
    SuperItemType getType();
}

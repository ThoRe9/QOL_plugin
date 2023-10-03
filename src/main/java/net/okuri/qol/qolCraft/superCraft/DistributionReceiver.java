package net.okuri.qol.qolCraft.superCraft;

public interface DistributionReceiver extends SuperCraftable{
    // setMatrixの1番目の要素には分配前のボトルが記憶される。(2番目以降は必ずnull)
    // その後、receiveが呼び出される。この時、量などを設定してください。
    // 最後にgetSuperItemが呼び出される。

    void receive(int count);
}

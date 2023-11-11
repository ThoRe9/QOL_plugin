package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.superItems.itemStack.SuperItemStack;

public interface Distillable {
    // distillationの処理は以下のように進む
    // 1. setDistillationVariable で 材料、温度、湿度を設定
    // 2. getSuperItem で結果を取得する。

    void setDistillationVariable(SuperItemStack item, double temp, double humid);

    SuperItemStack getSuperItem();

}

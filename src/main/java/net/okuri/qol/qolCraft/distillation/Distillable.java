package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.superItems.SuperItem;
import org.bukkit.inventory.ItemStack;

public interface Distillable extends SuperItem {
    // distillationの処理は以下のように進む
    // 1. setDistillationVariable で 材料、温度、湿度を設定
    // 2. getSuperItem で結果を取得する。

    void setDistillationVariable(ItemStack item, double temp, double humid);
}

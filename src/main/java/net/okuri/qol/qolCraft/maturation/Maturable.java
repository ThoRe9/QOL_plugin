package net.okuri.qol.qolCraft.maturation;

import net.okuri.qol.superItems.SuperItemStack;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Maturable {
    // maturationの処理は以下のように進む
    // 1. setMaturationVariable で 材料、開始時刻、終了時刻、温度、湿度を設定
    // 2. getSuperItem で結果を取得する。

    void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid);
}

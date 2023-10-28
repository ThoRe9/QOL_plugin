package net.okuri.qol.superItems.factory.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SampleResource extends SuperResource {

    // SampleResource: 資源を簡単に追加できます
    // 1. コンストラクタで名前, 説明文, 資源のブロック, SuperItemType, 確率(%), 乱数(パラメータ用)を決める
    // 2. (任意) setResVariables でパラメータ計算を実装する。デフォルトはSuperWheatのものを使用。
    // 3. (任意) getSuperItem でアイテム生成時の処理を実装する。デフォルトでも可能。
    // 4. (任意) getDebugItem でデバッグ用アイテム生成時の処理を実装する。デフォルトでも可能。
    // 5. GetSuperItemListenerに登録する。
    // おわり！

    public SampleResource() {
        super(Component.text("SAMPLE RESOURCE").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD),
                "This is a sample resource",
                Material.BEDROCK,
                SuperItemType.COAL,
                0);
        this.base = 1.0;
    }

    @Override
    public void setResVariables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality, Player producer) {
        super.setResVariables(Px, Py, Pz, temp, humid, biomeId, quality, producer);
    }

    @Override
    public SuperResourceStack getSuperItem() {
        return getSuperItem();
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {
        return getDebugItem(args);
    }
}

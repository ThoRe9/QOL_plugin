package net.okuri.qol.superItems.factory;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Material;

public abstract class SuperItem {
    /*
    SuperItemは、SuperItemの基底クラスです。
    これを継承して、SuperItemを作成します。
    getSuperItem()をオーバーライドして、SuperItemを作成してください。
    このメソッドでsuperItemTypeが設定されたItemStackを返すようにしてください。
     */

    /*
    SuperItemは金型のようなものです。
    getSuperItemで得られる　SuperItemStackが、実際にプレイヤーが持つアイテムです。
    あくまでこのクラスは、SuperItemStackを作成するためのクラスです。
     */

    private SuperItemData superItemData;
    private Material material;
    private int count = 1;
    private boolean consumable = true;

    public SuperItem(SuperItemType type, SuperItemStack stack) {
        if (type != stack.getSuperItemType()) throw new IllegalArgumentException("SuperItemTypeが一致しません。");
        this.superItemData = new SuperItemData(stack.getSuperItemType());
        this.material = stack.getType();
    }

    public SuperItem(SuperItemType superItemType) {
        this.superItemData = new SuperItemData(superItemType);
        this.material = superItemType.getMaterial();
    }

    public SuperItem(Material material) {
        this.superItemData = new SuperItemData(material);
        this.material = material;
    }

    public static int getRarity(double x, double y, double z) {
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

    public SuperItemType getSuperItemType() {
        return this.superItemData.getType();
    }

    public void setSuperItemType(SuperItemType superItemType) {
        this.superItemData = new SuperItemData(superItemType);
        this.material = superItemType.getMaterial();
    }

    public SuperItemData getSuperItemData() {
        return this.superItemData;
    }

    public Material getMaterial() {
        return this.material;
    }

    public SuperItemStack getSuperItem() {
        SuperItemStack stack = new SuperItemStack(this.superItemData.getType(), this.count);
        if (!this.consumable) {
            stack.setConsumable(false);
        }
        return stack;
    }

    public abstract SuperItemStack getDebugItem(int... args);

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setConsumable(boolean flag) {
        this.consumable = flag;
    }

}

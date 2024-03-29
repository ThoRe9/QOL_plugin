package net.okuri.qol.superItems.factory;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

public class DefaultItem extends SuperItem {
    // このクラスはSuperItemではないバニラのアイテムを扱うためのクラスです。
    // このクラスを継承して、バニラのアイテムを扱うクラスを作成してください。


    public DefaultItem(SuperItemStack stack) {
        super(SuperItemType.DEFAULT, stack);
    }

    public DefaultItem(SuperItemType superItemType) {
        super(superItemType);
    }

    public SuperItemStack getSuperItem() {
        return new SuperItemStack(this.getMaterial());
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }
}

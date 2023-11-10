package net.okuri.qol.superItems.factory.ingredient;

import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.CraftableXYZItem;

public abstract class Ingredient extends CraftableXYZItem {

    public Ingredient(SuperItemType superItemType, boolean hasTSC) {
        super(superItemType, hasTSC);
        assert superItemType.getTag() == SuperItemTag.INGREDIENT;
    }

}

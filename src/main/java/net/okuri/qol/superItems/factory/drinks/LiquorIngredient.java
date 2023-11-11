package net.okuri.qol.superItems.factory.drinks;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperLiquorStack;

public abstract class LiquorIngredient extends Liquor {

    public LiquorIngredient(SuperItemType type, Liquor liquor) {
        super(type, liquor.getBaseDuration(), liquor.getAmplifierLine(), liquor.getAlcoholAmount(), liquor.getAlcoholPercentage(), liquor.getXEffectType(), liquor.getYEffectType(), liquor.getZEffectType());
    }

    @Override
    public SuperLiquorStack getSuperItem() {
        SuperLiquorStack result = super.getSuperItem();
        result.setConsumable(false);
        return result;
    }
}

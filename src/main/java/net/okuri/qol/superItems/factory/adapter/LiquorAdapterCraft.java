package net.okuri.qol.superItems.factory.adapter;

import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiquorIngredient;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperLiquorStack;

public class LiquorAdapterCraft implements SuperCraftable {
    private SuperLiquorStack liquorIngredient;
    private LiquorIngredient liquorIngredientClass;
    private SuperItemStack liquorAdaptor;
    private LiquorAdapter liquorAdaptorClass;

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.liquorIngredient = new SuperLiquorStack(matrix[0]);
        this.liquorIngredientClass = (LiquorIngredient) SuperItemType.getSuperItemClass(this.liquorIngredient.getSuperItemType());
        this.liquorIngredientClass.initialize(this.liquorIngredient);
        this.liquorAdaptor = matrix[1];
        this.liquorAdaptorClass = (LiquorAdapter) SuperItemType.getSuperItemClass(this.liquorAdaptor.getSuperItemType());
    }

    @Override
    public SuperItemStack getSuperItem() {
        double x = this.liquorIngredientClass.getX();
        double y = this.liquorIngredientClass.getY();
        double z = this.liquorIngredientClass.getZ();
        double taste = this.liquorIngredientClass.getTaste();
        double smell = this.liquorIngredientClass.getSmell();
        double compatibility = this.liquorIngredientClass.getCompatibility();
        double amount = this.liquorIngredientClass.getAmount();
        double alcPercent = this.liquorIngredientClass.getAlcoholPercentage();
        x += liquorAdaptorClass.getxAddition();
        x *= liquorAdaptorClass.getxAmplifier();
        y += liquorAdaptorClass.getyAddition();
        y *= liquorAdaptorClass.getyAmplifier();
        z += liquorAdaptorClass.getzAddition();
        z *= liquorAdaptorClass.getzAmplifier();
        taste += liquorAdaptorClass.getTasteAddition();
        taste *= liquorAdaptorClass.getTasteAmplifier();
        smell += liquorAdaptorClass.getSmellAddition();
        smell *= liquorAdaptorClass.getSmellAmplifier();
        compatibility += liquorAdaptorClass.getCompatibilityAddition();
        compatibility *= liquorAdaptorClass.getCompatibilityAmplifier();
        amount += liquorAdaptorClass.getAmountAddition();
        amount *= liquorAdaptorClass.getAmountAmplifier();
        alcPercent += liquorAdaptorClass.getAlcPercentAddition();
        alcPercent *= liquorAdaptorClass.getAlcPercentAmplifier();
        this.liquorIngredientClass.setX(x);
        this.liquorIngredientClass.setY(y);
        this.liquorIngredientClass.setZ(z);
        this.liquorIngredientClass.setTaste(taste);
        this.liquorIngredientClass.setSmell(smell);
        this.liquorIngredientClass.setCompatibility(compatibility);
        this.liquorIngredientClass.setAlcoholAmount(amount);
        this.liquorIngredientClass.setAlcoholPercentage(alcPercent);
        this.liquorIngredientClass.setDisplayName(this.liquorAdaptorClass.getHeader().append(this.liquorIngredientClass.getDisplayName()));
        this.liquorIngredientClass.addAdapter(this.liquorAdaptorClass.getAdapterID());
        return this.liquorIngredientClass.getSuperItem();
    }
}

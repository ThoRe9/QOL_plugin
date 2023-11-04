package net.okuri.qol.qolCraft.maturation;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;

import java.util.ArrayList;


public class MaturationRecipe {
    private final ArrayList<SuperItemData> ingredients = new ArrayList<>();

    private final Maturable resultClass;
    private final String recipeName;

    public MaturationRecipe(String name, Maturable resultClass) {
        this.resultClass = resultClass;
        this.recipeName = name;
    }

    public void addIngredient(SuperItemType superIngredient) {
        this.addIngredient(new SuperItemData(superIngredient));
    }

    public void addIngredient(SuperItemData superIngredient) {
        ingredients.add(superIngredient);
    }

    public int getIngredientSize() {
        return ingredients.size();
    }

    public ArrayList<SuperItemData> getIngredients() {
        return ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Maturable getResultClass() {
        return resultClass;
    }
}

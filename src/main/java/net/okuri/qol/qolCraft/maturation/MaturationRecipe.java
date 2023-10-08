package net.okuri.qol.qolCraft.maturation;

import net.okuri.qol.superItems.SuperItemType;

import java.util.ArrayList;


public class MaturationRecipe {
    private final ArrayList<SuperItemType> ingredients = new ArrayList<>();

    private final Maturable resultClass;
    private final String recipeName;

    public MaturationRecipe(String name, Maturable resultClass) {
        this.resultClass = resultClass;
        this.recipeName = name;
    }

    public void addingredient(SuperItemType superIngredient) {
        ingredients.add(superIngredient);
    }

    public int getIngredientSize() {
        return ingredients.size();
    }

    public ArrayList<SuperItemType> getIngredients() {
        return ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Maturable getResultClass() {
        return resultClass;
    }
}

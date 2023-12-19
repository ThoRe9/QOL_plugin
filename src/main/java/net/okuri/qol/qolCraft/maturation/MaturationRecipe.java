package net.okuri.qol.qolCraft.maturation;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;

import java.util.ArrayList;


public class MaturationRecipe {
    private final ArrayList<SuperItemData> ingredients = new ArrayList<>();

    private final Maturable resultClass;
    private final String recipeName;

    private double maxTemp = 1000;
    private double minTemp = -1000;

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

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

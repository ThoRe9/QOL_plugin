package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;

import java.util.ArrayList;

public class DistillationRecipe {
    // 注意：　ここでのingredients は、全てが必須なのではなく、どれか一つでもあれば良い。
    private final ArrayList<SuperItemData> ingredients = new ArrayList<>();
    private final Distillable resultClass;
    private final String recipeName;

    public DistillationRecipe(String name, Distillable resultClass) {
        this.resultClass = resultClass;
        this.recipeName = name;
    }

    public void addIngredient(SuperItemType superIngredient) {
        this.addIngredient(new SuperItemData(superIngredient));
    }

    public void addIngredient(SuperItemData data) {
        ingredients.add(data);
    }

    private String getRecipeName() {
        return recipeName;
    }

    public Distillable getResultClass() {
        return resultClass;
    }

    public ArrayList<SuperItemData> getIngredients() {
        return ingredients;
    }
}

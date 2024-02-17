package net.okuri.qol.alcohol;

import java.util.ArrayList;

/**
 * LiquorRecipe をここに登録する必要があります。
 */
public class LiquorRecipeController {
    public static final LiquorRecipeController instance = new LiquorRecipeController();
    private final ArrayList<LiquorRecipe> recipes = new ArrayList<>();

    private LiquorRecipeController() {
    }

    public ArrayList<LiquorRecipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(LiquorRecipe recipe) {
        recipes.add(recipe);
    }
}

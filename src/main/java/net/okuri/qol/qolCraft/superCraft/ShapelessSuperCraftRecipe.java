package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShapelessSuperCraftRecipe implements SuperRecipe {
    //不定形レシピはsetMatrixに登録した順にSuperItem のItemStackがわたされるので注意!!

    private final ItemStack result;
    private final String id;
    private SuperCraftable resultClass;
    private final ArrayList<SuperItemType> ingredients = new ArrayList<>();
    private SuperItemStack[] ingredientItems;

    public ShapelessSuperCraftRecipe(ItemStack result, String id) {
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean checkSuperRecipe(SuperItemStack[] matrix) {
        boolean flag = false;
        // ingredientItemsには、ingredientsの順番にmatrixの中身が入っている
        ingredientItems = new SuperItemStack[ingredients.size()];

        // matrixの中身のSuperItemTypeがingredientsとすべて一致するかチェック
        for (int i = 0; i < matrix.length; i++) {
            SuperItemStack itemStack = matrix[i];
            if (itemStack != null) {
                for (int j = 0; j < ingredients.size(); j++) {
                    SuperItemType ingredient = ingredients.get(j);
                    if (itemStack.isSimilar(ingredient)) {
                        ingredientItems[j] = itemStack;
                        flag = true;
                        break;
                    } else {
                        return false;
                    }
                }

            }
        }
        //ingredientItemsにすべての要素が入っているかチェック
        for (SuperItemStack itemStack : ingredientItems) {
            if (itemStack == null) {
                return false;
            }
        }
        return flag;
    }

    public void addIngredient(Material ingredient) {
        this.ingredients.add(SuperItemType.DEFAULT.setMaterial(ingredient));
    }

    public void addingredient(SuperItemType ingredient) {
        this.ingredients.add(ingredient);
    }

    public void setResultClass(SuperCraftable resultClass) {
        this.resultClass = resultClass;
    }

    public SuperCraftable getResultClass() {
        resultClass.setMatrix(ingredientItems, id);
        return resultClass;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }
}

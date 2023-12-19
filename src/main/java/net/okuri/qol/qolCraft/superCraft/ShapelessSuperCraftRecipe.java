package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShapelessSuperCraftRecipe implements SuperRecipe {
    //不定形レシピはsetMatrixに登録した順にSuperItem のItemStackがわたされるので注意!!


    private final String id;
    private SuperCraftable resultClass;
    private final ArrayList<SuperItemData> ingredients = new ArrayList<>();
    private SuperItemStack[] ingredientItems;

    private final ArrayList<SuperItemStack> returnItems = new ArrayList<>();

    public ShapelessSuperCraftRecipe(String id) {
        this.id = id;
    }

    @Override
    public boolean checkSuperRecipe(SuperItemStack[] matrix) {
        assert !ingredients.isEmpty();
        // ingredientItemsには、ingredientsの順番にmatrixの中身が入っている
        ingredientItems = new SuperItemStack[ingredients.size()];
        boolean[] isSeen = new boolean[ingredients.size()];
        for (int i = 0; i < isSeen.length; i++) {
            isSeen[i] = false;
        }

        // matrixの中身のSuperItemTypeがingredientsとすべて一致するかチェック
        for (int i = 0; i < matrix.length; i++) {
            SuperItemStack itemStack = matrix[i];
            if (itemStack != null) {
                for (int j = 0; j < ingredients.size(); j++) {
                    SuperItemData ingredient = ingredients.get(j);
                    if (itemStack.isSimilar(ingredient)) {
                        ingredientItems[j] = itemStack;
                        if (isSeen[j]) {
                            return false;
                        }
                        isSeen[j] = true;
                        break;
                    }
                    // ingredientsの最後まで見ても一致するものがなかった場合
                    if (j == ingredients.size() - 1) {
                        return false;
                    }
                }
            }
        }
        //ingredientItemsにすべての要素が入っているかチェック
        for (int i = 0; i < isSeen.length; i++) {
            if (!isSeen[i]) {
                return false;
            }
        }
        return true;
    }

    public void addIngredient(Material ingredient) {
        this.ingredients.add(new SuperItemData(ingredient));
    }

    public void addIngredient(SuperItemType ingredient) {
        this.ingredients.add(new SuperItemData(ingredient));
    }

    public void addIngredient(SuperItemData ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addIngredient(Tag<Material> ingredient) {
        this.ingredients.add(new SuperItemData(ingredient));
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
        return getResultClass().getSuperItem();
    }

    @Override
    public ArrayList<SuperItemStack> getReturnItems() {
        return returnItems;
    }

    public void addReturnItem(SuperItemStack itemStack) {
        returnItems.add(itemStack);
    }

}

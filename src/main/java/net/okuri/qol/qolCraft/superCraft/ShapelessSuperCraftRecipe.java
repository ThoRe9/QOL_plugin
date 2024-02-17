package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ShapelessSuperCraftRecipe implements SuperRecipe {
    //不定形レシピはsetMatrixに登録した順にSuperItem のItemStackがわたされるので注意!!
    // なお、getRawMatrixをtrueにすると、そのままのItemStackがわたされる

    private boolean getRawMatrix = false;
    private final String id;
    private Class<? extends SuperCraftable> resultClass;
    private SuperCraftable resultInstance;
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

        // matrixの中身のSuperItemTypeがingredientsとすべて一致するかチェック
        for (int i = 0; i < matrix.length; i++) {
            SuperItemStack itemStack = matrix[i];
            if (itemStack != null) {
                for (int j = 0; j < ingredients.size(); j++) {
                    SuperItemData ingredient = ingredients.get(j);
                    if (ingredient.isSimilar(itemStack.getSuperItemData()) && !isSeen[j]) {
                        ingredientItems[j] = itemStack;
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
        Bukkit.getLogger().info(this.getId());
        try {
            Constructor<? extends SuperCraftable> constructor = this.resultClass.getConstructor();
            this.resultInstance = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (getRawMatrix) {
            this.ingredientItems = matrix;
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

    public void addIngredient(SuperItemTag tag) {
        this.ingredients.add(new SuperItemData(tag));
    }

    public SuperCraftable getResultClass() {
        resultInstance.setMatrix(ingredientItems, id);
        return resultInstance;
    }

    public void setResultClass(SuperCraftable resultClass) {
        this.resultClass = resultClass.getClass();
        this.resultInstance = resultClass;
    }

    @Override

    public String getId() {
        return id;
    }

    @Override
    public @NotNull ItemStack getResult() {
        return resultInstance.getSuperItem();
    }

    @Override
    public ArrayList<SuperItemStack> getReturnItems() {
        return returnItems;
    }

    public void addReturnItem(SuperItemStack itemStack) {
        returnItems.add(itemStack);
    }

    public void setGetRawMatrix(boolean getRawMatrix) {
        this.getRawMatrix = getRawMatrix;
    }

}

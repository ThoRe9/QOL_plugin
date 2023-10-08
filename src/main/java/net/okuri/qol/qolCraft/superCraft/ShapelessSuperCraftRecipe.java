package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShapelessSuperCraftRecipe implements SuperRecipe {
    //不定形レシピはsetMatrixに登録した順にSuperItem のItemStackがわたされるので注意!!

    private final ItemStack result;
    private final String id;
    private SuperCraftable resultClass;
    private final ArrayList<Material> ingredients = new ArrayList<>();
    private final ArrayList<SuperItemType> superIngredients = new ArrayList<>();
    private ItemStack[] superIngredientItems;

    public ShapelessSuperCraftRecipe(ItemStack result, String id) {
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean checkSuperRecipe(ItemStack[] matrix) {
        boolean flag = false;
        superIngredientItems = new ItemStack[superIngredients.size()];
        for (int i = 0; i < superIngredients.size(); i++) {
            SuperItemType searchType = superIngredients.get(i);
            for (int j = 0; j < matrix.length; j++) {
                ItemStack itemStack = matrix[j];
                if (itemStack != null) {
                    if (PDCC.has(itemStack.getItemMeta(), PDCKey.TYPE)) {
                        SuperItemType type = SuperItemType.valueOf(PDCC.get(itemStack.getItemMeta(), PDCKey.TYPE));
                        if (type == searchType) {
                            superIngredientItems[i] = itemStack;
                            matrix[j] = null;
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        // matrixの中身をingredientsと比較
        for (Material ingredient : ingredients) {
            for (int j = 0; j < matrix.length; j++) {
                ItemStack itemStack = matrix[j];
                if (itemStack != null) {
                    if (itemStack.getType() == ingredient) {
                        matrix[j] = null;
                        flag = true;
                        break;
                    }
                }
            }
        }
        // matrixが空になっているかチェック
        for (ItemStack itemStack : matrix) {
            if (itemStack != null) {
                return false;
            }
        }
        return flag;
    }

    public void addIngredient(Material ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addSuperIngredient(SuperItemType ingredient) {
        this.superIngredients.add(ingredient);
    }

    public void setResultClass(SuperCraftable resultClass) {
        this.resultClass = resultClass;
    }

    public SuperCraftable getResultClass() {
        resultClass.setMatrix(superIngredientItems, id);
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

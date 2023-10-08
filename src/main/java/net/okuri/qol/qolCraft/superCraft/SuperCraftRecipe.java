package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuperCraftRecipe implements SuperRecipe {
    // 特殊レシピの判定ができる
    // Materialだけでなく、SuperItemTypeも判定できる
    private String id;
    private ItemStack[] matrix = new ItemStack[9];
    public ItemStack result = null;
    public String[] shape = new String[3];
    public Map<Character, Material> ingredients = new HashMap<Character, Material>();
    public Map<Character, SuperItemType> superIngredients = new HashMap<Character, SuperItemType>();
    private SuperCraftable resultClass = null;

    public SuperCraftRecipe(ItemStack result, String id) {
        this.result = result;
        this.id = id;
    }

    // レシピの判定
    @Override
    public boolean checkSuperRecipe(ItemStack[] matrix) {
        this.matrix = matrix;
        //Bukkit.getServer().getLogger().info("checkSuperRecipe");

        for (int i = 0; i < 3; i++) {
            String row = shape[i];
            for (int j = 0; j < 3; j++) {

                int checkIndex = i * 3 + j;
                char ingredient = row.charAt(j);
                if (ingredient == ' ') {
                    //Bukkit.getServer().getLogger().info("nullAndAirSlotDetected");
                    //Bukkit.getServer().getLogger().info(String.valueOf(checkIndex));
                    if (matrix[checkIndex] != null) {
                        //Bukkit.getServer().getLogger().info(matrix[checkIndex].toString());
                        return false;
                    }
                } else if (ingredients.containsKey(ingredient)) {
                    if (matrix[checkIndex] == null) {
                        //Bukkit.getServer().getLogger().info("nullSlotDetected");
                        return false;
                    }
                    if (matrix[checkIndex].getType() != ingredients.get(ingredient)) {
                        //Bukkit.getServer().getLogger().info("differentMaterialDetected");
                        return false;
                    }
                } else if (superIngredients.containsKey(ingredient)) {
                    if (matrix[checkIndex] == null) {
                        // Bukkit.getServer().getLogger().info("nullSlotDetected");
                        return false;
                    }
                    if (!PDCC.has(matrix[checkIndex].getItemMeta(), PDCKey.TYPE)) {
                        //Bukkit.getServer().getLogger().info("noTypeDetected");
                        return false;
                    }
                    if (!Objects.equals(PDCC.get(matrix[checkIndex].getItemMeta(), PDCKey.TYPE), superIngredients.get(ingredient).toString())) {
                        //Bukkit.getServer().getLogger().info("differentTypeDetected");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setShape(String[] shape) {
        this.shape = shape;
    }

    public void setIngredients(Map<Character, Material> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSuperIngredients(Map<Character, SuperItemType> superIngredients) {
        this.superIngredients = superIngredients;
    }

    public void addIngredient(Character key, Material value) {
        this.ingredients.put(key, value);
    }

    public void addSuperIngredient(Character key, SuperItemType value) {
        this.superIngredients.put(key, value);
    }

    public SuperCraftRecipe getRecipe() {
        return this;
    }

    public void setResultClass(SuperCraftable resultClass) {
        this.resultClass = resultClass;
    }

    public SuperCraftable getResultClass() {
        this.resultClass.setMatrix(this.matrix, id);
        return this.resultClass;
    }

    @Override
    public ItemStack getResult() {
        return this.resultClass.getSuperItem();
    }


}

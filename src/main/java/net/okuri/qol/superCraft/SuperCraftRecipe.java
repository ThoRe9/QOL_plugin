package net.okuri.qol.superCraft;

import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuperCraftRecipe {
    // 特殊レシピの判定ができる
    // Materialだけでなく、SuperItemTypeも判定できる

    public ItemStack result = null;
    public String[] shape = new String[3];
    public Map<Character, Material> ingredients = new HashMap<Character, Material>();
    public Map<Character, SuperItemType> superIngredients = new HashMap<Character, SuperItemType>();
    private SuperCraftable resultClass = null;

    public SuperCraftRecipe(ItemStack result, String[] shape, Map<Character, Material> ingredients, Map<Character, SuperItemType> superIngredients) {
        this.result = result;
        this.shape = shape;
        this.ingredients = ingredients;
        this.superIngredients = superIngredients;
    }

    public SuperCraftRecipe(ItemStack result){
        this.result = result;
    }

    // レシピの判定
    public boolean checkDrinkRecipe(ItemStack[] matrix){

        //Bukkit.getServer().getLogger().info("checkDrinkRecipe");

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
                    if (!matrix[checkIndex].getItemMeta().getPersistentDataContainer().has(SuperItemType.typeKey, PersistentDataType.STRING)) {
                        //Bukkit.getServer().getLogger().info("noTypeDetected");
                        return false;
                    }
                    if (!Objects.equals(matrix[checkIndex].getItemMeta().getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING), superIngredients.get(ingredient).toString())) {
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

    public SuperCraftRecipe(String[] shape) {
        this.shape = shape;
    }

    public SuperCraftRecipe() {
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
        return this.resultClass;
    }



}

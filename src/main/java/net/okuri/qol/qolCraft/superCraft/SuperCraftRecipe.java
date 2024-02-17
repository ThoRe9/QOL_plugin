package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuperCraftRecipe implements SuperRecipe {
    // 特殊レシピの判定ができる
    // Materialだけでなく、SuperItemTypeも判定できる
    private String id;
    public Map<Character, SuperItemData> ingredients = new HashMap<>();
    public String[] shape = new String[3];
    private SuperItemStack[] matrix = new SuperItemStack[9];
    private Class<? extends SuperCraftable> resultClass = null;
    private SuperCraftable resultInstance = null;
    private final ArrayList<SuperItemStack> returnItems = new ArrayList<>();

    public SuperCraftRecipe(String id) {
        this.id = id;
    }

    // レシピの判定
    @Override
    public boolean checkSuperRecipe(SuperItemStack[] matrix) {

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
                    if (!matrix[checkIndex].isSimilar(ingredients.get(ingredient))) {
                        //Bukkit.getServer().getLogger().info("differentMaterialDetected");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        try {
            Constructor<? extends SuperCraftable> constructor = this.resultClass.getConstructor();
            this.resultInstance = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
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

    public void setIngredients(Map<Character, SuperItemData> ingredients) {
        this.ingredients = ingredients;
    }


    public void addIngredient(Character key, Material value) {
        this.addIngredient(key, new SuperItemData(value));
    }

    public void addIngredient(Character key, SuperItemType value) {
        this.addIngredient(key, new SuperItemData(value));
    }

    public void addIngredient(Character key, SuperItemData value) {
        this.ingredients.put(key, value);
    }

    public void addIngredient(Character key, Tag<Material> value) {
        this.addIngredient(key, new SuperItemData(value));
    }

    public void addIngredient(Character key, SuperItemTag value) {
        this.addIngredient(key, new SuperItemData(value));
    }

    public SuperCraftRecipe getRecipe() {
        return this;
    }

    public SuperCraftable getResultClass() {

        resultInstance.setMatrix(this.matrix, id);
        return resultInstance;
    }

    public Map<Character, SuperItemData> getIngredients() {
        return this.ingredients;
    }

    public void setResultClass(SuperCraftable resultClass) {
        this.resultClass = resultClass.getClass();
        this.resultInstance = resultClass;
    }

    @Override
    public @NotNull SuperItemStack getResult() {
        return resultInstance.getSuperItem();
    }

    public SuperItemData getResultData() {
        return resultInstance.getSuperItem().getSuperItemData();
    }

    public String[] getShape() {
        return this.shape;
    }

    @Override
    public ArrayList<SuperItemStack> getReturnItems() {
        return this.returnItems;
    }

    public void addReturnItem(SuperItemStack item) {
        this.returnItems.add(item);
    }


}

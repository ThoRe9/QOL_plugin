package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class DistillationRecipe {
    // 注意：　ここでのingredients は、全てが必須なのではなく、どれか一つでもあれば良い。
    private final ArrayList<SuperItemData> ingredients = new ArrayList<>();
    private final Class<? extends Distillable> resultClass;
    private Distillable resultInstance;
    private final String recipeName;

    public DistillationRecipe(String name, Distillable resultClass) {
        this.resultClass = resultClass.getClass();
        this.resultInstance = resultClass;
        this.recipeName = name;
    }

    public boolean isDistillable(SuperItemData ingredient) {
        for (SuperItemData superItemData : ingredients) {
            if (superItemData.isSimilar(ingredient)) {
                try {
                    Constructor<? extends Distillable> constructor = this.resultClass.getConstructor();
                    this.resultInstance = constructor.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }


                return true;
            }
        }
        return false;
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
        return this.resultInstance;
    }

    public ArrayList<SuperItemData> getIngredients() {
        return ingredients;
    }
}

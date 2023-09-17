package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Distillation implements Listener {
    public static NamespacedKey distillationRecipeKey = new NamespacedKey("qol", "distillation_recipe");
    private ArrayList<DistillationRecipe> distillationRecipes = new ArrayList<>();
    @EventHandler
    // Distillationのレシピを登録する
    public void FurnaceSmeltEvent(FurnaceSmeltEvent event) {
        if (event.getRecipe().getKey().equals(distillationRecipeKey)) {
            ItemStack ingredient = ((Furnace)event.getBlock().getState()).getInventory().getSmelting();
            if (ingredient.getItemMeta().getPersistentDataContainer().has(SuperItemType.typeKey, PersistentDataType.STRING)){
                SuperItemType ingredientType = SuperItemType.valueOf(ingredient.getItemMeta().getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING));
                // ingredientTypeがDistillation可能なアイテムならば、処理を実行
                for (DistillationRecipe distillationRecipe : distillationRecipes) {
                    if (distillationRecipe.getIngredients().contains(ingredientType)) {
                        Distillable resultClass = distillationRecipe.getResultClass();
                        resultClass.setDistillationVariable(ingredient, event.getBlock().getLocation().getBlock().getTemperature(), event.getBlock().getLocation().getBlock().getHumidity());
                        event.setResult(resultClass.getSuperItem());
                        return;
                    }
                }
            }
        }
    }

    public void addDistillationRecipe(DistillationRecipe distillationRecipe){
        this.distillationRecipes.add(distillationRecipe);
    }

}

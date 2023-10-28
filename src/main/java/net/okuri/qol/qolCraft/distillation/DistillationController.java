package net.okuri.qol.qolCraft.distillation;

import net.okuri.qol.event.DistillationEvent;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import java.util.ArrayList;

public class DistillationController implements Listener {
    public static NamespacedKey distillationRecipeKey = new NamespacedKey("qol", "distillation_recipe");
    private final ArrayList<DistillationRecipe> distillationRecipes = new ArrayList<>();
    @EventHandler
    // Distillationのレシピを登録する
    public void FurnaceSmeltEvent(FurnaceSmeltEvent event) {
        if (event.getRecipe().getKey().equals(distillationRecipeKey)) {
            SuperItemStack ingredient = new SuperItemStack(((Furnace) event.getBlock().getState()).getInventory().getSmelting());
            // ingredientTypeがDistillation可能なアイテムならば、処理を実行
            for (DistillationRecipe distillationRecipe : distillationRecipes) {
                if (distillationRecipe.getIngredients().contains(ingredient.getSuperItemType())) {
                    Distillable resultClass = distillationRecipe.getResultClass();
                    resultClass.setDistillationVariable(ingredient, event.getBlock().getLocation().getBlock().getTemperature(), event.getBlock().getLocation().getBlock().getHumidity());
                    DistillationEvent distillationEvent = new DistillationEvent(event, resultClass, ((SuperItem) resultClass).getSuperItem());
                    //Bukkit.getLogger().info("DistillationEvent called");
                    Bukkit.getServer().getPluginManager().callEvent(distillationEvent);
                }
            }
        }
    }

    public void addDistillationRecipe(DistillationRecipe distillationRecipe){
        this.distillationRecipes.add(distillationRecipe);
    }

}

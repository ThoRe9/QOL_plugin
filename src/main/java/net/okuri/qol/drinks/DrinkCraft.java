package net.okuri.qol.drinks;

import net.okuri.qol.drinks.distillation.Distillation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class DrinkCraft implements Listener {

    private final ArrayList<DrinkCraftRecipe> drinkCraftRecipes = new ArrayList<>();

    public void addDrinkCraftRecipe(DrinkCraftRecipe drinkCraftRecipe) {
        drinkCraftRecipes.add(drinkCraftRecipe);
    }

    @EventHandler
    public void PrepareItemCraftEvent(PrepareItemCraftEvent event) {
        // drinkCraftRecipes に登録されているレシピをチェック
        // レシピが一致したら、logに出力
        Bukkit.getServer().getLogger().info("PrepareItemCraftEvent");
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = event.getInventory().getMatrix();

        // レシピの判定
        for (DrinkCraftRecipe drinkCraftRecipe : drinkCraftRecipes) {
            if (drinkCraftRecipe.checkDrinkRecipe(matrix)) {
                Bukkit.getLogger().info("DrinkCraftRecipe matched!");
                DrinkCraftable result = drinkCraftRecipe.getResultClass();
                result.setMatrix(matrix);
                inventory.setResult(result.getSuperItem());
                return;
            }
        }

    }



    @EventHandler
    // Distillationのレシピを登録する
    public void FurnaceSmeltEvent(FurnaceSmeltEvent event) {
        if (event.getRecipe().getKey().equals(new NamespacedKey("qol", "distillation"))){
            ItemStack ingredient = ((Furnace)event.getBlock().getState()).getInventory().getSmelting();
            // ingredientがDistillation可能なアイテムかどうかを判定
            if (new Distillation(ingredient, event.getBlock()).isDistillationable()){
                // Distillation可能なら、Distillationの処理を行う
                ItemStack result = new Distillation(ingredient, event.getBlock()).getDistillationResult();
                event.setResult(result);

            } else{
                // Distillation不可能なら、Distillationの処理を行わない
                ((Furnace)event.getBlock().getState()).getInventory().setSmelting(null);
                event.setCancelled(true);
            }

        }
    }


    @EventHandler
    public void CraftItemEvent(CraftItemEvent event) {

    }
}


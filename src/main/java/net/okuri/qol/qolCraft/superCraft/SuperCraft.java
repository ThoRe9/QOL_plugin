package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.qolCraft.distillation.Distillation;
import org.bukkit.Bukkit;
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


public class SuperCraft implements Listener {

    private final ArrayList<SuperCraftRecipe> superCraftRecipes = new ArrayList<>();

    public void addSuperCraftRecipe(SuperCraftRecipe superCraftRecipe) {
        superCraftRecipes.add(superCraftRecipe);
    }

    @EventHandler
    public void PrepareItemCraftEvent(PrepareItemCraftEvent event) {
        // superCraftRecipes に登録されているレシピをチェック
        // レシピが一致したら、logに出力
        //Bukkit.getServer().getLogger().info("PrepareItemCraftEvent");
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = event.getInventory().getMatrix();

        // レシピの判定
        for (SuperCraftRecipe superCraftRecipe : superCraftRecipes) {
            if (superCraftRecipe.checkSuperRecipe(matrix)) {
                Bukkit.getLogger().info("SuperCraftRecipe matched!");
                SuperCraftable result = superCraftRecipe.getResultClass();
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


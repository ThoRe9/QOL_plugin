package net.okuri.qol.qolCraft.superCraft;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class SuperCraft implements Listener {

    private final ArrayList<SuperCraftRecipe> superCraftRecipes = new ArrayList<>();
    private final ArrayList<ShapelessSuperCraftRecipe> shapelessSuperCraftRecipes = new ArrayList<>();

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
        // 不定形レシピの判定
        for (ShapelessSuperCraftRecipe shapelessSuperCraftRecipe : shapelessSuperCraftRecipes) {
            if (shapelessSuperCraftRecipe.checkSuperRecipe(matrix)) {
                Bukkit.getLogger().info("ShapelessSuperCraftRecipe matched!");
                SuperCraftable result = shapelessSuperCraftRecipe.getResultClass();
                inventory.setResult(result.getSuperItem());
                return;
            }
        }
    }
}


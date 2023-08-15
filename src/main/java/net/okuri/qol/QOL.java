package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.drinks.DrinkCraftRecipe;
import net.okuri.qol.drinks.DrinkCraft;
import net.okuri.qol.drinks.DrinkCraftType;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class QOL extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // drinkCraftsには特殊レシピを登録する
        DrinkCraft drinkCraft = new DrinkCraft();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(drinkCraft, this);
        getServer().getPluginManager().registerEvents(new SignFunction(), this);
        registerRecipes(drinkCraft);
        getCommand("getenv").setExecutor(new Commands());
        getCommand("matsign").setExecutor(new Commands());

        FurnaceRecipe distillationRecipe = new FurnaceRecipe(new NamespacedKey("qol","distillation"), new ItemStack(Material.POTION, 1), Material.POTION, 0.0f, 200);
        Bukkit.addRecipe(distillationRecipe);
        getLogger().info("QOL Plugin Enabled");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QOL Plugin Disabled");
    }

    public void registerRecipes(DrinkCraft drinkCraft) {
    //ここに特殊レシピ(作業台)を登録する

        // Whisky Ingredient
        ItemStack whisky = new ItemStack(Material.POTION, 1);
        ItemMeta whiskyMeta = whisky.getItemMeta();
        Component display;
        display = Component.text("Whisky Ingredients").color(NamedTextColor.DARK_GRAY);
        whiskyMeta.displayName(display);
        whisky.setItemMeta(whiskyMeta);

        DrinkCraftRecipe whiskyRecipe = new DrinkCraftRecipe(whisky);
        whiskyRecipe.setShape(new String[]{" W ", " B ", " C "});
        whiskyRecipe.addSuperIngredient('W', SuperItemType.BARLEY);
        whiskyRecipe.addIngredient('B', Material.WATER_BUCKET);
        whiskyRecipe.addSuperIngredient('C', SuperItemType.COAL);
        whiskyRecipe.setDrinkCraftType(DrinkCraftType.WHISKY_INGREDIENT);

        drinkCraft.addDrinkCraftRecipe(whiskyRecipe);

        // WhiskyWithIce
        ItemStack whiskyWithIce = new ItemStack(Material.POTION, 1);
        DrinkCraftRecipe whiskyWithIceRecipe = new DrinkCraftRecipe(whiskyWithIce);
        whiskyWithIceRecipe.setShape(new String[]{" I ", " W ", "BBB"});
        whiskyWithIceRecipe.addSuperIngredient('W', SuperItemType.WHISKY);
        whiskyWithIceRecipe.addIngredient('I', Material.ICE);
        whiskyWithIceRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        whiskyWithIceRecipe.setDrinkCraftType(DrinkCraftType.WHISKY_WITH_ICE);

        drinkCraft.addDrinkCraftRecipe(whiskyWithIceRecipe);

    }

    public JavaPlugin getPlugin() {
        return this;
    }
}

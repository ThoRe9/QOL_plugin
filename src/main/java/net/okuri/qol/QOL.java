package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.drinks.*;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

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

        //test
        ShapedRecipe test = new ShapedRecipe(new NamespacedKey("qol", "test"), new ItemStack(Material.POTION, 1));
        test.shape(" W ", " B ", " C ");
        test.setIngredient('W', Material.WHEAT);
        test.setIngredient('B', Material.WATER_BUCKET);
        test.setIngredient('C', Material.COAL);
        Bukkit.addRecipe(test);

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
        whiskyRecipe.setResultClass(new WhiskyIngredient());
        drinkCraft.addDrinkCraftRecipe(whiskyRecipe);

        // WhiskyWithIce
        ItemStack whiskyWithIce = new ItemStack(Material.POTION, 1);
        DrinkCraftRecipe whiskyWithIceRecipe = new DrinkCraftRecipe(whiskyWithIce);
        whiskyWithIceRecipe.setShape(new String[]{" I ", " W ", "BBB"});
        whiskyWithIceRecipe.addSuperIngredient('W', SuperItemType.WHISKY);
        whiskyWithIceRecipe.addIngredient('I', Material.ICE);
        whiskyWithIceRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        whiskyWithIceRecipe.setDrinkCraftType(DrinkCraftType.WHISKY_WITH_ICE);
        whiskyWithIceRecipe.setResultClass(new WhiskyWithIce());
        drinkCraft.addDrinkCraftRecipe(whiskyWithIceRecipe);

        // Soda
        ItemStack soda = new ItemStack(Material.POTION, 1);
        DrinkCraftRecipe sodaRecipe = new DrinkCraftRecipe(soda);
        sodaRecipe.setShape(new String[]{"CCC", " W ", "BBB"});
        sodaRecipe.addSuperIngredient('C', SuperItemType.COAL);
        sodaRecipe.addIngredient('W', Material.WATER_BUCKET);
        sodaRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        sodaRecipe.setResultClass(new Soda());
        drinkCraft.addDrinkCraftRecipe(sodaRecipe);

        //Highball
        ItemStack highball = new ItemStack(Material.POTION, 1);
        DrinkCraftRecipe highballRecipe = new DrinkCraftRecipe(highball);
        highballRecipe.setShape(new String[]{" I ", " W ", "SSS"});
        highballRecipe.addSuperIngredient('W', SuperItemType.WHISKY);
        highballRecipe.addIngredient('I', Material.ICE);
        highballRecipe.addSuperIngredient('S', SuperItemType.SODA);
        highballRecipe.setResultClass(new Highball());
        drinkCraft.addDrinkCraftRecipe(highballRecipe);

    }

    public JavaPlugin getPlugin() {
        return this;
    }
}

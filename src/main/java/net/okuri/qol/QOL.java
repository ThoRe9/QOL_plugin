package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.drinks.*;
import net.okuri.qol.foods.BarleyBread;
import net.okuri.qol.foods.Bread;
import net.okuri.qol.foods.RyeBread;
import net.okuri.qol.superCraft.SuperCraft;
import net.okuri.qol.superCraft.SuperCraftRecipe;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;


public final class QOL extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // drinkCraftsには特殊レシピを登録する
        SuperCraft superCraft = new SuperCraft();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getServer().getPluginManager().registerEvents(superCraft, this);
        getServer().getPluginManager().registerEvents(new SignFunction(), this);
        registerRecipes(superCraft);
        getCommand("getenv").setExecutor(new Commands());
        getCommand("matsign").setExecutor(new Commands());

        // bukkitRunnableを起動
        Alcohol alc = new Alcohol();
        alc.runTaskTimer(this, 0, 1200);

        // distillationのレシピを登録
        FurnaceRecipe distillationRecipe = new FurnaceRecipe(new NamespacedKey("qol","distillation"), new ItemStack(Material.POTION, 1), Material.POTION, 0.0f, 200);
        Bukkit.addRecipe(distillationRecipe);
        getLogger().info("QOL Plugin Enabled");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QOL Plugin Disabled");
    }

    public void registerRecipes(SuperCraft superCraft) {
    //ここに特殊レシピ(作業台)を登録する
        // 注意: VANILLAのレシピも登録しておくこと！！材料増えるバグが発生するよ。

        // Whisky Ingredient
        ItemStack whisky = new ItemStack(Material.POTION, 1);
        PotionMeta whiskyMeta = (PotionMeta)whisky.getItemMeta();
        whiskyMeta.setColor(Color.fromRGB(255,255,255));
        Component display;
        display = Component.text("Whisky Ingredients").color(NamedTextColor.GOLD);
        whiskyMeta.displayName(display);
        whiskyMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        whisky.setItemMeta(whiskyMeta);
        SuperCraftRecipe whiskyRecipe = new SuperCraftRecipe(whisky);
        whiskyRecipe.setShape(new String[]{" W ", " B ", " C "});
        whiskyRecipe.addSuperIngredient('W', SuperItemType.BARLEY);
        whiskyRecipe.addIngredient('B', Material.WATER_BUCKET);
        whiskyRecipe.addSuperIngredient('C', SuperItemType.COAL);
        whiskyRecipe.setResultClass(new WhiskyIngredient());
        superCraft.addSuperCraftRecipe(whiskyRecipe);

        ShapedRecipe WVR = new ShapedRecipe(new NamespacedKey("qol","whisky_ingredient"), whisky);
        WVR.shape(" W ", " B ", " C ");
        WVR.setIngredient('W', Material.WHEAT);
        WVR.setIngredient('B', Material.WATER_BUCKET);
        WVR.setIngredient('C', Material.COAL);
        Bukkit.addRecipe(WVR);

        // WhiskyWithIce
        ItemStack whiskyWithIce = new ItemStack(Material.POTION, 1);
        PotionMeta wwim = (PotionMeta)whiskyWithIce.getItemMeta();
        wwim.setColor(Color.fromRGB(170,70,10));
        wwim.displayName(Component.text("Whisky With Ice").color(NamedTextColor.GOLD));
        wwim.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        whiskyWithIce.setItemMeta(wwim);
        SuperCraftRecipe whiskyWithIceRecipe = new SuperCraftRecipe(whiskyWithIce);
        whiskyWithIceRecipe.setShape(new String[]{" I ", " W ", "BBB"});
        whiskyWithIceRecipe.addSuperIngredient('W', SuperItemType.WHISKY);
        whiskyWithIceRecipe.addIngredient('I', Material.ICE);
        whiskyWithIceRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        whiskyWithIceRecipe.setResultClass(new WhiskyWithIce());
        superCraft.addSuperCraftRecipe(whiskyWithIceRecipe);

        ShapedRecipe WWIR = new ShapedRecipe(new NamespacedKey("qol","whisky_with_ice"), whiskyWithIce);
        WWIR.shape(" I ", " W ", "BBB");
        WWIR.setIngredient('I', Material.ICE);
        WWIR.setIngredient('W', Material.POTION);
        WWIR.setIngredient('B', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(WWIR);

        // Soda
        ItemStack soda = new ItemStack(Material.POTION, 1);
        PotionMeta sodaMeta = (PotionMeta)soda.getItemMeta();
        sodaMeta.setColor(Color.AQUA);
        sodaMeta.displayName(Component.text("Soda").color(NamedTextColor.AQUA));
        sodaMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        soda.setItemMeta(sodaMeta);
        SuperCraftRecipe sodaRecipe = new SuperCraftRecipe(soda);
        sodaRecipe.setShape(new String[]{"CCC", " W ", "BBB"});
        sodaRecipe.addSuperIngredient('C', SuperItemType.COAL);
        sodaRecipe.addIngredient('W', Material.WATER_BUCKET);
        sodaRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        sodaRecipe.setResultClass(new Soda());
        superCraft.addSuperCraftRecipe(sodaRecipe);

        ShapedRecipe SR = new ShapedRecipe(new NamespacedKey("qol","soda"), soda);
        SR.shape("CCC", " W ", "BBB");
        SR.setIngredient('C', Material.COAL);
        SR.setIngredient('W', Material.WATER_BUCKET);
        SR.setIngredient('B', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(SR);

        //Highball
        ItemStack highball = new ItemStack(Material.POTION, 1);
        PotionMeta highballMeta = (PotionMeta)highball.getItemMeta();
        highballMeta.setColor(Color.fromRGB(220,210,150));
        highballMeta.displayName(Component.text("Highball").color(NamedTextColor.GOLD));
        highballMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        highball.setItemMeta(highballMeta);
        SuperCraftRecipe highballRecipe = new SuperCraftRecipe(highball);
        highballRecipe.setShape(new String[]{" I ", " W ", "SSS"});
        highballRecipe.addSuperIngredient('W', SuperItemType.WHISKY);
        highballRecipe.addIngredient('I', Material.ICE);
        highballRecipe.addSuperIngredient('S', SuperItemType.SODA);
        highballRecipe.setResultClass(new Highball());
        superCraft.addSuperCraftRecipe(highballRecipe);

        ShapedRecipe HR = new ShapedRecipe(new NamespacedKey("qol","highball"), highball);
        HR.shape(" I ", " W ", "SSS");
        HR.setIngredient('I', Material.ICE);
        HR.setIngredient('W', Material.POTION);
        HR.setIngredient('S', Material.POTION);
        Bukkit.addRecipe(HR);

        // Bread
        ItemStack superBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superBreadRecipe = new SuperCraftRecipe(superBread);
        superBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBreadRecipe.addSuperIngredient('W', SuperItemType.WHEAT);
        superBreadRecipe.setResultClass(new Bread());
        superCraft.addSuperCraftRecipe(superBreadRecipe);
        // パンは元からレシピが存在するので以下略

        // RyeBread
        ItemStack superRyeBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superRyeBreadRecipe = new SuperCraftRecipe(superRyeBread);
        superRyeBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superRyeBreadRecipe.addSuperIngredient('W', SuperItemType.RYE);
        superRyeBreadRecipe.setResultClass(new RyeBread());
        superCraft.addSuperCraftRecipe(superRyeBreadRecipe);

        // BarleyBread
        ItemStack superBarleyBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superBarleyBreadRecipe = new SuperCraftRecipe(superBarleyBread);
        superBarleyBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBarleyBreadRecipe.addSuperIngredient('W', SuperItemType.BARLEY);
        superBarleyBreadRecipe.setResultClass(new BarleyBread());
        superCraft.addSuperCraftRecipe(superBarleyBreadRecipe);

        // BeerIngredient
        ItemStack beer = new ItemStack(Material.POTION, 1);
        PotionMeta beerMeta = (PotionMeta)beer.getItemMeta();
        beerMeta.setColor(Color.fromRGB(255,255,255));
        beerMeta.displayName(Component.text("Beer Ingredients").color(NamedTextColor.GOLD));
        beerMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        beer.setItemMeta(beerMeta);
        SuperCraftRecipe beerRecipe = new SuperCraftRecipe(beer);
        beerRecipe.setShape(new String[]{" W ", " B ", " V "});
        beerRecipe.addSuperIngredient('W', SuperItemType.BARLEY);
        beerRecipe.addIngredient('B', Material.WATER_BUCKET);
        beerRecipe.addIngredient('V', Material.VINE);
        beerRecipe.setResultClass(new BeerIngredient());
        superCraft.addSuperCraftRecipe(beerRecipe);

        ShapedRecipe BIR = new ShapedRecipe(new NamespacedKey("qol","beer_ingredient"), beer);
        BIR.shape(" W ", " B ", " V ");
        BIR.setIngredient('W', Material.WHEAT);
        BIR.setIngredient('B', Material.WATER_BUCKET);
        BIR.setIngredient('V', Material.VINE);
        Bukkit.addRecipe(BIR);

        // ストゼロ
        ItemStack st0 = new ItemStack(Material.POTION, 1);
        PotionMeta st0meta = (PotionMeta) st0.getItemMeta();
        st0meta.displayName(Component.text("Strong Zero").color(NamedTextColor.GREEN));
        st0meta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        st0.setItemMeta(st0meta);
        SuperCraftRecipe st0Recipe = new SuperCraftRecipe(st0);
        st0Recipe.setShape(new String[]{" B ", " R ", " W "});
        st0Recipe.addIngredient('B', Material.SWEET_BERRIES);
        st0Recipe.addSuperIngredient('R', SuperItemType.RICE);
        st0Recipe.addIngredient('W', Material.POTION);
        st0Recipe.setResultClass(new StrongZero());
        superCraft.addSuperCraftRecipe(st0Recipe);

        ShapedRecipe SZR = new ShapedRecipe(new NamespacedKey("qol","strong_zero"), st0);
        SZR.shape(" B "," R "," W ");
        SZR.setIngredient('B', Material.SWEET_BERRIES);
        SZR.setIngredient('R', Material.WHEAT);
        SZR.setIngredient('W', Material.POTION);
        Bukkit.addRecipe(SZR);



    }

    public JavaPlugin getPlugin() {
        return this;
    }
}

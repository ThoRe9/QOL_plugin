package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.alcohol.FermentationIngredient;
import net.okuri.qol.alcohol.LiquorIngredient;
import net.okuri.qol.alcohol.LiquorRecipe;
import net.okuri.qol.alcohol.LiquorRecipeController;
import net.okuri.qol.alcohol.resources.BarleyJuice;
import net.okuri.qol.alcohol.resources.Malt;
import net.okuri.qol.alcohol.resources.newBarley;
import net.okuri.qol.alcohol.taste.AlcoholTaste;
import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.alcohol.taste.MaltTaste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.help.Help;
import net.okuri.qol.help.HelpCommand;
import net.okuri.qol.help.HelpContent;
import net.okuri.qol.help.Page;
import net.okuri.qol.listener.*;
import net.okuri.qol.qolCraft.distillation.DistillationController;
import net.okuri.qol.qolCraft.distillation.DistillationRecipe;
import net.okuri.qol.qolCraft.maturation.MaturationController;
import net.okuri.qol.qolCraft.maturation.MaturationRecipe;
import net.okuri.qol.qolCraft.resource.ResourceController;
import net.okuri.qol.qolCraft.superCraft.DistributionCraftRecipe;
import net.okuri.qol.qolCraft.superCraft.ShapelessSuperCraftRecipe;
import net.okuri.qol.qolCraft.superCraft.SuperCraftController;
import net.okuri.qol.qolCraft.superCraft.SuperCraftRecipe;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.LiverHelper;
import net.okuri.qol.superItems.factory.foods.BarleyBread;
import net.okuri.qol.superItems.factory.foods.Bread;
import net.okuri.qol.superItems.factory.foods.RyeBread;
import net.okuri.qol.superItems.factory.tools.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class QOL extends JavaPlugin {

    private static QOL plugin;

    public static QOL getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QOL Plugin Disabled");
    }

    public void registerRecipes(SuperCraftController superCraft) {
    //ここに特殊レシピ(作業台)を登録する

        // Bread
        SuperCraftRecipe superBreadRecipe = new SuperCraftRecipe("bread");
        superBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBreadRecipe.addIngredient('W', SuperItemType.WHEAT);
        superBreadRecipe.setResultClass(new Bread());
        superCraft.addSuperCraftRecipe(superBreadRecipe);
        // パンは元からレシピが存在するので以下略

        // RyeBread
        SuperCraftRecipe superRyeBreadRecipe = new SuperCraftRecipe("rye_bread");
        superRyeBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superRyeBreadRecipe.addIngredient('W', SuperItemType.RYE);
        superRyeBreadRecipe.setResultClass(new RyeBread());
        superCraft.addSuperCraftRecipe(superRyeBreadRecipe);

        // BarleyBread
        SuperCraftRecipe superBarleyBreadRecipe = new SuperCraftRecipe("barley_bread");
        superBarleyBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBarleyBreadRecipe.addIngredient('W', SuperItemType.BARLEY);
        superBarleyBreadRecipe.setResultClass(new BarleyBread());
        superCraft.addSuperCraftRecipe(superBarleyBreadRecipe);

        // envGetter
        SuperCraftRecipe envGetterRecipe = new SuperCraftRecipe("env_getter");
        envGetterRecipe.setShape(new String[]{"IOI", "IRI", "III"});
        envGetterRecipe.addIngredient('I', Material.IRON_INGOT);
        envGetterRecipe.addIngredient('R', Material.COMPARATOR);
        envGetterRecipe.addIngredient('O', Material.OBSERVER);
        envGetterRecipe.setResultClass(new EnvGetter());
        superCraft.addSuperCraftRecipe(envGetterRecipe);

        // farmer tool adapter
        SuperCraftRecipe farmerToolAdapterRecipe = new SuperCraftRecipe("farmer_tool_adapter");
        farmerToolAdapterRecipe.setShape(new String[]{"SSS", "SIS", "SSS"});
        farmerToolAdapterRecipe.addIngredient('S', Material.WHEAT_SEEDS);
        farmerToolAdapterRecipe.addIngredient('I', Material.IRON_INGOT);
        farmerToolAdapterRecipe.setResultClass(new FarmerToolAdapter());
        superCraft.addSuperCraftRecipe(farmerToolAdapterRecipe);

        // miner tool adapter
        SuperCraftRecipe minerToolAdapterRecipe = new SuperCraftRecipe("miner_tool_adapter");
        minerToolAdapterRecipe.setShape(new String[]{"SSS", "SIS", "SSS"});
        minerToolAdapterRecipe.addIngredient('S', Material.COAL);
        minerToolAdapterRecipe.addIngredient('I', Material.IRON_INGOT);
        minerToolAdapterRecipe.setResultClass(new MinerToolAdapter());
        superCraft.addSuperCraftRecipe(minerToolAdapterRecipe);

        // farmer tool
        ShapelessSuperCraftRecipe farmerToolRecipe = new ShapelessSuperCraftRecipe("farmer_tool");
        farmerToolRecipe.addIngredient(Tag.ITEMS_HOES);
        farmerToolRecipe.addIngredient(SuperItemType.FARMER_TOOL);
        farmerToolRecipe.setResultClass(new FarmerTool());
        superCraft.addShapelessSuperCraftRecipe(farmerToolRecipe);

        // miner tool
        ShapelessSuperCraftRecipe minerToolRecipe = new ShapelessSuperCraftRecipe("miner_tool");
        minerToolRecipe.addIngredient(Tag.ITEMS_PICKAXES);
        minerToolRecipe.addIngredient(SuperItemType.MINER_TOOL);
        minerToolRecipe.setResultClass(new MinerTool());
        superCraft.addShapelessSuperCraftRecipe(minerToolRecipe);

        // liver helper
        SuperCraftRecipe liverHelperRecipe = new SuperCraftRecipe("liver_helper");
        liverHelperRecipe.setShape(new String[]{"PPP", "NNN", "BWB"});
        liverHelperRecipe.addIngredient('P', Material.PORKCHOP);
        liverHelperRecipe.addIngredient('N', Material.NETHER_WART);
        liverHelperRecipe.addIngredient('B', Material.BROWN_MUSHROOM);
        liverHelperRecipe.addIngredient('W', Material.POTION);
        liverHelperRecipe.setResultClass(new LiverHelper());
        superCraft.addSuperCraftRecipe(liverHelperRecipe);

        //liquor ingredient(初回)
        ShapelessSuperCraftRecipe li = new ShapelessSuperCraftRecipe("liquor_ingredient");
        li.addIngredient(SuperItemTag.LIQUOR_RESOURCE);
        li.addIngredient(Material.WATER_BUCKET);
        li.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li);

        //liquor ingredient(追加)
        ShapelessSuperCraftRecipe li2 = new ShapelessSuperCraftRecipe("liquor_ingredient2");
        li2.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        li2.addIngredient(SuperItemTag.LIQUOR_RESOURCE);
        li2.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li2);

        //liquor ingredient(追加)
        ShapelessSuperCraftRecipe li3 = new ShapelessSuperCraftRecipe("liquor_ingredient3");
        li3.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        li3.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        li3.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li3);

        //liquor ingredient(追加)
        ShapelessSuperCraftRecipe li4 = new ShapelessSuperCraftRecipe("liquor_ingredient4");
        li4.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        li4.addIngredient(Material.WATER_BUCKET);
        li4.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li4);

        // fermentation ingredient
        ShapelessSuperCraftRecipe fi = new ShapelessSuperCraftRecipe("fermentation_ingredient");
        fi.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        fi.addIngredient(SuperItemType.YEAST);
        fi.setResultClass(new FermentationIngredient());
        superCraft.addShapelessSuperCraftRecipe(fi);

        // Liquor
        ShapelessSuperCraftRecipe lq = new ShapelessSuperCraftRecipe("liquor");
        lq.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        lq.setResultClass(new net.okuri.qol.alcohol.Liquor());
        superCraft.addShapelessSuperCraftRecipe(lq);

        // LiquorGlass
        DistributionCraftRecipe lg = new DistributionCraftRecipe("liquor_glass");
        lg.setDistribution(new net.okuri.qol.alcohol.Liquor());
        lg.setReciver(new net.okuri.qol.alcohol.LiquorGlass());
        lg.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(lg);

        // BarleyJuice
        ShapelessSuperCraftRecipe bj = new ShapelessSuperCraftRecipe("barley_juice");
        bj.addIngredient(SuperItemType.NEW_BARLEY);
        bj.addIngredient(Material.WATER_BUCKET);
        bj.addIngredient(SuperItemType.YEAST);
        bj.setResultClass(new BarleyJuice());
        superCraft.addShapelessSuperCraftRecipe(bj);

        for (String s : superCraft.getRecipeList()) {
            getLogger().info(s);
        }
    }

    // Maturationのレシピを登録する
    private void registerMaturationRecipes(MaturationController maturation) {
        // ここにMaturationのレシピを登録する

        // Fermentation
        MaturationRecipe fermentationRecipe = new MaturationRecipe("Fermentation", new LiquorIngredient());
        fermentationRecipe.addIngredient(SuperItemType.FERMENTATION_INGREDIENT);
        maturation.addMaturationRecipe(fermentationRecipe);

        // Malt
        MaturationRecipe maltRecipe = new MaturationRecipe("Malt", new Malt());
        maltRecipe.addIngredient(SuperItemType.BARLEY_JUICE);
        maturation.addMaturationRecipe(maltRecipe);

    }

    // Distillationのレシピを登録する
    private void registerDistillationRecipes(DistillationController distillation) {
        // ここにDistillationのレシピを登録する

        // LiquorIngredient
        DistillationRecipe liquorIngredientRecipe = new DistillationRecipe("Liquor Ingredient", new LiquorIngredient());
        liquorIngredientRecipe.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        distillation.addDistillationRecipe(liquorIngredientRecipe);

    }


    private void registerSuperResources(ResourceController superResource) {
        // ここにSuperResourceを登録していく

        newBarley barley = new newBarley();
        superResource.addResource(barley);


    }

    private void registerLiquorRecipe(LiquorRecipeController controller) {
        // ここにLiquorRecipeを登録していく

        // whisky
        LiquorRecipe whiskyRecipe = new LiquorRecipe("whisky", Component.text("ウィスキー").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false), 0);
        whiskyRecipe.addMinimumTaste(MaltTaste.instance, 1.5);
        whiskyRecipe.setMinimumAlcohol(0.20);
        controller.addRecipe(whiskyRecipe);

    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        // config.ymlが存在しない場合はファイルに出力します。
        saveDefaultConfig();

        // drinkCraftsには特殊レシピを登録する
        SuperCraftController superCraft = SuperCraftController.getListener();
        MaturationController maturation = MaturationController.getListener();
        DistillationController distillation = DistillationController.getListener();
        ResourceController superResource = ResourceController.getListener();
        LiquorRecipeController liquorRecipe = LiquorRecipeController.instance;
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getServer().getPluginManager().registerEvents(new ConsumeListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new ProtectListener(), this);
        getServer().getPluginManager().registerEvents(new QOLSignListener(this), this);
        getServer().getPluginManager().registerEvents(superCraft, this);
        getServer().getPluginManager().registerEvents(maturation, this);
        getServer().getPluginManager().registerEvents(distillation, this);
        getServer().getPluginManager().registerEvents(superResource, this);

        registerRecipes(superCraft);
        registerMaturationRecipes(maturation);
        registerDistillationRecipes(distillation);
        registerSuperResources(superResource);
        registerLiquorRecipe(liquorRecipe);

        getCommand("getenv").setExecutor(new Commands(this));
        getCommand("matsign").setExecutor(new Commands(this));
        getCommand("givesuperitem").setExecutor(new Commands(this));
        getCommand("superwheat").setExecutor(new Commands(this));
        getCommand("alc").setExecutor(new Commands(this));
        getCommand("producer").setExecutor(new Commands(this));
        getCommand("qolhelp").setExecutor(new HelpCommand());

        // bukkitRunnableを起動
        Alcohol alc = new Alcohol(this);
        alc.runTaskTimer(this, 0, 1200);

        // distillationのレシピを登録
        FurnaceRecipe distillationRecipe = new FurnaceRecipe(new NamespacedKey("qol","distillation_recipe"), new ItemStack(Material.POTION, 1), Material.POTION, 0.0f, 200);
        Bukkit.addRecipe(distillationRecipe);

        // help.jsonが存在しない場合はファイルに出力します。
        Page page = new Page("debug", "sample");
        HelpContent content = new HelpContent();
        content.addContent("test");
        content.addContent("test2");
        content.addContent("test3");
        content.addContent("test4");
        page.addContent(content);
        HelpContent content2 = new HelpContent();
        content2.addContent("test");
        content2.setRecipeID("whisky_ingredient");
        page.addContent(content2);
        Help.addPage(page);
        try {
            Help.setDefaultPages();
            Help.loadPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Tasteを登録
        TasteController taste = TasteController.getController();
        taste.registerTaste(BarleyTaste.instance);
        taste.registerTaste(AlcoholTaste.instance);

        getLogger().info("QOL Plugin Enabled");


    }
}

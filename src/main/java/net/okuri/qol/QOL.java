package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.okuri.qol.alcohol.*;
import net.okuri.qol.alcohol.resources.*;
import net.okuri.qol.alcohol.resources.buff.LiquorResourceBuff;
import net.okuri.qol.alcohol.resources.buff.LiquorResourceBuffController;
import net.okuri.qol.alcohol.taste.*;
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
        li.addReturnItem(Material.BUCKET);
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
        li3.addReturnItem(Material.GLASS_BOTTLE);
        li3.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li3);

        //liquor ingredient(追加)
        ShapelessSuperCraftRecipe li4 = new ShapelessSuperCraftRecipe("liquor_ingredient4");
        li4.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        li4.addIngredient(Material.WATER_BUCKET);
        li4.addReturnItem(Material.BUCKET);
        li4.setResultClass(new LiquorIngredient());
        superCraft.addShapelessSuperCraftRecipe(li4);

        // fermentation ingredient
        ShapelessSuperCraftRecipe fi = new ShapelessSuperCraftRecipe("fermentation_ingredient");
        fi.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        fi.addIngredient(SuperItemType.YEAST);
        fi.addReturnItem(Material.GLASS_BOTTLE);
        fi.setResultClass(new FermentationIngredient());
        superCraft.addShapelessSuperCraftRecipe(fi);

        // Liquor
        ShapelessSuperCraftRecipe lq = new ShapelessSuperCraftRecipe("liquor");
        lq.addIngredient(SuperItemType.LIQUOR_INGREDIENT);
        lq.setResultClass(new net.okuri.qol.alcohol.Liquor());
        superCraft.addShapelessSuperCraftRecipe(lq);

        // BarleyJuice
        ShapelessSuperCraftRecipe bj = new ShapelessSuperCraftRecipe("barley_juice");
        bj.addIngredient(SuperItemType.BARLEY);
        bj.addIngredient(SuperItemType.YEAST);
        bj.setResultClass(new BarleyJuice());
        superCraft.addShapelessSuperCraftRecipe(bj);

        // PolishedRice
        ShapelessSuperCraftRecipe pr = new ShapelessSuperCraftRecipe("polished_rice");
        pr.addIngredient(SuperItemType.RICE);
        pr.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(pr);

        // PolishedRice 2
        ShapelessSuperCraftRecipe pr2 = new ShapelessSuperCraftRecipe("polished_rice2");
        pr2.addIngredient(SuperItemType.POLISHED_RICE);
        pr2.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(pr2);

        // Pouring
        DistributionCraftRecipe pouring = new DistributionCraftRecipe("pouring");
        pouring.setGetRawMatrix(true);
        pouring.setDistribution(new Liquor());
        pouring.setReceiver(new LiquorGlass());
        pouring.setBottle(SuperItemType.GLASS);
        superCraft.addDistributionCraftRecipe(pouring);

        // pouring2
        DistributionCraftRecipe pouring2 = new DistributionCraftRecipe("pouring2");
        pouring2.setGetRawMatrix(true);
        pouring2.setDistribution(new Liquor());
        pouring2.setReceiver(new LiquorGlass());
        pouring2.setBottle(SuperItemType.LIQUOR_GLASS);
        superCraft.addDistributionCraftRecipe(pouring2);

        // grapeJuice
        ShapelessSuperCraftRecipe grapeJuice = new ShapelessSuperCraftRecipe("grape_juice");
        grapeJuice.addIngredient(SuperItemType.GRAPE);
        grapeJuice.addIngredient(SuperItemType.YEAST);
        grapeJuice.setResultClass(new GrapeJuice());
        superCraft.addShapelessSuperCraftRecipe(grapeJuice);



        // glass(100~900ml)
        for (int i = 1; i < 10; i++) {
            ShapelessSuperCraftRecipe glass = new ShapelessSuperCraftRecipe(String.valueOf(i * 100));
            for (int j = 0; j < i; j++) {
                glass.addIngredient(Material.GLASS_BOTTLE);
            }
            glass.setResultClass(new Glass());
            superCraft.addShapelessSuperCraftRecipe(glass);
        }

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

        // GrapeJuice
        MaturationRecipe grapeJuiceRecipe = new MaturationRecipe("GrapeJuice", new FermentedGrape());
        grapeJuiceRecipe.addIngredient(SuperItemType.GRAPE_JUICE);
        maturation.addMaturationRecipe(grapeJuiceRecipe);

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

        Barley barley = new Barley();
        superResource.addResource(barley);

        Rye rye = new Rye();
        superResource.addResource(rye);

        Wheat wheat = new Wheat();
        superResource.addResource(wheat);

        Rice rice = new Rice();
        superResource.addResource(rice);

        Potato potato = new Potato();
        superResource.addResource(potato);

        SugarCane sugarCane = new SugarCane();
        superResource.addResource(sugarCane);

        Grape grape = new Grape();
        superResource.addResource(grape);

    }

    private void registerLiquorRecipe(LiquorRecipeController controller) {
        // ここにLiquorRecipeを登録していく

        // whisky
        LiquorRecipe whiskyRecipe = new LiquorRecipe("whisky", Component.text("ウィスキー").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false), 0);
        whiskyRecipe.addMinimumTaste(MaltTaste.instance, 1);
        whiskyRecipe.setMinimumAlcohol(0.20);
        whiskyRecipe.setDurationAmp(1.2);
        whiskyRecipe.setLevelAmp(1.2);
        whiskyRecipe.setCustomModelData(1);
        controller.addRecipe(whiskyRecipe);

        // Ale Beer
        LiquorRecipe aleBeerRecipe = new LiquorRecipe("ale_beer", Component.text("エールビール").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false), 1);
        aleBeerRecipe.addMinimumTaste(BarleyTaste.instance, 0.5);
        aleBeerRecipe.addMinimumTaste(HopTaste.instance, 0.1);
        aleBeerRecipe.setMinimumAlcohol(0.03);
        aleBeerRecipe.setMaximumAlcohol(0.2);
        aleBeerRecipe.setMaximumFermentation(1.1);
        aleBeerRecipe.setDurationAmp(1.7);
        aleBeerRecipe.setLevelAmp(1.7);
        aleBeerRecipe.setCustomModelData(2);
        controller.addRecipe(aleBeerRecipe);

        // Lager Beer
        LiquorRecipe lagerBeerRecipe = new LiquorRecipe("lager_beer", Component.text("ラガービール").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false), 2);
        lagerBeerRecipe.addMinimumTaste(BarleyTaste.instance, 0.5);
        lagerBeerRecipe.addMinimumTaste(HopTaste.instance, 0.1);
        lagerBeerRecipe.setMinimumAlcohol(0.03);
        lagerBeerRecipe.setMaximumAlcohol(0.2);
        lagerBeerRecipe.setMinimumFermentation(1.1);
        lagerBeerRecipe.setDurationAmp(1.2);
        lagerBeerRecipe.setLevelAmp(1.2);
        aleBeerRecipe.setCustomModelData(3);
        controller.addRecipe(lagerBeerRecipe);

        // Sake
        LiquorRecipe sakeRecipe = new LiquorRecipe("sake", Component.text("日本酒").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false), 3);
        sakeRecipe.addMinimumTaste(RiceTaste.instance, 0.5);
        sakeRecipe.setMinimumAlcohol(0.05);
        sakeRecipe.setMaximumAlcohol(0.2);
        sakeRecipe.setMinimumFermentation(1);
        sakeRecipe.setDurationAmp(1.2);
        sakeRecipe.setLevelAmp(1.2);
        sakeRecipe.setCustomModelData(4);
        controller.addRecipe(sakeRecipe);

        // Shochu
        LiquorRecipe shochuRecipe = new LiquorRecipe("shochu", Component.text("焼酎").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false), 4);
        shochuRecipe.addMinimumTaste(RiceTaste.instance, 1.0);
        shochuRecipe.setMinimumAlcohol(0.2);
        shochuRecipe.setDurationAmp(0.9);
        shochuRecipe.setLevelAmp(1.6);
        shochuRecipe.setCustomModelData(4);
        controller.addRecipe(shochuRecipe);

        // Wine
        LiquorRecipe wineRecipe = new LiquorRecipe("wine", Component.text("ワイン").color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false), 5);
        wineRecipe.addMinimumTaste(GrapeBitterness.instance, 0.5);
        wineRecipe.addMinimumTaste(GrapeSourness.instance, 0.1);
        wineRecipe.setDurationAmp(1.4);
        wineRecipe.setLevelAmp(0.9);
        controller.addRecipe(wineRecipe);

        // White wine
        LiquorRecipe whiteWineRecipe = new LiquorRecipe("white_wine", Component.text("白ワイン").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false), 6);
        whiteWineRecipe.addMinimumTaste(GrapeSourness.instance, 0.5);
        whiteWineRecipe.addMinimumTaste(GrapeBitterness.instance, 0.1);
        whiteWineRecipe.setDurationAmp(0.9);
        whiteWineRecipe.setLevelAmp(1.4);
        controller.addRecipe(whiteWineRecipe);

    }

    private void registerTastes(TasteController controller) {
        // ここにTasteを登録していく

        controller.registerTaste(BarleyTaste.instance);
        controller.registerTaste(MaltTaste.instance);
        controller.registerTaste(RyeTaste.instance);
        controller.registerTaste(RiceTaste.instance);
        controller.registerTaste(RiceSweetness.instance);
        controller.registerTaste(PotatoTaste.instance);
        controller.registerTaste(HopTaste.instance);
        controller.registerTaste(SugarTaste.instance);
        controller.registerTaste(RumTaste.instance);
        controller.registerTaste(GrapeSweetness.instance);
        controller.registerTaste(GrapeSourness.instance);
        controller.registerTaste(GrapeBitterness.instance);
        controller.registerTaste(SkyTaste.instance);
    }


    private void registerLiquorResourceBuffs(LiquorResourceBuffController controller) {
        // ここにLiquorResourceBuffを登録する
        LiquorResourceBuff skyBuff = new LiquorResourceBuff("天空", NamedTextColor.AQUA, SkyTaste.instance, 0.1);
        skyBuff.setHeader(Component.text("天空の").color(NamedTextColor.AQUA));
        skyBuff.setMinY(300);
        controller.registerBuff(skyBuff);
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
        TasteController tasteController = TasteController.getController();
        LiquorResourceBuffController liquorResourceBuffController = LiquorResourceBuffController.instance;
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
        registerTastes(tasteController);
        registerLiquorResourceBuffs(liquorResourceBuffController);

        getCommand("getenv").setExecutor(new Commands(this));
        getCommand("matsign").setExecutor(new Commands(this));
        getCommand("givesuperitem").setExecutor(new Commands(this));
        getCommand("superwheat").setExecutor(new Commands(this));
        getCommand("alc").setExecutor(new Commands(this));
        getCommand("producer").setExecutor(new Commands(this));
        getCommand("qolhelp").setExecutor(new HelpCommand());
        getCommand("drinkcost").setExecutor(new Commands(this));

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

        // Taste一覧のページを作成する
        Page tastePage = new Page("taste", "tasteの一覧を表示します");
        for (Taste taste : TasteController.getController().getTastes()) {
            HelpContent t = new HelpContent();
            t.addContent("● " + taste.getDisplayName());
            t.addContent("発酵しやすさ : " + String.format("%.2f", taste.getDegradability()) + "/1 日");
            t.addContent("蒸留での減少量 : " + String.format("%.2f", 1 - taste.getVolatility()) + "/1 回");
            if (taste.hasPotionInfo()) {
                t.addContent("ポーション効果 : " + taste.getEffectType());
                t.addContent("レベル : " + String.format("%.2f", taste.getEffectAmplifier()) + "/1p  時間 : " + String.format("%.2f", taste.getEffectDuration()) + "/1p");
            }
            if (taste.hasBuffInfo()) {
                t.addContent("ポーション時間のバフ : x" + String.format("%.2f", taste.getDurationAmplifier()) + "/1p");
                t.addContent("ポーションレベルのバフ : x" + String.format("%.2f", taste.getLevelAmplifier()) + "/1p");
            }
            if (taste.hasDelicacyBuff()) {
                t.addContent("くせの変化量 : " + String.format("%.2f", taste.getDelicacyBuff()) + "/1p/L");
            }
            if (taste.hasEffectRateAffection()) {
                t.addContent("傾向の変化 : " + String.format("%.2f", taste.getEffectRateAffection() - 1) + "/1p");
            }
            if (taste.hasBestFermentation()) {
                t.addContent("最適な発酵度 : " + String.format("%.2f", taste.getBestFermentation()) + "days");
            }
            if (taste.hasFermentationLine()) {
                t.addContent("このTasteは発酵させないと効果が表れません。");
            }
            tastePage.addContent(t);
        }
        Help.addPage(tastePage);

        // LiquorRecipe一覧のページを作成する
        Page liquorRecipePage = new Page("liquor_recipe", "liquorのレシピ一覧を表示します");
        for (LiquorRecipe recipe : LiquorRecipeController.instance.getRecipes()) {
            HelpContent t = new HelpContent();
            t.addContent("● " + PlainTextComponentSerializer.plainText().serialize(recipe.getName()));
            if (recipe.getMinimumAlcohol() > 0 || recipe.getMaximumAlcohol() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("  アルコール度 : ");
                if (recipe.getMinimumAlcohol() > 0) {
                    sb.append(String.format("%.2f", recipe.getMinimumAlcohol() * 100));
                }
                sb.append("% ~ ");
                if (recipe.getMaximumAlcohol() > 0) {
                    sb.append(String.format("%.2f", recipe.getMaximumAlcohol() * 100));
                }
                sb.append("%");
                t.addContent(sb.toString());
            }
            if (recipe.getMaximumFermentation() > 0 || recipe.getMinimumFermentation() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("  発酵度 : ");
                if (recipe.getMinimumFermentation() > 0) {
                    sb.append(String.format("%.2f", recipe.getMinimumFermentation()));
                }
                sb.append(" ~ ");
                if (recipe.getMaximumFermentation() > 0) {
                    sb.append(String.format("%.2f", recipe.getMaximumFermentation()));
                }
                t.addContent(sb.toString());
            }
            if (!recipe.getTasteStandards().isEmpty()) {
                for (LiquorRecipe.TasteStandardInfo taste : recipe.getTasteStandards()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("  ");
                    sb.append(taste.taste.getDisplayName()).append(" : ");
                    if (taste.min > 0) {
                        sb.append(String.format("%.2f", taste.min * 100));
                        sb.append("%");
                    }
                    sb.append(" ~ ");
                    if (taste.max > 0) {
                        sb.append(String.format("%.2f", taste.max * 100));
                        sb.append("%");
                    }
                    t.addContent(sb.toString());
                }
            }
            t.addContent("レシピ効果 : ");
            t.addContent("  発酵時間の倍率 : " + String.format("%.2f", recipe.getDurationAmp()) + "/1");
            t.addContent("  レベルの倍率 : " + String.format("%.2f", recipe.getLevelAmp()) + "/1");
            liquorRecipePage.addContent(t);
        }
        Help.addPage(liquorRecipePage);

        getLogger().info("QOL Plugin Enabled");


    }

}

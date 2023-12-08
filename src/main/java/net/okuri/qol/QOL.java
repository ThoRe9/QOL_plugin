package net.okuri.qol;

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
import net.okuri.qol.superItems.factory.FusionCraft;
import net.okuri.qol.superItems.factory.drinks.Horoyoi;
import net.okuri.qol.superItems.factory.drinks.LiverHelper;
import net.okuri.qol.superItems.factory.drinks.Soda;
import net.okuri.qol.superItems.factory.drinks.StrongZero;
import net.okuri.qol.superItems.factory.drinks.sake.*;
import net.okuri.qol.superItems.factory.drinks.spirits.Rum;
import net.okuri.qol.superItems.factory.drinks.spirits.RumIngredient;
import net.okuri.qol.superItems.factory.drinks.spirits.RumStraight;
import net.okuri.qol.superItems.factory.drinks.whisky.*;
import net.okuri.qol.superItems.factory.foods.BarleyBread;
import net.okuri.qol.superItems.factory.foods.Bread;
import net.okuri.qol.superItems.factory.foods.RyeBread;
import net.okuri.qol.superItems.factory.ingredient.Koji;
import net.okuri.qol.superItems.factory.ingredient.Molasses;
import net.okuri.qol.superItems.factory.ingredient.PolishedRice;
import net.okuri.qol.superItems.factory.resources.*;
import net.okuri.qol.superItems.factory.tools.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public final class QOL extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // config.ymlが存在しない場合はファイルに出力します。
        saveDefaultConfig();

        // drinkCraftsには特殊レシピを登録する
        SuperCraftController superCraft = SuperCraftController.getListener();
        MaturationController maturation = MaturationController.getListener();
        DistillationController distillation = DistillationController.getListener();
        ResourceController superResource = ResourceController.getListener();
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

        getCommand("getenv").setExecutor(new Commands(this));
        getCommand("matsign").setExecutor(new Commands(this));
        getCommand("givesuperitem").setExecutor(new Commands(this));
        getCommand("superwheat").setExecutor(new Commands(this));
        getCommand("alc").setExecutor(new Commands(this));
        getCommand("producer").setExecutor(new Commands(this));

        // bukkitRunnableを起動
        Alcohol alc = new Alcohol(this);
        alc.runTaskTimer(this, 0, 1200);

        // distillationのレシピを登録
        FurnaceRecipe distillationRecipe = new FurnaceRecipe(new NamespacedKey("qol","distillation_recipe"), new ItemStack(Material.POTION, 1), Material.POTION, 0.0f, 200);
        Bukkit.addRecipe(distillationRecipe);
        getLogger().info("QOL Plugin Enabled");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QOL Plugin Disabled");
    }

    public void registerRecipes(SuperCraftController superCraft) {
    //ここに特殊レシピ(作業台)を登録する

        // Whisky Ingredient
        SuperCraftRecipe whiskyRecipe = new SuperCraftRecipe("whisky_ingredient");
        whiskyRecipe.setShape(new String[]{" W ", " B ", " C "});
        whiskyRecipe.addIngredient('W', SuperItemType.BARLEY);
        whiskyRecipe.addIngredient('B', Material.WATER_BUCKET);
        whiskyRecipe.addIngredient('C', SuperItemType.COAL);
        whiskyRecipe.setResultClass(new WhiskyIngredient());
        superCraft.addSuperCraftRecipe(whiskyRecipe);

        // WhiskyWithIce
        SuperCraftRecipe whiskyWithIceRecipe = new SuperCraftRecipe("whisky_with_ice");
        whiskyWithIceRecipe.setShape(new String[]{" I ", " W ", "BBB"});
        whiskyWithIceRecipe.addIngredient('W', SuperItemType.WHISKY);
        whiskyWithIceRecipe.addIngredient('I', Material.ICE);
        whiskyWithIceRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        whiskyWithIceRecipe.setResultClass(new WhiskyWithIce());
        superCraft.addSuperCraftRecipe(whiskyWithIceRecipe);

        // Soda
        SuperCraftRecipe sodaRecipe = new SuperCraftRecipe("soda");
        sodaRecipe.setShape(new String[]{"CCC", " W ", "BBB"});
        sodaRecipe.addIngredient('C', SuperItemType.COAL);
        sodaRecipe.addIngredient('W', Material.WATER_BUCKET);
        sodaRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        sodaRecipe.setResultClass(new Soda());
        superCraft.addSuperCraftRecipe(sodaRecipe);

        //Highball
        SuperCraftRecipe highballRecipe = new SuperCraftRecipe("highball");
        highballRecipe.setShape(new String[]{" I ", " W ", "SSS"});
        highballRecipe.addIngredient('W', SuperItemType.WHISKY);
        highballRecipe.addIngredient('I', Material.ICE);
        highballRecipe.addIngredient('S', SuperItemType.SODA);
        highballRecipe.setResultClass(new Highball());
        superCraft.addSuperCraftRecipe(highballRecipe);

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

        // BeerIngredient
        SuperCraftRecipe beerRecipe = new SuperCraftRecipe("beer_ingredient");
        beerRecipe.setShape(new String[]{" W ", " B ", " V "});
        beerRecipe.addIngredient('W', SuperItemType.BARLEY);
        beerRecipe.addIngredient('B', Material.WATER_BUCKET);
        beerRecipe.addIngredient('V', Material.VINE);
        beerRecipe.setResultClass(new BeerIngredient());
        superCraft.addSuperCraftRecipe(beerRecipe);

        // ストゼロ
        SuperCraftRecipe st0Recipe = new SuperCraftRecipe("strong_zero");
        st0Recipe.setShape(new String[]{" B ", " R ", " W "});
        st0Recipe.addIngredient('B', Material.SWEET_BERRIES);
        st0Recipe.addIngredient('R', SuperItemType.RICE);
        st0Recipe.addIngredient('W', Material.POTION);
        st0Recipe.setResultClass(new StrongZero());
        superCraft.addSuperCraftRecipe(st0Recipe);

        // envGetter
        SuperCraftRecipe envGetterRecipe = new SuperCraftRecipe("env_getter");
        envGetterRecipe.setShape(new String[]{"IOI", "IRI", "III"});
        envGetterRecipe.addIngredient('I', Material.IRON_INGOT);
        envGetterRecipe.addIngredient('R', Material.COMPARATOR);
        envGetterRecipe.addIngredient('O', Material.OBSERVER);
        envGetterRecipe.setResultClass(new EnvGetter());
        superCraft.addSuperCraftRecipe(envGetterRecipe);

        // polished rice

        ShapelessSuperCraftRecipe polishedRiceRecipe = new ShapelessSuperCraftRecipe("polished_rice");
        polishedRiceRecipe.addIngredient(SuperItemType.POLISHED_RICE);
        polishedRiceRecipe.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(polishedRiceRecipe);

        // polished rice(initial)
        ShapelessSuperCraftRecipe polishedRiceRecipe2 = new ShapelessSuperCraftRecipe("polished_rice2");
        polishedRiceRecipe2.addIngredient(SuperItemType.RICE);
        polishedRiceRecipe2.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(polishedRiceRecipe2);

        // Koji
        SuperCraftRecipe kojiRecipe = new SuperCraftRecipe("koji");
        kojiRecipe.setShape(new String[]{" R ", " W ", "   "});
        kojiRecipe.addIngredient('R', SuperItemType.POLISHED_RICE);
        kojiRecipe.addIngredient('W', Material.WATER_BUCKET);
        kojiRecipe.setResultClass(new Koji());
        superCraft.addSuperCraftRecipe(kojiRecipe);

        // SakeIngredient
        SuperCraftRecipe sakeRecipe = new SuperCraftRecipe("sake_ingredient");
        sakeRecipe.setShape(new String[]{" R ", " K ", " W "});
        sakeRecipe.addIngredient('R', SuperItemType.POLISHED_RICE);
        sakeRecipe.addIngredient('K', SuperItemType.KOJI);
        sakeRecipe.addIngredient('W', Material.WATER_BUCKET);
        sakeRecipe.setResultClass(new SakeIngredient());
        superCraft.addSuperCraftRecipe(sakeRecipe);

        // Sake(1合)(distribution)
        DistributionCraftRecipe sake1goRecipe = new DistributionCraftRecipe("sake_1go");
        sake1goRecipe.setDistribution(new Sake1ShoBottle());
        sake1goRecipe.setReciver(new SakeBottle());
        sake1goRecipe.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(sake1goRecipe);

        // Sake(お猪口)(distribution)
        DistributionCraftRecipe sakeOchokoRecipe = new DistributionCraftRecipe("sake_ochoko");
        sakeOchokoRecipe.setDistribution(new SakeBottle());
        sakeOchokoRecipe.setReciver(new Ochoko());
        sakeOchokoRecipe.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(sakeOchokoRecipe);

        // shochu(1合)(distribution)
        DistributionCraftRecipe shochu1goRecipe = new DistributionCraftRecipe("shochu_1go");
        shochu1goRecipe.setDistribution(new Shochu());
        shochu1goRecipe.setReciver(new ShochuBottle());
        shochu1goRecipe.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(shochu1goRecipe);

        // shochu(お猪口)(distribution)
        DistributionCraftRecipe shochuOchokoRecipe = new DistributionCraftRecipe("shochu_ochoko");
        shochuOchokoRecipe.setDistribution(new ShochuBottle());
        shochuOchokoRecipe.setReciver(new ShochuOchoko());
        shochuOchokoRecipe.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(shochuOchokoRecipe);

        // hotsakeなochoko
        DistributionCraftRecipe hotSakeOchokoRecipe = new DistributionCraftRecipe("hot_sake_ochoko");
        hotSakeOchokoRecipe.setDistribution(new HotSake());
        hotSakeOchokoRecipe.setReciver(new Ochoko());
        hotSakeOchokoRecipe.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(hotSakeOchokoRecipe);

        // Molasses
        ShapelessSuperCraftRecipe molassesRecipe = new ShapelessSuperCraftRecipe("molasses");
        molassesRecipe.addIngredient(SuperItemType.SUGAR_CANE);
        molassesRecipe.addIngredient(SuperItemType.SUGAR_CANE);
        molassesRecipe.addIngredient(SuperItemType.SUGAR_CANE);
        molassesRecipe.addIngredient(Material.GLASS_BOTTLE);
        molassesRecipe.setResultClass(new Molasses());
        superCraft.addShapelessSuperCraftRecipe(molassesRecipe);

        // Ram ingredient
        SuperCraftRecipe rumIngredientRecipe = new SuperCraftRecipe("rum_ingredient");
        rumIngredientRecipe.setShape(new String[]{"MMM", "MWM", "MMM"});
        rumIngredientRecipe.addIngredient('M', SuperItemType.MOLASSES);
        rumIngredientRecipe.addIngredient('W', Material.POTION);
        rumIngredientRecipe.setResultClass(new RumIngredient());
        superCraft.addSuperCraftRecipe(rumIngredientRecipe);

        // a cup of rum
        DistributionCraftRecipe rumStraight = new DistributionCraftRecipe("distribution");
        rumStraight.setDistribution(new Rum());
        rumStraight.setReciver(new RumStraight());
        rumStraight.setBottle(Material.GLASS_BOTTLE);
        superCraft.addDistributionCraftRecipe(rumStraight);

        // horoyoi
        SuperCraftRecipe horoyoiRecipe = new SuperCraftRecipe("horoyoi");
        horoyoiRecipe.setShape(new String[]{" A ", " W ", "   "});
        horoyoiRecipe.addIngredient('A', SuperItemType.APPLE);
        horoyoiRecipe.addIngredient('W', Material.WATER_BUCKET);
        horoyoiRecipe.setResultClass(new Horoyoi());
        superCraft.addSuperCraftRecipe(horoyoiRecipe);

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

        // fusion craft
        for (SuperItemType t : SuperItemTag.RESOURCE.getSuperItemTypes()) {
            ShapelessSuperCraftRecipe fcr = new ShapelessSuperCraftRecipe("fusion_craft");
            fcr.addIngredient(t);
            fcr.addIngredient(t);
            fcr.setResultClass(new FusionCraft());
            superCraft.addShapelessSuperCraftRecipe(fcr);
        }
        for (String s : superCraft.getRecipeList()) {
            getLogger().info(s);
        }

    }

    // Maturationのレシピを登録する
    private void registerMaturationRecipes(MaturationController maturation) {
        // ここにMaturationのレシピを登録する

        // Whisky
        MaturationRecipe whiskyRecipe = new MaturationRecipe("Whisky", new Whisky());
        whiskyRecipe.addIngredient(SuperItemType.WHISKY_INGREDIENT);
        maturation.addMaturationRecipe(whiskyRecipe);

        // Beer
        MaturationRecipe beerRecipe = new MaturationRecipe("Beer", new Beer());
        beerRecipe.addIngredient(SuperItemType.BEER_INGREDIENT);
        maturation.addMaturationRecipe(beerRecipe);

        // Sake
        MaturationRecipe sakeRecipe = new MaturationRecipe("Sake", new Sake1ShoBottle());
        sakeRecipe.addIngredient(SuperItemType.SAKE_INGREDIENT);
        maturation.addMaturationRecipe(sakeRecipe);
    }

    // Distillationのレシピを登録する
    private void registerDistillationRecipes(DistillationController distillation) {
        // ここにDistillationのレシピを登録する

        // Whisky Ingredient
        DistillationRecipe whiskyIngredientRecipe = new DistillationRecipe("Whisky Ingredient", new WhiskyIngredient());
        whiskyIngredientRecipe.addIngredient(SuperItemType.WHISKY_INGREDIENT);
        whiskyIngredientRecipe.addIngredient(SuperItemType.UNDISTILLED_WHISKY_INGREDIENT);
        distillation.addDistillationRecipe(whiskyIngredientRecipe);

        // hot sake
        DistillationRecipe hotSakeRecipe = new DistillationRecipe("Hot Sake", new HotSake());
        hotSakeRecipe.addIngredient(SuperItemType.SAKE_1GO);
        distillation.addDistillationRecipe(hotSakeRecipe);

        // shochu
        DistillationRecipe shochuRecipe = new DistillationRecipe("Shochu", new Shochu());
        shochuRecipe.addIngredient(SuperItemType.SAKE_INGREDIENT);
        distillation.addDistillationRecipe(shochuRecipe);

        // ram
        DistillationRecipe ramRecipe = new DistillationRecipe("Ram", new Rum());
        ramRecipe.addIngredient(SuperItemType.RUM_INGREDIENT);
        ramRecipe.addIngredient(SuperItemType.RUM);
        distillation.addDistillationRecipe(ramRecipe);
    }


    private void registerSuperResources(ResourceController superResource) {
        // ここにSuperResourceを登録していく

        // SuperWheat
        SuperWheat superWheat = new SuperWheat();
        int wheatProbability = this.getConfig().getInt("resource.wheat.probability");
        superWheat.setProbability(wheatProbability);
        superResource.addResource(superWheat);

        // SuperCoal
        SuperCoal superCoal = new SuperCoal();
        int coalProbability = this.getConfig().getInt("resource.coal.probability");
        superCoal.setProbability(coalProbability);
        superResource.addResource(superCoal);

        // SuperPotato
        SuperPotato superPotato = new SuperPotato();
        int potatoProbability = this.getConfig().getInt("resource.potato.probability");
        superPotato.setProbability(potatoProbability);
        superResource.addResource(superPotato);

        // SugarCane
        SugarCane sugarCane = new SugarCane();
        int sugarCaneProbability = this.getConfig().getInt("resource.sugar_cane.probability");
        sugarCane.setProbability(sugarCaneProbability);
        superResource.addResource(sugarCane);

        // Apple
        SuperApple superApple = new SuperApple();
        int appleProbability = this.getConfig().getInt("resource.apple.probability");
        superApple.setProbability(appleProbability);
        superResource.addResource(superApple);
    }

    public JavaPlugin getPlugin() {
        return this;
    }
}

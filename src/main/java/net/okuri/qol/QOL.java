package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.Koji;
import net.okuri.qol.superItems.factory.PolishedRice;
import net.okuri.qol.superItems.factory.drinks.Soda;
import net.okuri.qol.superItems.factory.drinks.StrongZero;
import net.okuri.qol.superItems.factory.drinks.ingredients.BeerIngredient;
import net.okuri.qol.superItems.factory.drinks.ingredients.SakeIngredient;
import net.okuri.qol.superItems.factory.drinks.ingredients.WhiskyIngredient;
import net.okuri.qol.superItems.factory.drinks.sake.*;
import net.okuri.qol.superItems.factory.drinks.whisky.Beer;
import net.okuri.qol.superItems.factory.drinks.whisky.Highball;
import net.okuri.qol.superItems.factory.drinks.whisky.Whisky;
import net.okuri.qol.superItems.factory.drinks.whisky.WhiskyWithIce;
import net.okuri.qol.superItems.factory.foods.BarleyBread;
import net.okuri.qol.superItems.factory.foods.Bread;
import net.okuri.qol.superItems.factory.foods.RyeBread;
import net.okuri.qol.superItems.factory.resources.SuperCoal;
import net.okuri.qol.superItems.factory.resources.SuperPotato;
import net.okuri.qol.superItems.factory.resources.SuperWheat;
import net.okuri.qol.superItems.factory.tools.EnvGetter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;


public final class QOL extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // drinkCraftsには特殊レシピを登録する
        SuperCraftController superCraft = new SuperCraftController();
        MaturationController maturation = new MaturationController();
        DistillationController distillation = new DistillationController();
        ResourceController superResource = ResourceController.getListener();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getServer().getPluginManager().registerEvents(new ConsumeListener(), this);
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

        getCommand("getenv").setExecutor(new Commands());
        getCommand("matsign").setExecutor(new Commands());
        getCommand("givesuperitem").setExecutor(new Commands());
        getCommand("superwheat").setExecutor(new Commands());
        getCommand("alc").setExecutor(new Commands());

        // bukkitRunnableを起動
        Alcohol alc = new Alcohol();
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
        SuperCraftRecipe whiskyRecipe = new SuperCraftRecipe(whisky, "whisky_ingredient");
        whiskyRecipe.setShape(new String[]{" W ", " B ", " C "});
        whiskyRecipe.addIngredient('W', SuperItemType.BARLEY);
        whiskyRecipe.addIngredient('B', Material.WATER_BUCKET);
        whiskyRecipe.addIngredient('C', SuperItemType.COAL);
        whiskyRecipe.setResultClass(new WhiskyIngredient());
        superCraft.addSuperCraftRecipe(whiskyRecipe);

        // WhiskyWithIce
        ItemStack whiskyWithIce = new ItemStack(Material.POTION, 1);
        PotionMeta wwim = (PotionMeta)whiskyWithIce.getItemMeta();
        wwim.setColor(Color.fromRGB(170,70,10));
        wwim.displayName(Component.text("Whisky With Ice").color(NamedTextColor.GOLD));
        wwim.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        whiskyWithIce.setItemMeta(wwim);
        SuperCraftRecipe whiskyWithIceRecipe = new SuperCraftRecipe(whiskyWithIce, "whisky_with_ice");
        whiskyWithIceRecipe.setShape(new String[]{" I ", " W ", "BBB"});
        whiskyWithIceRecipe.addIngredient('W', SuperItemType.WHISKY);
        whiskyWithIceRecipe.addIngredient('I', Material.ICE);
        whiskyWithIceRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        whiskyWithIceRecipe.setResultClass(new WhiskyWithIce());
        superCraft.addSuperCraftRecipe(whiskyWithIceRecipe);

        // Soda
        ItemStack soda = new ItemStack(Material.POTION, 1);
        PotionMeta sodaMeta = (PotionMeta)soda.getItemMeta();
        sodaMeta.setColor(Color.AQUA);
        sodaMeta.displayName(Component.text("Soda").color(NamedTextColor.AQUA));
        sodaMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        soda.setItemMeta(sodaMeta);
        SuperCraftRecipe sodaRecipe = new SuperCraftRecipe(soda, "soda");
        sodaRecipe.setShape(new String[]{"CCC", " W ", "BBB"});
        sodaRecipe.addIngredient('C', SuperItemType.COAL);
        sodaRecipe.addIngredient('W', Material.WATER_BUCKET);
        sodaRecipe.addIngredient('B', Material.GLASS_BOTTLE);
        sodaRecipe.setResultClass(new Soda());
        superCraft.addSuperCraftRecipe(sodaRecipe);

        //Highball
        ItemStack highball = new ItemStack(Material.POTION, 1);
        PotionMeta highballMeta = (PotionMeta)highball.getItemMeta();
        highballMeta.setColor(Color.fromRGB(220,210,150));
        highballMeta.displayName(Component.text("Highball").color(NamedTextColor.GOLD));
        highballMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        highball.setItemMeta(highballMeta);
        SuperCraftRecipe highballRecipe = new SuperCraftRecipe(highball, "highball");
        highballRecipe.setShape(new String[]{" I ", " W ", "SSS"});
        highballRecipe.addIngredient('W', SuperItemType.WHISKY);
        highballRecipe.addIngredient('I', Material.ICE);
        highballRecipe.addIngredient('S', SuperItemType.SODA);
        highballRecipe.setResultClass(new Highball());
        superCraft.addSuperCraftRecipe(highballRecipe);

        // Bread
        ItemStack superBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superBreadRecipe = new SuperCraftRecipe(superBread, "bread");
        superBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBreadRecipe.addIngredient('W', SuperItemType.WHEAT);
        superBreadRecipe.setResultClass(new Bread());
        superCraft.addSuperCraftRecipe(superBreadRecipe);
        // パンは元からレシピが存在するので以下略

        // RyeBread
        ItemStack superRyeBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superRyeBreadRecipe = new SuperCraftRecipe(superRyeBread, "rye_bread");
        superRyeBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superRyeBreadRecipe.addIngredient('W', SuperItemType.RYE);
        superRyeBreadRecipe.setResultClass(new RyeBread());
        superCraft.addSuperCraftRecipe(superRyeBreadRecipe);

        // BarleyBread
        ItemStack superBarleyBread = new ItemStack(Material.BREAD, 1);
        SuperCraftRecipe superBarleyBreadRecipe = new SuperCraftRecipe(superBarleyBread, "barley_bread");
        superBarleyBreadRecipe.setShape(new String[]{"   ", "WWW", "   "});
        superBarleyBreadRecipe.addIngredient('W', SuperItemType.BARLEY);
        superBarleyBreadRecipe.setResultClass(new BarleyBread());
        superCraft.addSuperCraftRecipe(superBarleyBreadRecipe);

        // BeerIngredient
        ItemStack beer = new ItemStack(Material.POTION, 1);
        PotionMeta beerMeta = (PotionMeta)beer.getItemMeta();
        beerMeta.setColor(Color.fromRGB(255,255,255));
        beerMeta.displayName(Component.text("Beer Ingredients").color(NamedTextColor.GOLD));
        beerMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE!").generateLore());
        beer.setItemMeta(beerMeta);
        SuperCraftRecipe beerRecipe = new SuperCraftRecipe(beer, "beer_ingredient");
        beerRecipe.setShape(new String[]{" W ", " B ", " V "});
        beerRecipe.addIngredient('W', SuperItemType.BARLEY);
        beerRecipe.addIngredient('B', Material.WATER_BUCKET);
        beerRecipe.addIngredient('V', Material.VINE);
        beerRecipe.setResultClass(new BeerIngredient());
        superCraft.addSuperCraftRecipe(beerRecipe);

        // ストゼロ
        ItemStack st0 = new ItemStack(Material.POTION, 1);
        PotionMeta st0meta = (PotionMeta) st0.getItemMeta();
        st0meta.displayName(Component.text("Strong Zero").color(NamedTextColor.GREEN));
        st0meta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        st0.setItemMeta(st0meta);
        SuperCraftRecipe st0Recipe = new SuperCraftRecipe(st0, "strong_zero");
        st0Recipe.setShape(new String[]{" B ", " R ", " W "});
        st0Recipe.addIngredient('B', Material.SWEET_BERRIES);
        st0Recipe.addIngredient('R', SuperItemType.RICE);
        st0Recipe.addIngredient('W', Material.POTION);
        st0Recipe.setResultClass(new StrongZero());
        superCraft.addSuperCraftRecipe(st0Recipe);

        // envGetter
        ItemStack envGetter = new ItemStack(Material.PAPER, 1);
        ItemMeta envGetterMeta = envGetter.getItemMeta();
        envGetterMeta.displayName(Component.text("Environment Getter").color(NamedTextColor.GREEN));
        envGetterMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        envGetter.setItemMeta(envGetterMeta);
        SuperCraftRecipe envGetterRecipe = new SuperCraftRecipe(envGetter, "env_getter");
        envGetterRecipe.setShape(new String[]{"IOI", "IRI", "III"});
        envGetterRecipe.addIngredient('I', Material.IRON_INGOT);
        envGetterRecipe.addIngredient('R', Material.COMPARATOR);
        envGetterRecipe.addIngredient('O', Material.OBSERVER);
        envGetterRecipe.setResultClass(new EnvGetter());
        superCraft.addSuperCraftRecipe(envGetterRecipe);

        // polished rice
        ItemStack polishedRice = new ItemStack(Material.PUMPKIN_SEEDS, 1);
        ItemMeta polishedRiceMeta = polishedRice.getItemMeta();
        polishedRiceMeta.displayName(Component.text("Polished Rice").color(NamedTextColor.GOLD));
        polishedRiceMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        polishedRice.setItemMeta(polishedRiceMeta);
        ShapelessSuperCraftRecipe polishedRiceRecipe = new ShapelessSuperCraftRecipe(polishedRice, "polished_rice");
        polishedRiceRecipe.addIngredient(SuperItemType.POLISHED_RICE);
        polishedRiceRecipe.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(polishedRiceRecipe);

        // polished rice(initial)
        ShapelessSuperCraftRecipe polishedRiceRecipe2 = new ShapelessSuperCraftRecipe(polishedRice, "polished_rice2");
        polishedRiceRecipe2.addIngredient(SuperItemType.RICE);
        polishedRiceRecipe2.setResultClass(new PolishedRice());
        superCraft.addShapelessSuperCraftRecipe(polishedRiceRecipe2);

        // Koji
        ItemStack koji = new ItemStack(Material.POTION, 1);
        PotionMeta kojiMeta = (PotionMeta) koji.getItemMeta();
        kojiMeta.displayName(Component.text("Koji").color(NamedTextColor.GOLD));
        kojiMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        koji.setItemMeta(kojiMeta);
        SuperCraftRecipe kojiRecipe = new SuperCraftRecipe(koji, "koji");
        kojiRecipe.setShape(new String[]{" R ", " W ", "   "});
        kojiRecipe.addIngredient('R', SuperItemType.POLISHED_RICE);
        kojiRecipe.addIngredient('W', Material.WATER_BUCKET);
        kojiRecipe.setResultClass(new Koji());
        superCraft.addSuperCraftRecipe(kojiRecipe);

        // SakeIngredient
        ItemStack sake = new ItemStack(Material.POTION, 1);
        PotionMeta sakeMeta = (PotionMeta) sake.getItemMeta();
        sakeMeta.displayName(Component.text("Sake Ingredient").color(NamedTextColor.GOLD));
        sakeMeta.lore(new LoreGenerator().addImportantLore("WRONG RECIPE").generateLore());
        sake.setItemMeta(sakeMeta);
        SuperCraftRecipe sakeRecipe = new SuperCraftRecipe(sake, "sake_ingredient");
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
    }


    private void registerSuperResources(ResourceController superResource) {
        // ここにSuperResourceを登録していく

        // SuperWheat
        SuperWheat superWheat = new SuperWheat();
        superResource.addResource(superWheat);

        // SuperCoal
        SuperCoal superCoal = new SuperCoal();
        superResource.addResource(superCoal);

        // SuperPotato
        SuperPotato superPotato = new SuperPotato();
        superResource.addResource(superPotato);
    }

    public JavaPlugin getPlugin() {
        return this;
    }
}

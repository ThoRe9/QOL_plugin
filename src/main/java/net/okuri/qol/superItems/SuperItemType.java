package net.okuri.qol.superItems;


import net.okuri.qol.alcohol.LiquorIngredient;
import net.okuri.qol.alcohol.resources.newBarley;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.superItems.factory.DefaultItem;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.adapter.FishermanAdapter;
import net.okuri.qol.superItems.factory.drinks.Horoyoi;
import net.okuri.qol.superItems.factory.drinks.LiverHelper;
import net.okuri.qol.superItems.factory.drinks.Soda;
import net.okuri.qol.superItems.factory.drinks.StrongZero;
import net.okuri.qol.superItems.factory.drinks.sake.*;
import net.okuri.qol.superItems.factory.drinks.spirits.Rum;
import net.okuri.qol.superItems.factory.drinks.spirits.RumIngredient;
import net.okuri.qol.superItems.factory.drinks.spirits.RumStraight;
import net.okuri.qol.superItems.factory.drinks.whisky.*;
import net.okuri.qol.superItems.factory.foods.Bread;
import net.okuri.qol.superItems.factory.ingredient.Koji;
import net.okuri.qol.superItems.factory.ingredient.Molasses;
import net.okuri.qol.superItems.factory.ingredient.PolishedRice;
import net.okuri.qol.superItems.factory.resources.*;
import net.okuri.qol.superItems.factory.tools.*;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public enum SuperItemType {
    DEFAULT("DEFAULT", 0, null),
    COAL("COAL", 0, Material.COAL, SuperItemTag.RESOURCE),
    WHEAT("WHEAT", 0, Material.WHEAT, SuperItemTag.RESOURCE),
    RYE("RYE", 0, Material.WHEAT, SuperItemTag.RESOURCE),
    BARLEY("BARLEY", 0, Material.WHEAT, SuperItemTag.RESOURCE),
    RICE("RICE", 0, Material.WHEAT, SuperItemTag.RESOURCE),
    POLISHED_RICE("POLISHED_RICE", 0, Material.PUMPKIN_SEEDS, SuperItemTag.RESOURCE),
    KOJI("KOJI", 0, Material.POTION, SuperItemTag.INGREDIENT),
    SAKE_INGREDIENT("SAKE_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    SAKE_1SHO("SAKE_1SHO", 6, Material.POTION, SuperItemTag.LIQUOR),
    SAKE_1GO("SAKE_1GO", 7, Material.POTION, SuperItemTag.LIQUOR),
    HOT_SAKE("HOT_SAKE", 8, Material.POTION, SuperItemTag.LIQUOR),
    SAKE_OCHOKO("SAKE_OCHOKO", 9, Material.POTION, SuperItemTag.LIQUOR),
    SHOCHU("SHOCHU", 6, Material.POTION, SuperItemTag.LIQUOR),
    SHOCHU_1GO("SHOCHU_1GO", 7, Material.POTION, SuperItemTag.LIQUOR),
    SHOCHU_OCHOKO("SHOCHU_OCHOKO", 9, Material.POTION, SuperItemTag.LIQUOR),
    POTATO("POTATO", 0, Material.POTATO, SuperItemTag.RESOURCE),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    WHISKY("WHISKY", 1, Material.POTION, SuperItemTag.LIQUOR),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE", 2, Material.POTION, SuperItemTag.LIQUOR),
    HIGHBALL("HIGHBALL", 3, Material.POTION, SuperItemTag.LIQUOR),
    BREAD("BREAD", 0, Material.BREAD, SuperItemTag.FOOD),
    BEER_INGREDIENT("BEER_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    ALE_BEER("ALE_BEER", 4, Material.POTION, SuperItemTag.LIQUOR),
    LAGER_BEER("LAGER_BEER", 5, Material.POTION, SuperItemTag.LIQUOR),
    BEER("BEER", 0, Material.POTION, SuperItemTag.LIQUOR),
    SODA("SODA", 0, Material.POTION, SuperItemTag.DRINK),
    ENV_TOOL("ENV_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    SUGAR_CANE("SUGAR_CANE", 0, Material.SUGAR_CANE, SuperItemTag.RESOURCE),
    MOLASSES("MOLASSES", 0, Material.HONEY_BOTTLE, SuperItemTag.INGREDIENT),
    SUGAR("SUGAR", 0, Material.SUGAR, SuperItemTag.INGREDIENT),
    SPIRITS_INGREDIENT("SPIRITS_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    RUM("RUM", 0, Material.POTION, SuperItemTag.LIQUOR),
    RUM_INGREDIENT("RUM_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    RUM_STRAIGHT("RUM_STRAIGHT", 0, Material.POTION, SuperItemTag.LIQUOR),
    HOROYOI("HOROYOI", 0, Material.POTION, SuperItemTag.LIQUOR),
    APPLE("APPLE", 0, Material.APPLE, SuperItemTag.RESOURCE),
    FARMER_TOOL("FARMER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    MINER_TOOL("MINER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    STRONG_ZERO("STRONG_ZERO", 0, Material.POTION, SuperItemTag.LIQUOR),
    LIVER_HELPER("LIVER_HELPER", 0, Material.POTION, SuperItemTag.DRINK),
    RESOURCE_GETTER("RESOURCE_GETTER", 0, Material.IRON_PICKAXE, SuperItemTag.TOOL),
    MATURATION_TOOL("MATURATION_TOOL", 0, Material.BONE, SuperItemTag.TOOL),
    FISHERMAN_ADAPTER("FISHERMAN_ADAPTER", 0, Material.PUFFERFISH, SuperItemTag.LIQUOR_ADAPTOR),
    NEW_BARLEY("NEW_BARLEY", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    LIQUOR_INGREDIENT("LIQUOR_INGREDIENT", 0, Material.POTION, SuperItemTag.INGREDIENT),
    NEW_KOJI("KOJI", 0, Material.POTION, SuperItemTag.INGREDIENT),
    YEAST("YEAST", 0, Material.POTION, SuperItemTag.INGREDIENT),
    FERMENTATION_INGREDIENT("FERMENTATION_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT);

    private final String type;
    private final int customModelData;
    private Material material;
    private final SuperItemTag tag;

    SuperItemType(String type, int customModelData, Material material) {
        this.type = type;
        this.customModelData = customModelData;
        this.material = material;
        this.tag = null;
    }

    SuperItemType(String type, int customModelData, Material material, SuperItemTag tag) {
        this.type = type;
        this.customModelData = customModelData;
        this.material = material;
        this.tag = tag;
        tag.addSuperItemType(this);
    }


    public String getStringType() {
        return this.type;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public static SuperItem getSuperItemClass(SuperItemType type) {
        switch (type) {
            case COAL:
                return new SuperCoal();
            case WHEAT:
                return new Wheat();
            case RYE:
                return new Rye();
            case BARLEY:
                return new Barley();
            case RICE:
                return new Rice();
            case POLISHED_RICE:
                return new PolishedRice();
            case KOJI:
                return new Koji();
            case SAKE_INGREDIENT:
                return new SakeIngredient();
            case SAKE_1SHO:
                return new Sake1ShoBottle();
            case SAKE_1GO:
                return new SakeBottle();
            case HOT_SAKE:
                return new HotSake();
            case SAKE_OCHOKO:
                return new Ochoko();
            case SHOCHU:
                return new Shochu();
            case SHOCHU_1GO:
                return new ShochuBottle();
            case SHOCHU_OCHOKO:
                return new ShochuOchoko();
            case POTATO:
                return new SuperPotato();
            case WHISKY_INGREDIENT:
                return new WhiskyIngredient();
            case UNDISTILLED_WHISKY_INGREDIENT:
                return new UndistilledWhiskyIngredient();
            case WHISKY:
                return new Whisky();
            case WHISKY_WITH_ICE:
                return new WhiskyWithIce();
            case HIGHBALL:
                return new HighBall();
            case BREAD:
                return new Bread();
            case BEER_INGREDIENT:
                return new BeerIngredient();
            case ALE_BEER:
                return new AleBeer();
            case LAGER_BEER:
                return new LagerBeer();
            case SODA:
                return new Soda();
            case ENV_TOOL:
                return new EnvGetter();
            case STRONG_ZERO:
                return new StrongZero();
            case SUGAR_CANE:
                return new SugarCane();
            case MOLASSES:
                return new Molasses();
            case RUM:
                return new Rum();
            case RUM_INGREDIENT:
                return new RumIngredient();
            case RUM_STRAIGHT:
                return new RumStraight();
            case APPLE:
                return new SuperApple();
            case HOROYOI:
                return new Horoyoi();
            case FARMER_TOOL:
                return new FarmerToolAdapter();
            case MINER_TOOL:
                return new MinerToolAdapter();
            case DEFAULT:
                return new DefaultItem(type);
            case LIVER_HELPER:
                return new LiverHelper();
            case RESOURCE_GETTER:
                return new ResourceGetter();
            case MATURATION_TOOL:
                return new MaturationTool(1);
            case FISHERMAN_ADAPTER:
                return new FishermanAdapter();
            case NEW_BARLEY:
                return new newBarley();
            case LIQUOR_INGREDIENT:
                Map<Taste, Double> tastes = new HashMap<>();
                tastes.put(TasteController.getController().getTaste("barley"), 0.1);
                return new LiquorIngredient(1, 0.1, tastes, 0.1);
            case NEW_KOJI:
                return new net.okuri.qol.alcohol.Koji();
            case FERMENTATION_INGREDIENT:
                return new net.okuri.qol.alcohol.FermentationIngredient();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public Material getMaterial() {
        return this.material;
    }


    public SuperItemTag getTag() {
        return this.tag;
    }

    public boolean hasTag() {
        return this.tag != null;
    }

    public boolean hasTag(SuperItemTag tag) {
        return this.tag == tag;
    }

    public static SuperItemType getTypeFromString(String type) {
        for (SuperItemType superItemType : SuperItemType.values()) {
            if (superItemType.getStringType().equals(type)) {
                return superItemType;
            }
        }
        return null;
    }
}

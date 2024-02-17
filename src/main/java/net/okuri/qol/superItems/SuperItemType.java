package net.okuri.qol.superItems;


import net.okuri.qol.alcohol.*;
import net.okuri.qol.alcohol.resources.*;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.superItems.factory.DefaultItem;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.drinks.LiverHelper;
import net.okuri.qol.superItems.factory.tools.*;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public enum SuperItemType {
    DEFAULT("DEFAULT", 0, null),
    ENV_TOOL("ENV_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    FARMER_TOOL("FARMER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    MINER_TOOL("MINER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    LIVER_HELPER("LIVER_HELPER", 0, Material.POTION, SuperItemTag.DRINK),
    RESOURCE_GETTER("RESOURCE_GETTER", 0, Material.IRON_PICKAXE, SuperItemTag.TOOL),
    MATURATION_TOOL("MATURATION_TOOL", 0, Material.BONE, SuperItemTag.TOOL),
    BARLEY("BARLEY", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    RYE("RYE", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    WHEAT("WHEAT", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    RICE("RICE", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    YEAST("YEAST", 0, Material.POTION, SuperItemTag.INGREDIENT),
    MALT("MALT", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    BARLEY_JUICE("BARLEY_JUICE", 0, Material.POTION, SuperItemTag.LIQUOR_RESOURCE),
    POLISHED_RICE("POLISHED_RICE", 0, Material.PUMPKIN_SEEDS, SuperItemTag.LIQUOR_RESOURCE),
    POTATO("POTATO", 0, Material.POTATO, SuperItemTag.LIQUOR_RESOURCE),
    HOP("HOP", 0, Material.VINE, SuperItemTag.LIQUOR_RESOURCE),
    SUGAR_CANE("SUGAR_CANE", 0, Material.SUGAR_CANE, SuperItemTag.LIQUOR_RESOURCE),
    MOLASSES("MOLASSES", 0, Material.HONEY_BOTTLE, SuperItemTag.LIQUOR_RESOURCE),
    GRAPE("grape", 0, Material.SWEET_BERRIES, SuperItemTag.LIQUOR_RESOURCE),
    FERMENTED_GRAPE("fermented_grape", 0, Material.SWEET_BERRIES, SuperItemTag.LIQUOR_RESOURCE),
    LIQUOR_INGREDIENT("LIQUOR_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    FERMENTATION_INGREDIENT("FERMENTATION_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    LIQUOR("LIQUOR", 0, Material.POTION, SuperItemTag.LIQUOR),
    LIQUOR_GLASS("LIQUOR_GLASS", 0, Material.POTION, SuperItemTag.LIQUOR),
    GLASS("GLASS", 0, Material.GLASS_BOTTLE, SuperItemTag.INGREDIENT);

    private final String type;
    private final int customModelData;
    private final Material material;
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
            case ENV_TOOL:
                return new EnvGetter();
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
            case BARLEY:
                return new Barley();
            case LIQUOR_INGREDIENT:
                Map<Taste, Double> tastes = new HashMap<>();
                tastes.put(TasteController.getController().getTaste("barley"), 0.1);
                return new LiquorIngredient(1, 0.1, tastes, 0.1);
            case YEAST:
                return new Yeast();
            case FERMENTATION_INGREDIENT:
                return new FermentationIngredient();
            case LIQUOR:
                return new Liquor();
            case LIQUOR_GLASS:
                return new LiquorGlass();
            case MALT:
                return new Malt();
            case BARLEY_JUICE:
                return new BarleyJuice();
            case RYE:
                return new Rye();
            case WHEAT:
                return new Wheat();
            case RICE:
                return new Rice();
            case POLISHED_RICE:
                return new PolishedRice();
            case POTATO:
                return new Potato();
            case HOP:
                return new Hop();
            case SUGAR_CANE:
                return new SugarCane();
            case MOLASSES:
                return new Molasses();
            case GRAPE:
                return new Grape();
            case FERMENTED_GRAPE:
                return new FermentedGrape();
            case GLASS:
                return new Glass();
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
}

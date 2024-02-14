package net.okuri.qol.superItems;


import net.okuri.qol.alcohol.LiquorIngredient;
import net.okuri.qol.alcohol.resources.BarleyJuice;
import net.okuri.qol.alcohol.resources.Malt;
import net.okuri.qol.alcohol.resources.newBarley;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.superItems.factory.DefaultItem;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.drinks.LiverHelper;
import net.okuri.qol.superItems.factory.foods.Bread;
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
    POTATO("POTATO", 0, Material.POTATO, SuperItemTag.RESOURCE),
    BREAD("BREAD", 0, Material.BREAD, SuperItemTag.FOOD),
    ENV_TOOL("ENV_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    SUGAR_CANE("SUGAR_CANE", 0, Material.SUGAR_CANE, SuperItemTag.RESOURCE),
    MOLASSES("MOLASSES", 0, Material.HONEY_BOTTLE, SuperItemTag.INGREDIENT),
    SUGAR("SUGAR", 0, Material.SUGAR, SuperItemTag.INGREDIENT),
    APPLE("APPLE", 0, Material.APPLE, SuperItemTag.RESOURCE),
    FARMER_TOOL("FARMER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    MINER_TOOL("MINER_TOOL", 0, Material.PAPER, SuperItemTag.TOOL),
    LIVER_HELPER("LIVER_HELPER", 0, Material.POTION, SuperItemTag.DRINK),
    RESOURCE_GETTER("RESOURCE_GETTER", 0, Material.IRON_PICKAXE, SuperItemTag.TOOL),
    MATURATION_TOOL("MATURATION_TOOL", 0, Material.BONE, SuperItemTag.TOOL),
    FISHERMAN_ADAPTER("FISHERMAN_ADAPTER", 0, Material.PUFFERFISH, SuperItemTag.LIQUOR_ADAPTOR),
    NEW_BARLEY("NEW_BARLEY", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    LIQUOR_INGREDIENT("LIQUOR_INGREDIENT", 0, Material.POTION, SuperItemTag.INGREDIENT),
    YEAST("YEAST", 0, Material.POTION, SuperItemTag.INGREDIENT),
    FERMENTATION_INGREDIENT("FERMENTATION_INGREDIENT", 0, Material.POTION, SuperItemTag.LIQUOR_INGREDIENT),
    LIQUOR("LIQUOR", 0, Material.POTION, SuperItemTag.LIQUOR),
    LIQUOR_GLASS("LIQUOR_GLASS", 0, Material.POTION, SuperItemTag.LIQUOR),
    MALT("MALT", 0, Material.WHEAT, SuperItemTag.LIQUOR_RESOURCE),
    BARLEY_JUICE("BARLEY_JUICE", 0, Material.POTION, SuperItemTag.LIQUOR_RESOURCE);

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
            case BREAD:
                return new Bread();
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
            case NEW_BARLEY:
                return new newBarley();
            case LIQUOR_INGREDIENT:
                Map<Taste, Double> tastes = new HashMap<>();
                tastes.put(TasteController.getController().getTaste("barley"), 0.1);
                return new LiquorIngredient(1, 0.1, tastes, 0.1);
            case YEAST:
                return new net.okuri.qol.alcohol.Yeast();
            case FERMENTATION_INGREDIENT:
                return new net.okuri.qol.alcohol.FermentationIngredient();
            case LIQUOR:
                return new net.okuri.qol.alcohol.Liquor();
            case LIQUOR_GLASS:
                return new net.okuri.qol.alcohol.LiquorGlass();
            case MALT:
                return new Malt();
            case BARLEY_JUICE:
                return new BarleyJuice();
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

package net.okuri.qol.superItems;


import net.okuri.qol.superItems.drinks.Soda;
import net.okuri.qol.superItems.drinks.ingredients.BeerIngredient;
import net.okuri.qol.superItems.drinks.ingredients.SakeIngredient;
import net.okuri.qol.superItems.drinks.ingredients.WhiskyIngredient;
import net.okuri.qol.superItems.drinks.sake.*;
import net.okuri.qol.superItems.drinks.whisky.*;
import net.okuri.qol.superItems.foods.Bread;
import net.okuri.qol.superItems.resources.*;
import net.okuri.qol.superItems.tools.EnvGetter;

public enum SuperItemType {
    COAL("COAL", 0),
    WHEAT("WHEAT", 0),
    RYE("RYE", 0),
    BARLEY("BARLEY", 0),
    RICE("RICE", 0),
    POLISHED_RICE("POLISHED_RICE", 0),
    KOJI("KOJI", 0),
    SAKE_INGREDIENT("SAKE_INGREDIENT", 0),
    SAKE_1SHO("SAKE_1SHO", 6),
    SAKE_1GO("SAKE_1GO", 7),
    HOT_SAKE("HOT_SAKE", 8),
    SAKE_OCHOKO("SAKE_OCHOKO", 9),
    SHOCHU("SHOCHU", 6),
    SHOCHU_1GO("SHOCHU_1GO", 7),
    SHOCHU_OCHOKO("SHOCHU_OCHOKO", 9),
    POTATO("POTATO", 0),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT", 0),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT", 0),
    WHISKY("WHISKY", 1),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE", 2),
    HIGHBALL("HIGHBALL", 3),
    BREAD("BREAD", 0),
    BEER_INGREDIENT("BEER_INGREDIENT", 0),
    ALE_BEER("ALE_BEER", 4),
    LAGER_BEER("LAGER_BEER", 5),
    SODA("SODA", 0),
    ENV_TOOL("ENV_TOOL", 0);

    private final String type;
    private final int customModelData;

    SuperItemType(String type, int customModelData) {
        this.type = type;
        this.customModelData = customModelData;
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
                return new WhiskyIngredient(1);
            case UNDISTILLED_WHISKY_INGREDIENT:
                return new WhiskyIngredient(0);
            case WHISKY:
                return new Whisky();
            case WHISKY_WITH_ICE:
                return new WhiskyWithIce();
            case HIGHBALL:
                return new Highball();
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
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
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

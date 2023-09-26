package net.okuri.qol.superItems;




import net.okuri.qol.superItems.drinks.*;
import net.okuri.qol.superItems.drinks.AleBeer;
import net.okuri.qol.superItems.drinks.LagerBeer;
import net.okuri.qol.superItems.drinks.Whisky;
import net.okuri.qol.superItems.foods.Bread;
import net.okuri.qol.superItems.tools.EnvGetter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum SuperItemType {
    COAL("COAL", 0),
    WHEAT("WHEAT", 0),
    RYE("RYE", 0),
    BARLEY("BARLEY", 0),
    RICE("RICE", 0),
    POLISHED_RICE("POLISHED_RICE", 0),
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

    public static SuperItem getSuperItemClass (SuperItemType type){
        switch(type){
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
                return null;
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

package net.okuri.qol.superItems;




import net.okuri.qol.drinks.*;
import net.okuri.qol.drinks.maturation.Beer;
import net.okuri.qol.drinks.maturation.Whisky;
import net.okuri.qol.foods.Bread;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Bee;
import org.bukkit.persistence.PersistentDataType;

public enum SuperItemType {
    COAL("COAL", new SuperCoal(), 0),
    WHEAT("WHEAT", new SuperWheat(), 0),
    RYE("RYE", new SuperWheat(), 0),
    BARLEY("BARLEY", new SuperWheat(), 0),
    RICE("RICE", new SuperWheat(), 0),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT", new WhiskyIngredient(), 0),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT", new WhiskyIngredient(), 0),
    WHISKY("WHISKY", new Whisky(), 1),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE", new WhiskyWithIce(), 2),
    STRAIGHT_WHISKY("STRAIGHT_WHISKY", new SuperCoal(), 0),
    HIGHBALL("HIGHBALL", new Highball(), 3),
    WHISKY_WITH_WATER("WHISKY_WITH_WATER", new SuperCoal(), 0),
    BREAD("BREAD", new Bread(), 0),
    BEER_INGREDIENT("BEER_INGREDIENT", new BeerIngredient(), 0),
    ALE_BEER("ALE_BEER", new Beer(), 4),
    LAGER_BEER("LAGER_BEER", new Beer(), 5),
    SODA("SODA", new Soda(), 0);

    private final String type;
    private final SuperItem superItemClass;
    private final int customModelData;

    public static final NamespacedKey typeKey = new NamespacedKey("qol", "super_item_type");

    public static final PersistentDataType typeDataType = PersistentDataType.STRING;

    SuperItemType(String type, SuperItem superItemClass, int customModelData) {
        this.type = type;
        this.superItemClass = superItemClass;
        this.customModelData = customModelData;
    }

    public String getStringType() {
        return this.type;
    }
    public int getCustomModelData() {
        return this.customModelData;
    }
    public SuperItem getSuperItemClass() {
        return this.superItemClass;
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

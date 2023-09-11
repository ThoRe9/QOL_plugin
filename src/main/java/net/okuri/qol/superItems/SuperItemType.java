package net.okuri.qol.superItems;




import net.okuri.qol.drinks.WhiskyIngredient;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum SuperItemType {
    COAL("COAL", new SuperCoal(), 0),
    WHEAT("WHEAT", new SuperWheat(), 0),
    RYE("RYE", new SuperWheat(), 0),
    BARLEY("BARLEY", new SuperWheat(), 0),
    RICE("RICE", new SuperWheat(), 0),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT", new WhiskyIngredient(), 0),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT", new WhiskyIngredient(), 0),
    WHISKY("WHISKY", new SuperCoal(), 1),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE", new SuperCoal(), 2),
    STRAIGHT_WHISKY("STRAIGHT_WHISKY", new SuperCoal(), 0),
    HIGHBALL("HIGHBALL", new SuperCoal(), 3),
    WHISKY_WITH_WATER("WHISKY_WITH_WATER", new SuperCoal(), 0),
    BREAD("BREAD", new SuperCoal(), 0),
    BEER_INGREDIENT("BEER_INGREDIENT", new SuperCoal(), 0),
    ALE_BEER("ALE_BEER", new SuperCoal(), 4),
    LAGER_BEER("LAGER_BEER", new SuperCoal(), 5),
    SODA("SODA", new SuperCoal(), 0);

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

    public static SuperItemType getTypeFromString(String type) {
        for (SuperItemType superItemType : SuperItemType.values()) {
            if (superItemType.getStringType().equals(type)) {
                return superItemType;
            }
        }
        return null;
    }
}

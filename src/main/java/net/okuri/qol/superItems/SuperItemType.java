package net.okuri.qol.superItems;




import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum SuperItemType {
    COAL("COAL"),
    WHEAT("WHEAT"),
    RYE("RYE"),
    BARLEY("BARLEY"),
    RICE("RICE"),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT"),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT"),
    WHISKY("WHISKY"),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE"),
    STRAIGHT_WHISKY("STRAIGHT_WHISKY"),
    HIGHBALL("HIGHBALL"),
    WHISKY_WITH_WATER("WHISKY_WITH_WATER"),
    SODA("SODA");

    private String type;

    public static final NamespacedKey typeKey = new NamespacedKey("qol", "super_item_type");

    public static final PersistentDataType typeDataType = PersistentDataType.STRING;

    private SuperItemType(String type) {
        this.type = type;
    }

    public String getStringType() {
        return this.type;
    }

    public boolean isMaturationable() {
        return this == WHISKY_INGREDIENT;
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

package net.okuri.qol.superItems;




import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum SuperItemType {
    COAL("COAL",0),
    WHEAT("WHEAT",0),
    RYE("RYE",0),
    BARLEY("BARLEY",0),
    RICE("RICE",0),
    WHISKY_INGREDIENT("WHISKY_INGREDIENT",0),
    UNDISTILLED_WHISKY_INGREDIENT("UNDISTILLED_WHISKY_INGREDIENT",0),
    WHISKY("WHISKY",1),
    WHISKY_WITH_ICE("WHISKY_WITH_ICE",2),
    STRAIGHT_WHISKY("STRAIGHT_WHISKY",0),
    HIGHBALL("HIGHBALL",3),
    WHISKY_WITH_WATER("WHISKY_WITH_WATER",0),
    BREAD("BREAD",0),
    BEER_INGREDIENT("BEER_INGREDIENT",0),
    ALE_BEER("ALE_BEER",4),
    LAGER_BEER("LAGER_BEER",5),
    SODA("SODA",0);

    private final String type;
    private final int customModelData;

    public static final NamespacedKey typeKey = new NamespacedKey("qol", "super_item_type");

    public static final PersistentDataType typeDataType = PersistentDataType.STRING;

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

    public static SuperItemType getTypeFromString(String type) {
        for (SuperItemType superItemType : SuperItemType.values()) {
            if (superItemType.getStringType().equals(type)) {
                return superItemType;
            }
        }
        return null;
    }
}

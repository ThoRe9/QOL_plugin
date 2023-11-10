package net.okuri.qol.superItems;

import org.bukkit.Material;

public class SuperItemData {
    private final SuperItemType type;
    private final Material material;

    public SuperItemData(SuperItemType type) {
        assert type != SuperItemType.DEFAULT;
        this.type = type;
        this.material = type.getMaterial();
    }

    public SuperItemData(Material material) {
        this.type = SuperItemType.DEFAULT;
        this.material = material;
    }

    public SuperItemType getType() {
        return type;
    }

    public boolean isSimillar(SuperItemData data) {
        if (this.type == SuperItemType.DEFAULT) {
            return this.material == data.material;
        } else {
            return this.type == data.type;
        }
    }

    public boolean isDefault() {
        return this.type == SuperItemType.DEFAULT;
    }
}

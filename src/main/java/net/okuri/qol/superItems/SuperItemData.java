package net.okuri.qol.superItems;

import org.bukkit.Material;
import org.bukkit.Tag;

public class SuperItemData {
    private final SuperItemType type;
    private final Material material;
    private final boolean isAbstract;
    private final Tag tag;

    public SuperItemData(SuperItemType type) {
        assert type != SuperItemType.DEFAULT;
        this.type = type;
        this.material = type.getMaterial();
        this.isAbstract = false;
        this.tag = null;
    }

    public SuperItemData(Material material) {
        this.type = SuperItemType.DEFAULT;
        this.material = material;
        this.isAbstract = false;
        this.tag = null;
    }

    public SuperItemData(Tag<Material> tag) {
        this.type = null;
        this.material = null;
        this.isAbstract = true;
        this.tag = tag;
    }

    public SuperItemType getType() {
        return type;
    }

    public boolean isSimilar(SuperItemData data) {
        if (data.isAbstract && this.isAbstract) {
            return data.tag == this.tag;
        } else if (data.isAbstract) {
            return data.tag.isTagged(this.material);
        } else if (this.isAbstract) {
            return this.tag.isTagged(data.material);
        } else if (this.type == SuperItemType.DEFAULT) {
            return this.material == data.material;
        } else {
            return this.type == data.type;
        }
    }

    public String toString() {
        if (this.isAbstract) {
            return this.tag.toString();
        } else if (this.type == SuperItemType.DEFAULT) {
            return this.material.toString();
        } else {
            return this.type.toString();
        }
    }

    public boolean isDefault() {
        return this.type == SuperItemType.DEFAULT;
    }
}

package net.okuri.qol.superItems;

import org.bukkit.Material;
import org.bukkit.Tag;

public class SuperItemData {
    private final SuperItemType type;
    private final Material material;
    private final boolean isAbstract;
    private final Tag<Material> tag;
    private final SuperItemTag superItemTag;

    public SuperItemData(SuperItemType type) {
        assert type != SuperItemType.DEFAULT;
        this.type = type;
        this.material = type.getMaterial();
        this.isAbstract = false;
        this.tag = null;
        this.superItemTag = null;
    }

    public SuperItemData(Material material) {
        this.type = SuperItemType.DEFAULT;
        this.material = material;
        this.isAbstract = false;
        this.tag = null;
        this.superItemTag = null;
    }

    public SuperItemData(Tag<Material> tag) {
        this.type = null;
        this.material = null;
        this.isAbstract = true;
        this.tag = tag;
        this.superItemTag = null;
    }

    public SuperItemData(SuperItemTag superItemTag) {
        this.type = null;
        this.material = null;
        this.isAbstract = true;
        this.tag = null;
        this.superItemTag = superItemTag;
    }

    public SuperItemType getType() {
        return type;
    }

    public boolean isSimilar(SuperItemData data) {
        if (data.isAbstract && this.isAbstract) {
            return data.tag == this.tag;
        } else if (data.isAbstract) {
            if (data.tag != null) {
                return data.tag.isTagged(this.material);
            } else {
                return data.superItemTag.getSuperItemTypes().contains(this.type);
            }
        } else if (this.isAbstract) {
            if (this.tag != null) {
                return this.tag.isTagged(data.material);
            } else {
                return this.superItemTag.getSuperItemTypes().contains(data.type);
            }
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

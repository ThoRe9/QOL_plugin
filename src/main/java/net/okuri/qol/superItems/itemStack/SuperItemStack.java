package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SuperItemStack extends ItemStack {
    private SuperItemType superItemType;
    private SuperItem superItemClass;

    public SuperItemStack(ItemStack stack) {
        // 浅いコピーではなく、深いコピーを行っている
        // そのため、このクラスをいじっても元のアイテム情報は変化しないことに注意
        super(stack);
        if (PDCC.has(super.getItemMeta(), PDCKey.TYPE)) {
            if (this.superItemType == SuperItemType.DEFAULT) {
                this.initialize(stack.getType(), stack.getAmount());
            } else {
                this.initialize(SuperItemType.valueOf(PDCC.get(super.getItemMeta(), PDCKey.TYPE)), stack.getAmount());
            }
        } else {
            this.initialize(stack.getType(), stack.getAmount());
        }
    }

    public SuperItemStack(Material material) {
        this(material, 1);
    }

    public SuperItemStack(Material material, int amount) {
        super(material, amount);
        this.initialize(material, amount);
    }

    public SuperItemStack(@NotNull SuperItemType type) {
        this(type, 1);
    }

    public SuperItemStack(@NotNull SuperItemType type, int amount) {
        super(type.getMaterial(), amount);
        this.initialize(type, amount);
    }

    private void initialize(Material material, int amount) {
        this.setType(material);
        this.setAmount(amount);
        this.superItemType = SuperItemType.DEFAULT;
        this.superItemType.setMaterial(material);
        this.superItemClass = SuperItemType.getSuperItemClass(this.superItemType);
        ItemMeta meta = this.getItemMeta();
        if (PDCC.has(meta, PDCKey.TYPE)) {
            if (SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE)) != this.superItemType) {
                throw new IllegalArgumentException("SuperItemTypeが一致しません。");
            }
        } else {
            PDCC.set(meta, PDCKey.TYPE, this.superItemType.toString());
        }
        meta.setCustomModelData(this.superItemType.getCustomModelData());
        this.setItemMeta(meta);
    }

    private void initialize(SuperItemType type, int amount) {
        this.setType(type.getMaterial());
        this.setAmount(amount);
        this.superItemType = type;
        this.superItemClass = SuperItemType.getSuperItemClass(this.superItemType);
        ItemMeta meta = this.getItemMeta();
        if (PDCC.has(meta, PDCKey.TYPE)) {
            if (SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE)) != this.superItemType) {
                throw new IllegalArgumentException("SuperItemTypeが一致しません。");
            }
        } else {
            PDCC.set(meta, PDCKey.TYPE, this.superItemType.toString());
        }
        meta.setCustomModelData(this.superItemType.getCustomModelData());
        this.setItemMeta(meta);
    }

    public boolean isSimilar(SuperItemStack item) {
        if (item == null) return false;
        if (this.superItemType == SuperItemType.DEFAULT && item.getSuperItemType() == SuperItemType.DEFAULT) {
            return (item.getType() == this.getType());
        } else {
            return (this.superItemType == item.getSuperItemType());
        }
    }

    @Override
    public boolean isSimilar(ItemStack item) {
        SuperItemStack superItemStack = new SuperItemStack(item);
        return this.isSimilar(superItemStack);
    }

    public boolean isSimilar(SuperItemType type) {
        if (type == null) return false;
        if (this.superItemType == SuperItemType.DEFAULT && type == SuperItemType.DEFAULT) {
            return (this.getType() == type.getMaterial());
        } else {
            return (this.superItemType == type);
        }
    }

    public boolean isConsumable() {
        if (!this.hasItemMeta()) return true;
        ItemMeta meta = this.getItemMeta();
        if (!PDCC.has(meta, PDCKey.CONSUMABLE)) return true;
        return PDCC.get(meta, PDCKey.CONSUMABLE);
    }

    public SuperItemType getSuperItemType() {
        return this.superItemType;
    }

    public void setSuperItemType(SuperItemType type) {
        initialize(type, this.getAmount());
    }

    public SuperItem getSuperItemClass() {
        return this.superItemClass;
    }

}

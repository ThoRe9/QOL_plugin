package net.okuri.qol.superItems.itemStack;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SuperItemStack extends ItemStack {
    private SuperItemData superItemData;
    private SuperItem superItemClass;
    private ProducerInfo producerInfo = null;

    public SuperItemStack(ItemStack stack) {
        // 浅いコピーではなく、深いコピーを行っている
        // そのため、このクラスをいじっても元のアイテム情報は変化しないことに注意
        super(stack);
        if (PDCC.has(super.getItemMeta(), PDCKey.TYPE)) {
            if (SuperItemType.valueOf(PDCC.get(super.getItemMeta(), PDCKey.TYPE)) == SuperItemType.DEFAULT) {
                this.initialize(stack.getType(), stack.getAmount());
            } else {
                this.initialize(SuperItemType.valueOf(PDCC.get(super.getItemMeta(), PDCKey.TYPE)), stack.getAmount());
            }
        } else {
            this.initialize(stack.getType(), stack.getAmount());
        }

        if (PDCC.has(super.getItemMeta(), PDCKey.PRODUCER_INFO)) {
            this.producerInfo = PDCC.getProducerInfo(super.getItemMeta());
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
        assert type != SuperItemType.DEFAULT;
        this.initialize(type, amount);
    }

    private void initialize(Material material, int amount) {
        this.setType(material);
        this.setAmount(amount);
        this.superItemData = new SuperItemData(material);
        this.superItemClass = SuperItemType.getSuperItemClass(this.superItemData.getType());
        ItemMeta meta = this.getItemMeta();
        if (PDCC.has(meta, PDCKey.TYPE)) {
            if (SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE)) != this.superItemData.getType()) {
                throw new IllegalArgumentException("SuperItemTypeが一致しません。");
            }
        } else {
            PDCC.set(meta, PDCKey.TYPE, this.superItemData.getType().toString());
        }
        meta.setCustomModelData(this.superItemData.getType().getCustomModelData());
        this.setItemMeta(meta);
    }

    private void initialize(SuperItemType type, int amount) {
        this.setType(type.getMaterial());
        this.setAmount(amount);
        this.superItemData = new SuperItemData(type);
        this.superItemClass = SuperItemType.getSuperItemClass(this.superItemData.getType());
        ItemMeta meta = this.getItemMeta();
        if (PDCC.has(meta, PDCKey.TYPE)) {
            if (SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE)) != this.superItemData.getType()) {
                throw new IllegalArgumentException("SuperItemTypeが一致しません。");
            }
        } else {
            PDCC.set(meta, PDCKey.TYPE, this.superItemData.getType().toString());
        }
        meta.setCustomModelData(this.superItemData.getType().getCustomModelData());
        this.setItemMeta(meta);
    }

    public boolean isSimilar(SuperItemStack item) {
        if (item == null) return false;
        return this.superItemData.isSimilar(item.superItemData);
    }

    @Override
    public boolean isSimilar(ItemStack item) {
        SuperItemStack superItemStack = new SuperItemStack(item);
        return this.isSimilar(superItemStack);
    }

    public boolean isSimilar(@NotNull SuperItemData data) {
        return this.superItemData.isSimilar(data);
    }

    public boolean isConsumable() {
        if (!this.hasItemMeta()) return true;
        ItemMeta meta = this.getItemMeta();
        if (!PDCC.has(meta, PDCKey.CONSUMABLE)) return true;
        return PDCC.get(meta, PDCKey.CONSUMABLE);
    }

    public SuperItemType getSuperItemType() {
        return this.superItemData.getType();
    }

    public SuperItemData getSuperItemData() {
        return this.superItemData;
    }

    public void setSuperItemType(SuperItemType type) {
        initialize(type, this.getAmount());
    }

    public SuperItem getSuperItemClass() {
        return this.superItemClass;
    }

    public void setDisplayName(Component display) {
        ItemMeta meta = super.getItemMeta();
        meta.displayName(display);
        super.setItemMeta(meta);
    }

    public void setLore(LoreGenerator lore) {
        ItemMeta meta = super.getItemMeta();
        meta.lore(lore.generateLore());
        super.setItemMeta(meta);
    }

    public void setConsumable(boolean consumable) {
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.CONSUMABLE, consumable);
        super.setItemMeta(meta);
    }

    public ProducerInfo getProducerInfo() {
        assert this.hasProducerInfo();
        return this.producerInfo;
    }

    public void setProducerInfo(ProducerInfo producerInfo) {
        this.producerInfo = producerInfo;
        ItemMeta meta = super.getItemMeta();
        PDCC.setProducerInfo(meta, producerInfo);
        super.setItemMeta(meta);
    }

    public boolean hasProducerInfo() {
        return this.producerInfo != null;
    }

    public boolean isFarmerTool() {
        if (!this.hasItemMeta()) return false;
        if (!PDCC.has(this.getItemMeta(), PDCKey.FARMER_TOOL)) return false;
        return PDCC.get(this.getItemMeta(), PDCKey.FARMER_TOOL);
    }

    public boolean isMinerTool() {
        if (!this.hasItemMeta()) return false;
        if (!PDCC.has(this.getItemMeta(), PDCKey.MINER_TOOL)) return false;
        return PDCC.get(this.getItemMeta(), PDCKey.MINER_TOOL);
    }
}

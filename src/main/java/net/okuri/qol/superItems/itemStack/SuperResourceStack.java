package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.resources.SuperResource;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SuperResourceStack extends SuperItemStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにSuperItemTag.RESOURCEを持つSuperItemStack全般を表します。
    private double x;
    private double y;
    private double z;
    private double temp;
    private double humid;
    private int biomeId;
    private double quality;
    private double rarity;
    private String producer;

    public SuperResourceStack(ItemStack stack) {
        super(stack);
        assert this.getSuperItemType().hasTag(SuperItemTag.RESOURCE);
        ItemMeta meta = stack.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);
        this.biomeId = PDCC.get(meta, PDCKey.BIOME_ID);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.rarity = PDCC.get(meta, PDCKey.RARITY);
        this.producer = PDCC.get(meta, PDCKey.PRODUCER);
    }

    public SuperResourceStack(@NotNull SuperItemType type, SuperResource factory) {
        this(type, 1, factory);
        this.setParameters(factory);
    }

    public SuperResourceStack(@NotNull SuperItemType type, int amount, SuperResource factory) {
        super(type, amount);
        assert type.hasTag(SuperItemTag.RESOURCE);
        this.setParameters(factory);
    }

    private void setParameters(SuperResource factory) {
        this.x = factory.getX();
        this.y = factory.getY();
        this.z = factory.getZ();
        this.temp = factory.getTemp();
        this.humid = factory.getHumid();
        this.biomeId = factory.getBiomeId();
        this.quality = factory.getQuality();
        this.rarity = factory.getRarity();
        this.producer = factory.getProducerName();
        ItemMeta meta = this.getItemMeta();
        PDCC.setSuperResource(meta, factory);
        this.setItemMeta(meta);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.rarity = SuperItem.getRarity(x, y, z);
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.RARITY, rarity);
        this.setItemMeta(meta);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.rarity = SuperItem.getRarity(x, y, z);
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.RARITY, rarity);
        this.setItemMeta(meta);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        this.rarity = SuperItem.getRarity(x, y, z);
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.Z, z);
        PDCC.set(meta, PDCKey.RARITY, rarity);
        this.setItemMeta(meta);
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.TEMP, temp);
        this.setItemMeta(meta);
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.HUMID, humid);
        this.setItemMeta(meta);
    }

    public int getBiomeId() {
        return biomeId;
    }

    public void setBiomeId(int biomeId) {
        this.biomeId = biomeId;
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.BIOME_ID, biomeId);
        this.setItemMeta(meta);
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.x = this.x / this.quality * quality;
        this.y = this.y / this.quality * quality;
        this.z = this.z / this.quality * quality;
        this.quality = quality;
        ItemMeta meta = this.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        PDCC.set(meta, PDCKey.QUALITY, quality);
        this.setItemMeta(meta);
    }
}

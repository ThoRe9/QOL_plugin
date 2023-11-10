package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SuperXYZStack extends SuperItemStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにLIQUOR,RESOURCE, INGREDIENTを持つSuperItemStack全般を表します。
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private int rarity = 0;
    private double quality = 1;
    private boolean hasTSC = false;
    private double taste = 0;
    private double smell = 0;
    private double compatibility = 0;

    public SuperXYZStack(ItemStack stack) {
        // 浅いコピーではなく、深いコピーを行っている
        // そのため、このクラスをいじっても元のアイテム情報は変化しないことに注意
        super(stack);
        ItemMeta meta = super.getItemMeta();
        x = PDCC.get(meta, PDCKey.X);
        y = PDCC.get(meta, PDCKey.Y);
        z = PDCC.get(meta, PDCKey.Z);
        quality = PDCC.get(meta, PDCKey.QUALITY);
        if (PDCC.has(meta, PDCKey.TASTE_RICHNESS)) {
            hasTSC = true;
            taste = PDCC.get(meta, PDCKey.TASTE_RICHNESS);
            smell = PDCC.get(meta, PDCKey.SMELL_RICHNESS);
            compatibility = PDCC.get(meta, PDCKey.COMPATIBILITY);
        }
        calcRarity();
    }

    public SuperXYZStack(SuperItemStack stack) {
        super(stack);
        ItemMeta meta = super.getItemMeta();
        x = PDCC.get(meta, PDCKey.X);
        y = PDCC.get(meta, PDCKey.Y);
        z = PDCC.get(meta, PDCKey.Z);
        quality = PDCC.get(meta, PDCKey.QUALITY);
        if (PDCC.has(meta, PDCKey.TASTE_RICHNESS)) {
            hasTSC = true;
            taste = PDCC.get(meta, PDCKey.TASTE_RICHNESS);
            smell = PDCC.get(meta, PDCKey.SMELL_RICHNESS);
            compatibility = PDCC.get(meta, PDCKey.COMPATIBILITY);
        }
        calcRarity();
    }

    public SuperXYZStack(Material material, int amount, boolean hasTSC) {
        super(material, amount);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        PDCC.set(meta, PDCKey.QUALITY, quality);
        if (hasTSC) {
            this.hasTSC = true;
            PDCC.set(meta, PDCKey.TASTE_RICHNESS, taste);
            PDCC.set(meta, PDCKey.SMELL_RICHNESS, smell);
            PDCC.set(meta, PDCKey.COMPATIBILITY, compatibility);
        }
        super.setItemMeta(meta);
        calcRarity();
    }

    public SuperXYZStack(Material material, boolean hasTSC) {
        this(material, 1, hasTSC);
    }

    public SuperXYZStack(@NotNull SuperItemType type, boolean hasTSC) {
        this(type, 1, hasTSC);
    }


    public SuperXYZStack(@NotNull SuperItemType type, int amount, boolean hasTSC) {
        super(type, amount);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        PDCC.set(meta, PDCKey.QUALITY, quality);
        if (hasTSC) {
            this.hasTSC = true;
            PDCC.set(meta, PDCKey.TASTE_RICHNESS, taste);
            PDCC.set(meta, PDCKey.SMELL_RICHNESS, smell);
            PDCC.set(meta, PDCKey.COMPATIBILITY, compatibility);
        }
        super.setItemMeta(meta);
        calcRarity();
    }

    public void calcRarity() {
        this.rarity = SuperItem.getRarity(x, y, z);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.RARITY, rarity);
        super.setItemMeta(meta);
    }

    public int getQOLRarity() {
        return rarity;
    }

    public boolean isHasTSC() {
        return hasTSC;
    }

    public void setHasTSC(boolean hasTSC) {
        this.hasTSC = hasTSC;
    }

    public double getTaste() {
        assert hasTSC;
        return taste;
    }

    public void setTaste(double taste) {
        assert hasTSC;
        this.taste = taste;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.TASTE_RICHNESS, taste);
        super.setItemMeta(meta);
    }

    public double getSmell() {
        assert hasTSC;
        return smell;
    }

    public void setSmell(double smell) {
        assert hasTSC;
        this.smell = smell;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.SMELL_RICHNESS, smell);
        super.setItemMeta(meta);
    }

    public double getCompatibility() {
        assert hasTSC;
        return compatibility;
    }

    public void setCompatibility(double compatibility) {
        assert hasTSC;
        this.compatibility = compatibility;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.COMPATIBILITY, compatibility);
        super.setItemMeta(meta);
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        super.setItemMeta(meta);
        calcRarity();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Y, y);
        super.setItemMeta(meta);
        calcRarity();
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Z, z);
        super.setItemMeta(meta);
        calcRarity();
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.x = this.x / this.quality * quality;
        this.y = this.y / this.quality * quality;
        this.z = this.z / this.quality * quality;
        this.quality = quality;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        PDCC.set(meta, PDCKey.QUALITY, quality);
        super.setItemMeta(meta);
        calcRarity();
    }
}

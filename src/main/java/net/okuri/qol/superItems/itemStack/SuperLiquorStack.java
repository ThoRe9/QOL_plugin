package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperLiquorStack extends SuperXYZStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにSuperItemTag.DRINKを持つSuperItemStack全般を表します。

    private double x;
    private double y;
    private double z;
    private double taste;
    private double smell;
    private double compatibility;
    private int rarity;

    public SuperLiquorStack(@NotNull SuperItemType type) {
        this(type, 1);
    }

    public SuperLiquorStack(@NotNull SuperItemType type, int amount) {
        super(type, amount);
        assert type.hasTag(SuperItemTag.LIQUOR);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.ALCOHOL, true);
        super.setItemMeta(meta);

        this.setX(0.0);
        this.setY(0.0);
        this.setZ(0.0);
        this.setTemp(0.0);
        this.setHumid(0.0);
        this.setBiomeId(0);
        this.setQuality(0.0);
        this.setRarity(0);
        this.setProducer("");
        this.setAlcoholAmount(0.0);
        this.setAlcoholPercentage(0.0);
        this.setAmplifierLine(0.0);
        this.setMaxDuration(0);
        this.setTaste(0.0);
        this.setSmell(0.0);
        this.setCompatibility(0.0);
        this.setXEffectType(null);
        this.setYEffectType(null);
        this.setZEffectType(null);

    }

    public SuperLiquorStack(ItemStack stack) {
        super(stack);
        assert this.getSuperItemType().hasTag(SuperItemTag.LIQUOR);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);
        this.biomeId = PDCC.get(meta, PDCKey.BIOME_ID);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.rarity = PDCC.get(meta, PDCKey.RARITY);
        this.producer = PDCC.get(meta, PDCKey.PRODUCER);
        this.alcoholAmount = PDCC.get(meta, PDCKey.ALCOHOL_AMOUNT);
        this.alcoholPercentage = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);
        this.maxDuration = PDCC.get(meta, PDCKey.MAX_DURATION);
        this.amplifierLine = PDCC.get(meta, PDCKey.DIVLINE);
        Liquor liquor = (Liquor) SuperItemType.getSuperItemClass(this.getSuperItemType());
        this.xEffectType = liquor.getXEffectType();
        this.yEffectType = liquor.getYEffectType();
        this.zEffectType = liquor.getZEffectType();

        List<PotionEffect> effects = meta.getCustomEffects();
        for (PotionEffect effect : effects) {
            if (effect.getType() == this.xEffectType) {
                this.xEffect = effect;
            }
            if (effect.getType() == this.yEffectType) {
                this.yEffect = effect;
            }
            if (effect.getType() == this.zEffectType) {
                this.zEffect = effect;
            }
        }
        this.xDuration = this.xEffect.getDuration();
        this.yDuration = this.yEffect.getDuration();
        this.zDuration = this.zEffect.getDuration();
        this.xAmplifier = this.xEffect.getAmplifier();
        this.yAmplifier = this.yEffect.getAmplifier();
        this.zAmplifier = this.zEffect.getAmplifier();
    }

    public double getTaste() {
        return taste;
    }

    public void setTaste(double taste) {
        this.taste = taste;
    }

    private double temp;
    private double humid;
    private int biomeId;
    private double quality;

    public double getSmell() {
        return smell;
    }
    private String producer;
    private PotionEffectType xEffectType;
    private PotionEffectType yEffectType;
    private PotionEffectType zEffectType;
    private int xAmplifier;
    private int yAmplifier;
    private int zAmplifier;
    private int xDuration;
    private int yDuration;
    private int zDuration;
    private double amplifierLine;
    private int maxDuration;
    private double alcoholAmount;
    private double alcoholPercentage;

    // 以下設定不要の変数
    private PotionEffect xEffect;
    private PotionEffect yEffect;
    private PotionEffect zEffect;

    public void setSmell(double smell) {
        this.smell = smell;
    }

    public double getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(double compatibility) {
        this.compatibility = compatibility;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        super.setItemMeta(meta);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Y, y);
        super.setItemMeta(meta);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Z, z);
        super.setItemMeta(meta);
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.TEMP, temp);
        super.setItemMeta(meta);
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.HUMID, humid);
        super.setItemMeta(meta);
    }

    public int getBiomeId() {
        return biomeId;
    }

    public void setBiomeId(int biomeId) {
        this.biomeId = biomeId;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.BIOME_ID, biomeId);
        super.setItemMeta(meta);
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.QUALITY, quality);
        super.setItemMeta(meta);
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.PRODUCER, producer);
        super.setItemMeta(meta);
    }

    public PotionEffectType getXEffectType() {
        return xEffectType;
    }

    public void setXEffectType(PotionEffectType xEffectType) {
        this.xEffectType = xEffectType;
    }

    public PotionEffectType getYEffectType() {
        return yEffectType;
    }

    public void setYEffectType(PotionEffectType yEffectType) {
        this.yEffectType = yEffectType;
    }

    public PotionEffectType getZEffectType() {
        return zEffectType;
    }

    public void setZEffectType(PotionEffectType zEffectType) {
        this.zEffectType = zEffectType;
    }

    public double getAmplifierLine() {
        return amplifierLine;
    }

    public void setAmplifierLine(double amplifierLine) {
        this.amplifierLine = amplifierLine;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.DIVLINE, amplifierLine);
        super.setItemMeta(meta);
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.MAX_DURATION, maxDuration);
        super.setItemMeta(meta);
    }

    public double getAlcoholAmount() {
        return alcoholAmount;
    }

    public void setAlcoholAmount(double alcoholAmount) {
        this.alcoholAmount = alcoholAmount;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.ALCOHOL_AMOUNT, alcoholAmount);
        super.setItemMeta(meta);
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.ALCOHOL_PERCENTAGE, alcoholPercentage);
        super.setItemMeta(meta);
    }

    public int getQOLRarity() {
        return this.rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.RARITY, rarity);
        super.setItemMeta(meta);
    }

    public void setXEffect(PotionEffect xEffect) {
        assert xEffect.getType() == this.xEffectType;
        this.xEffect = xEffect;
        PotionMeta meta = (PotionMeta) this.getItemMeta();
        //もし同じ効果があるならそれを消す
        List<PotionEffect> effects = meta.getCustomEffects();
        for (PotionEffect effect : effects) {
            if (effect.getType() == this.xEffectType) {
                meta.removeCustomEffect(effect.getType());
            }
        }
        meta.addCustomEffect(xEffect, true);
        this.setItemMeta(meta);
    }

    public void setYEffect(PotionEffect yEffect) {
        assert yEffect.getType() == this.yEffectType;
        this.yEffect = yEffect;
        PotionMeta meta = (PotionMeta) this.getItemMeta();
        //もし同じ効果があるならそれを消す
        List<PotionEffect> effects = meta.getCustomEffects();
        for (PotionEffect effect : effects) {
            if (effect.getType() == this.yEffectType) {
                meta.removeCustomEffect(effect.getType());
            }
        }
        meta.addCustomEffect(yEffect, true);
        this.setItemMeta(meta);
    }

    public void setZEffect(PotionEffect zEffect) {
        assert zEffect.getType() == this.zEffectType;
        this.zEffect = zEffect;
        PotionMeta meta = (PotionMeta) this.getItemMeta();
        //もし同じ効果があるならそれを消す
        List<PotionEffect> effects = meta.getCustomEffects();
        for (PotionEffect effect : effects) {
            if (effect.getType() == this.zEffectType) {
                meta.removeCustomEffect(effect.getType());
            }
        }
        meta.addCustomEffect(zEffect, true);
        this.setItemMeta(meta);
    }

}

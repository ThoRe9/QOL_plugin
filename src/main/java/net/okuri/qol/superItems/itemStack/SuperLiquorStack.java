package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.adapter.AdapterID;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SuperLiquorStack extends SuperXYZStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにSuperItemTag.LIQUORを持つSuperItemStack全般を表します。


    private double temp;
    private double humid;
    private int biomeId;

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
    private int baseDuration;
    private double alcoholAmount;
    private double alcoholPercentage;
    private ArrayList<AdapterID> adapters = new ArrayList<>();

    private Color potionColor;

    // 以下設定不要の変数
    private PotionEffect xEffect;
    private PotionEffect yEffect;
    private PotionEffect zEffect;


    public SuperLiquorStack(@NotNull SuperItemType type) {
        this(type, 1);
    }

    public SuperLiquorStack(@NotNull SuperItemType type, int amount) {
        super(type, amount, true);


        this.setX(0.0);
        this.setY(0.0);
        this.setZ(0.0);
        this.setTemp(0.0);
        this.setHumid(0.0);
        this.setBiomeId(0);
        this.setQuality(0.0);
        this.setProducer("");
        this.setAlcoholAmount(0.0);
        this.setAlcoholPercentage(0.0);
        this.setAmplifierLine(0.0);
        this.setBaseDuration(0);
        this.setTaste(0.0);
        this.setSmell(0.0);
        this.setCompatibility(0.0);
        this.setPotionColor(Color.WHITE);
        this.setXEffectType(null);
        this.setYEffectType(null);
        this.setZEffectType(null);

    }

    public SuperLiquorStack(ItemStack stack) {
        super(stack);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        PDCC.set(meta, PDCKey.ALCOHOL, true);
        super.setItemMeta(meta);


        if (PDCC.has(meta, PDCKey.TEMP)) {
            this.temp = PDCC.get(meta, PDCKey.TEMP);
        } else {
            this.temp = 0.0;
        }
        if (PDCC.has(meta, PDCKey.HUMID)) {
            this.humid = PDCC.get(meta, PDCKey.HUMID);
        } else {
            this.humid = 0.0;
        }
        if (PDCC.has(meta, PDCKey.BIOME_ID)) {
            this.biomeId = PDCC.get(meta, PDCKey.BIOME_ID);
        } else {
            this.biomeId = 0;
        }
        if (PDCC.has(meta, PDCKey.PRODUCER)) {
            this.producer = PDCC.get(meta, PDCKey.PRODUCER);
        } else {
            this.producer = "";
        }
        if (PDCC.has(meta, PDCKey.LIQUOR_AMOUNT)) {
            this.alcoholAmount = PDCC.get(meta, PDCKey.LIQUOR_AMOUNT);
        } else {
            this.alcoholAmount = 0.0;
        }
        if (PDCC.has(meta, PDCKey.ALCOHOL_PERCENTAGE)) {
            this.alcoholPercentage = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);
        } else {
            this.alcoholPercentage = 0.0;
        }
        if (PDCC.has(meta, PDCKey.BASE_DURATION)) {
            this.baseDuration = PDCC.get(meta, PDCKey.BASE_DURATION);
        } else {
            this.baseDuration = 0;
        }
        if (PDCC.has(meta, PDCKey.DIVLINE)) {
            this.amplifierLine = PDCC.get(meta, PDCKey.DIVLINE);
        } else {
            this.amplifierLine = 0.0;
        }
        if (PDCC.has(meta, PDCKey.ADAPTERS)) {
            this.adapters = PDCC.getAdapters(meta);
        } else {
            this.adapters = new ArrayList<>();
        }
        Liquor liquor = (Liquor) SuperItemType.getSuperItemClass(this.getSuperItemType());
        this.xEffectType = liquor.getXEffectType();
        this.yEffectType = liquor.getYEffectType();
        this.zEffectType = liquor.getZEffectType();
        List<PotionEffect> effects = meta.getCustomEffects();
        for (PotionEffect effect : effects) {
            if (effect.getType().equals(this.xEffectType)) {
                this.xEffect = effect;
                this.xDuration = this.xEffect.getDuration();
                this.xAmplifier = this.xEffect.getAmplifier();
            }
            if (effect.getType().equals(this.yEffectType)) {
                this.yEffect = effect;
                this.yDuration = this.yEffect.getDuration();
                this.yAmplifier = this.yEffect.getAmplifier();
            }
            if (effect.getType().equals(this.zEffectType)) {
                this.zEffect = effect;
                this.zDuration = this.zEffect.getDuration();
                this.zAmplifier = this.zEffect.getAmplifier();
            }
        }
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

    public int getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(int baseDuration) {
        this.baseDuration = baseDuration;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.BASE_DURATION, baseDuration);
        super.setItemMeta(meta);
    }

    public double getAlcoholAmount() {
        return alcoholAmount;
    }

    public void setAlcoholAmount(double alcoholAmount) {
        this.alcoholAmount = alcoholAmount;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.LIQUOR_AMOUNT, alcoholAmount);
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

    public Color getPotionColor() {
        return potionColor;
    }

    public void setPotionColor(Color potionColor) {
        this.potionColor = potionColor;
        PotionMeta meta = (PotionMeta) this.getItemMeta();
        meta.setColor(potionColor);
        this.setItemMeta(meta);
    }

    public void addAdapter(AdapterID adapter) {
        ItemMeta meta = super.getItemMeta();
        ArrayList<AdapterID> ids = PDCC.getAdapters(meta);
        ids.add(adapter);
        PDCC.setAdapters(meta, ids);
        super.setItemMeta(meta);
        this.adapters.add(adapter);
    }

    public void setAdapters(ArrayList<AdapterID> adapters) {
        ItemMeta meta = super.getItemMeta();
        PDCC.setAdapters(meta, adapters);
        super.setItemMeta(meta);
        this.adapters = adapters;
    }

    public ArrayList<AdapterID> getAdapters() {
        ItemMeta meta = super.getItemMeta();
        return this.adapters;
    }
}

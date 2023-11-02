package net.okuri.qol.superItems.factory.drinks;

import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Liquor extends SuperItem implements Distributable {
    // このクラスは酒類のアイテムのもとになるクラスです。
    // tag; LIQUORを持つアイテムはこのクラスを必ず継承してください。
    // 使用する場合はコンストラクタでSuperItemTypeを指定して生成してください。
    private PotionEffectType xEffectType;
    private PotionEffectType yEffectType;
    private PotionEffectType zEffectType;
    private int xAmplifier;
    private int yAmplifier;
    private int zAmplifier;
    private int xDuration;
    private int yDuration;
    private int zDuration;
    private double x;
    private double y;
    private double z;
    private int maxDuration;
    private double amplifierLine;
    private SuperResourceStack resource;
    private SuperItemType resourceType;
    private double alcoholAmount;
    private double alcoholPercentage;

    // 以下設定不要
    private PotionEffect xEffect;
    private PotionEffect yEffect;
    private PotionEffect zEffect;

    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, SuperItemType resourceType, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType, PotionEffectType zEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        assert resourceType.getTag() == SuperItemTag.RESOURCE;
        this.resourceType = resourceType;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.zEffectType = zEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, SuperItemType resourceType, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        assert resourceType.getTag() == SuperItemTag.RESOURCE;
        this.resourceType = resourceType;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, SuperItemType resourceType, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        assert resourceType.getTag() == SuperItemTag.RESOURCE;
        this.resourceType = resourceType;
        this.xEffectType = xEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    @Override
    public SuperItemStack getSuperItem() {
        return null;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return null;
    }

    public void setParmeter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        return false;
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {

    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {

    }

    public PotionEffectType getXEffectType() {
        return this.xEffectType;
    }

    public PotionEffectType getYEffectType() {
        return this.yEffectType;
    }

    public PotionEffectType getZEffectType() {
        return this.zEffectType;
    }

    public PotionEffect getXEffect() {
        return this.xEffect;
    }

    public PotionEffect getYEffect() {
        return this.yEffect;
    }

    public PotionEffect getZEffect() {
        return this.zEffect;
    }
}

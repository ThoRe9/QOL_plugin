package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.drinks.Liquor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperLiquorStack extends SuperItemStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにSuperItemTag.DRINKを持つSuperItemStack全般を表します。

    private double x;
    private double y;
    private double z;
    private double temp;
    private double humid;
    private int biomeId;
    private double quality;
    private double rarity;
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

    public SuperLiquorStack(@NotNull SuperItemType type, Liquor factory) {
        this(type, 1, factory);
        this.setParameters(factory);
    }

    public SuperLiquorStack(@NotNull SuperItemType type, int amount, Liquor factory) {
        super(type, amount);
        assert type.hasTag(SuperItemTag.RESOURCE);
        this.setParameters(factory);
    }

    private void setParameters(Liquor factory) {

    }
}

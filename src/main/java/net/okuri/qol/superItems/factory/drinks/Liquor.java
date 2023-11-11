package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.CraftableXYZItem;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperLiquorStack;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Liquor extends CraftableXYZItem implements Distributable, DistributionReceiver, Distillable, Maturable, SuperCraftable {
    // このクラスは酒類のアイテムのもとになるクラスです。
    // tag; LIQUORを持つアイテムはこのクラスを必ず継承してください。
    // 使用する場合はコンストラクタでSuperItemTypeを指定して生成してください。
    private PotionEffectType xEffectType;
    private PotionEffectType yEffectType;
    private PotionEffectType zEffectType;
    /*
    mainIngredients: x,y,zパラメータの基礎になる材料
    subIngredients: smell,taste,compatibilityの基礎になる材料
    mainBuffIngredients: x,y,zパラメータの補正になる材料
    subBuffIngredients: smell,taste,compatibilityの補正になる材料
    すべて多数ある場合、それらの平均値を取る
     */


    private int baseDuration;
    private double amplifierLine;
    private double alcoholAmount;
    private double alcoholPercentage;

    private Component displayName;
    private String infoLore;
    private double defaultMaturationDays = 1.0;
    // 以下オプション
    private double temp = 0;
    private double humid = 0;
    private int biomeId = 0;
    private String producer = "";

    // 以下設定不要
    private PotionEffect xEffect;
    private PotionEffect yEffect;
    private PotionEffect zEffect;
    private int xAmplifier;
    private int yAmplifier;
    private int zAmplifier;
    private int xDuration;
    private int yDuration;
    private int zDuration;
    private double maturationDays;
    private int rarity;

    private Color potionColor;


    public Liquor(SuperItemType type, int baseDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType, PotionEffectType zEffectType) {
        super(type, true);
        this.baseDuration = baseDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.zEffectType = zEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int baseDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType) {
        super(type, true);
        this.baseDuration = baseDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int baseDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType) {
        super(type, true);
        this.baseDuration = baseDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    private void initialize(SuperLiquorStack liquorStack) {
        super.initialize(liquorStack);
        this.xEffectType = liquorStack.getXEffectType();
        this.yEffectType = liquorStack.getYEffectType();
        this.zEffectType = liquorStack.getZEffectType();
        this.baseDuration = liquorStack.getBaseDuration();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.alcoholAmount = liquorStack.getAlcoholAmount();
        this.alcoholPercentage = liquorStack.getAlcoholPercentage();
        this.displayName = liquorStack.displayName();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.producer = liquorStack.getProducer();
        this.temp = liquorStack.getTemp();
        this.humid = liquorStack.getHumid();
        this.biomeId = liquorStack.getBiomeId();
    }



    @Override
    public SuperLiquorStack getSuperItem() {
        SuperLiquorStack item = new SuperLiquorStack(super.getSuperItem());
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(this.potionColor);
        item.setItemMeta(meta);

        double newX = item.getX() * this.getCompatibility();
        double newY = item.getY() * this.getCompatibility();
        double newZ = item.getZ() * this.getCompatibility();


        this.xAmplifier = (int) Math.floor(newX * calcAmplifierAmp());
        this.yAmplifier = (int) Math.floor(newY * calcAmplifierAmp());
        this.zAmplifier = (int) Math.floor(newZ * calcAmplifierAmp());
        Bukkit.getLogger().info(String.valueOf(newX * baseDuration * calcDurationAmp() * this.alcoholAmount));
        this.xDuration = (int) Math.floor(newX * baseDuration * calcDurationAmp() * this.alcoholAmount);
        this.yDuration = (int) Math.floor(newY * baseDuration * calcDurationAmp() * this.alcoholAmount);
        this.zDuration = (int) Math.floor(newZ * baseDuration * calcDurationAmp() * this.alcoholAmount);
        if (xEffectType != null) {
            this.xEffect = new PotionEffect(this.xEffectType, this.xDuration, this.xAmplifier);
        }
        if (yEffectType != null) {
            this.yEffect = new PotionEffect(this.yEffectType, this.yDuration, this.yAmplifier);
        }
        if (zEffectType != null) {
            this.zEffect = new PotionEffect(this.zEffectType, this.zDuration, this.zAmplifier);
        }
        this.rarity = SuperItem.getRarity(newX, newY, newZ);

        LoreGenerator lore = new LoreGenerator();
        lore.setLiquorLore(newX, newY, newZ, this.getTaste(), this.getSmell(), this.getCompatibility(), this.alcoholAmount, this.alcoholPercentage, this.getQuality(), this.rarity);
        lore.addInfoLore(this.infoLore);
        if (maturationDays > 0) {
            lore.addParametersLore("Maturation Days", this.maturationDays, true);
        }
        lore.addInfoLore("made by " + this.producer);

        item.setX(newX);
        item.setY(newY);
        item.setZ(newZ);
        item.setXEffectType(this.xEffectType);
        item.setYEffectType(this.yEffectType);
        item.setZEffectType(this.zEffectType);
        item.setTemp(this.temp);
        item.setHumid(this.humid);
        item.setBiomeId(this.biomeId);
        item.setQuality(super.getQuality());
        item.setProducer(this.producer);
        item.setAlcoholAmount(this.alcoholAmount);
        item.setAlcoholPercentage(this.alcoholPercentage);
        item.setBaseDuration(this.baseDuration);
        item.setAmplifierLine(this.amplifierLine);
        item.setDisplayName(this.displayName);
        item.setLore(lore);
        if (this.xEffect != null) {
            item.setXEffect(this.xEffect);
        }
        if (this.yEffect != null) {
            item.setYEffect(this.yEffect);
        }
        if (this.zEffect != null) {
            item.setZEffect(this.zEffect);
        }
        return item;
    }

    private double calcDurationAmp() {
        return this.getSmell() + 1;
    }

    private double calcAmplifierAmp() {
        return this.getTaste() / this.amplifierLine;
    }

    @Override
    public SuperLiquorStack getDebugItem(int... args) {
        return this.getSuperItem();
    }


    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        return this.alcoholAmount >= smallBottleAmount * smallBottleCount;
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {
        assert this.alcoholAmount > smallBottleAmount * smallBottleCounts;
        this.alcoholAmount = this.alcoholAmount - smallBottleAmount * smallBottleCounts;
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        if (Objects.equals(id, "distribution")) {
            // 分配(親)の場合
            SuperLiquorStack liquor = (SuperLiquorStack) matrix[0];
            this.initialize(liquor);
        } else if (Objects.equals(id, "distribution_receiver")) {
            // 分配(子)の場合
            SuperLiquorStack liquor = (SuperLiquorStack) matrix[0];
            this.initialize(liquor);
            this.displayName = this.displayName.append(Component.text("(" + this.alcoholAmount + "ml)").color(this.displayName.color()));
        } else {
            super.setMatrix(matrix, id);
        }
    }



    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
        // TODO 要調整
        SuperLiquorStack liquor = new SuperLiquorStack(item);
        this.temp = liquor.getTemp();
        this.humid = liquor.getHumid();
        this.biomeId = liquor.getBiomeId();
        this.producer = liquor.getProducer();

        if (item.getSuperItemData().getType().getTag() == this.getSuperItemData().getType().getTag()) {
            this.initialize(new SuperLiquorStack(item));
            this.alcoholPercentage = this.alcoholPercentage + (1 - Math.abs(1 - Math.abs(temp))) * 0.10;
            double amountMod = Math.pow(Math.abs(1.0 - temp) * (1.0 - humid), 0.1);
            this.alcoholAmount = this.alcoholAmount * amountMod;
            // パラメータの増減量の設定：
            // 増 : + 他パラメータから引いた分 * 0.9 * (1+compatibility)
            // 減 : - そのパラメータ * 0.2 * (1+compatibility)
            // 最大のパラメータを増やし、他のパラメータを減らす。
            // 以下処理
            double dx;
            double dy;
            double dz;
            double x = super.getX();
            double y = super.getY();
            double z = super.getZ();
            double compatibility = super.getCompatibility();
            if (x > y && x > z) {
                dy = -y * 0.2 * (1 + compatibility);
                dz = -z * 0.2 * (1 + compatibility);
                dx = -(dy + dz) * 0.9 * (1 + compatibility);
            } else if (y > x && y > z) {
                dx = -x * 0.2 * (1 + compatibility);
                dz = -z * 0.2 * (1 + compatibility);
                dy = -(dx + dz) * 0.9 * (1 + compatibility);
            } else {
                dx = -x * 0.2 * (1 + compatibility);
                dy = -y * 0.2 * (1 + compatibility);
                dz = -(dx + dy) * 0.9 * (1 + compatibility);
            }
            x = x + dx;
            y = y + dy;
            z = z + dz;
            super.setX(x);
            super.setY(y);
            super.setZ(z);
        } else {

            super.setX(liquor.getX());
            super.setY(liquor.getY());
            super.setZ(liquor.getZ());
            super.setSmell(liquor.getSmell());
            super.setTaste(liquor.getTaste());
            super.setCompatibility(liquor.getCompatibility());
            this.alcoholAmount = liquor.getAlcoholAmount();
            this.temp = liquor.getTemp();
            this.humid = liquor.getHumid();
            this.biomeId = liquor.getBiomeId();
            this.producer = liquor.getProducer();

        }
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        // TODO 要調整
        // defaultDaysの何倍の日数熟成したかでパラメータが変化
        // 10倍の日数ですべてのパラメータ1.5倍!
        // ただし、アルコール量も1/2 になる

        this.initialize(new SuperLiquorStack(ingredients.get(0)));
        Duration dur = Duration.between(start, end);
        double days = dur.toDays();
        this.maturationDays = days;
        this.alcoholAmount *= calcAmountMod(days);
        this.alcoholPercentage *= calcAlcPerMod(days);
        super.setTasteBuff(super.getTasteBuff() * calcTasteMod(days));
        super.setSmellBuff(super.getSmellBuff() * calcSmellMod(days));

    }

    private double calcAlcPerMod(double days) {
        return 0.1 * days / this.defaultMaturationDays + 1;
    }

    private double calcSmellMod(double days) {
        return 2.0 - this.defaultMaturationDays / (0.1 * days + this.defaultMaturationDays);
    }

    private double calcAmountMod(double days) {
        return this.defaultMaturationDays / (0.1 * days + this.defaultMaturationDays);
    }

    private double calcTasteMod(double days) {
        return 2.0 - this.defaultMaturationDays / (0.1 * days + this.defaultMaturationDays);
    }

    @Override
    public void receive(int count) {
        super.setCount(count);
    }

    @Override
    public double getAmount() {
        return this.alcoholAmount;
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

    public Component getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
    }

    public String getInfoLore() {
        return infoLore;
    }

    public void setInfoLore(String infoLore) {
        this.infoLore = infoLore;
    }

    public int getXAmplifier() {
        return xAmplifier;
    }

    public int getYAmplifier() {
        return yAmplifier;
    }

    public int getZAmplifier() {
        return zAmplifier;
    }

    public int getXDuration() {
        return xDuration;
    }

    public int getYDuration() {
        return yDuration;
    }

    public int getZDuration() {
        return zDuration;
    }

    public double getDefaultMaturationDays() {
        return defaultMaturationDays;
    }

    public void setDefaultMaturationDays(double defaultMaturationDays) {
        this.defaultMaturationDays = defaultMaturationDays;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    public int getBiomeId() {
        return biomeId;
    }

    public void setBiomeId(int biomeId) {
        this.biomeId = biomeId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(int maxDuration) {
        this.baseDuration = maxDuration;
    }

    public double getAmplifierLine() {
        return amplifierLine;
    }

    public void setAmplifierLine(double amplifierLine) {
        this.amplifierLine = amplifierLine;
    }

    public double getAlcoholAmount() {
        return alcoholAmount;
    }

    public void setAlcoholAmount(double alcoholAmount) {
        this.alcoholAmount = alcoholAmount;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public Color getPotionColor() {
        return potionColor;
    }

    public void setPotionColor(Color potionColor) {
        this.potionColor = potionColor;
    }

}

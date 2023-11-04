package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.calcuration.StatisticalCalcuration;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperLiquorStack;
import net.okuri.qol.superItems.itemStack.SuperXYZStack;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Liquor extends SuperItem implements Distributable, DistributionReceiver, Distillable, Maturable, SuperCraftable {
    // このクラスは酒類のアイテムのもとになるクラスです。
    // tag; LIQUORを持つアイテムはこのクラスを必ず継承してください。
    // 使用する場合はコンストラクタでSuperItemTypeを指定して生成してください。
    private PotionEffectType xEffectType;
    private PotionEffectType yEffectType;
    private PotionEffectType zEffectType;
    private double x;
    private double y;
    private double z;
    /*
    mianIngredients: x,y,zパラメータの基礎になる材料
    subIngredients: smell,taste,compatibilityの基礎になる材料
    mainBuffIngredients: x,y,zパラメータの補正になる材料
    subBuffIngredients: smell,taste,compatibilityの補正になる材料
    すべて多数ある場合、それらの平均値を取る
     */
    private final ArrayList<SuperItemType> mainIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> subIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> mainBuffIngredients = new ArrayList<>();
    private int maxDuration;
    private double amplifierLine;
    private double alcoholAmount;
    private double alcoholPercentage;
    private final ArrayList<SuperItemType> subBuffIngredients = new ArrayList<>();
    private double smell;
    private double taste;
    private double compatibility;
    private Component displayName;
    private String infoLore;
    private double defaultMaturationDays = 1.0;
    private double quality;
    // 以下オプション
    private double temp = 0;
    private double humid = 0;
    private int biomeId = 0;
    private String producer = "";
    // x,y,zの補正値。計算はすべて乗算。
    // デフォルトでは一切バフされない設定なので、バフをかける場合はampに1より大きい値を設定する。
    private double allBuff = 1;
    private double allBuffAmp = 1;
    private double xBuff = 1;
    private double xBuffAmp = 0;
    private double yBuff = 1;
    private double yBuffAmp = 0;
    private double zBuff = 1;
    private double zBuffAmp = 0;
    private double compatibilityMin = 1;
    private double compatibilityMax = 1;
    private double subAllBuff = 1;
    private double subAllBuffAmp = 0;
    private double smellBuff = 1;
    private double smellBuffAmp = 0;
    private double tasteBuff = 1;
    private double tasteBuffAmp = 0;
    private double compatibilityBuff = 1;
    private double compatibilityBuffAmp = 0;
    // 以下設定不要
    private int itemCount = 1;
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


    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType, PotionEffectType zEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.zEffectType = zEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType, PotionEffectType yEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.yEffectType = yEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    public Liquor(SuperItemType type, int maxDuration, double amplifierLine, double alcoholAmount, double alcoholPercentage, PotionEffectType xEffectType) {
        super(type);
        this.maxDuration = maxDuration;
        this.amplifierLine = amplifierLine;
        this.xEffectType = xEffectType;
        this.alcoholAmount = alcoholAmount;
        this.alcoholPercentage = alcoholPercentage;
    }

    private void initialize(SuperLiquorStack liquorStack) {
        this.xEffectType = liquorStack.getXEffectType();
        this.yEffectType = liquorStack.getYEffectType();
        this.zEffectType = liquorStack.getZEffectType();
        this.x = liquorStack.getX();
        this.y = liquorStack.getY();
        this.z = liquorStack.getZ();
        this.maxDuration = liquorStack.getMaxDuration();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.alcoholAmount = liquorStack.getAlcoholAmount();
        this.alcoholPercentage = liquorStack.getAlcoholPercentage();
        this.displayName = liquorStack.displayName();
        this.maxDuration = liquorStack.getMaxDuration();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.taste = liquorStack.getTaste();
        this.smell = liquorStack.getSmell();
        this.compatibility = liquorStack.getCompatibility();
        this.quality = liquorStack.getQuality();
        this.producer = liquorStack.getProducer();
        this.temp = liquorStack.getTemp();
        this.humid = liquorStack.getHumid();
        this.biomeId = liquorStack.getBiomeId();

    }

    @Override
    public SuperItemStack getSuperItem() {
        Bukkit.getLogger().info("getSuperItem called");
        double newX = this.x * this.xBuff * this.allBuff * this.quality;
        double newY = this.y * this.yBuff * this.allBuff * this.quality;
        double newZ = this.z * this.zBuff * this.allBuff * this.quality;
        this.xAmplifier = (int) Math.floor(newX / this.amplifierLine);
        this.yAmplifier = (int) Math.floor(newY / this.amplifierLine);
        this.zAmplifier = (int) Math.floor(newZ / this.amplifierLine);
        this.xDuration = (int) Math.floor(newX * this.maxDuration);
        this.yDuration = (int) Math.floor(newY * this.maxDuration);
        this.zDuration = (int) Math.floor(newZ * this.maxDuration);
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
        lore.setSuperItemLore(newX, newY, newZ, this.quality, this.rarity, this.infoLore);
        lore.addParametersLore("Smell", this.smell);
        lore.addParametersLore("Taste", this.taste);
        lore.addParametersLore("Compatibility", this.compatibility);
        if (maturationDays > 0) {
            lore.addParametersLore("Maturation Days", this.maturationDays, true);
        }
        lore.addParametersLore("Alcohol Percentage", this.alcoholPercentage, true);
        lore.addParametersLore("Amount", this.alcoholAmount, true);
        lore.addInfoLore(this.producer);
        lore.addInfoLore(this.infoLore);

        SuperLiquorStack item = new SuperLiquorStack(this.getSuperItemType(), this.itemCount);
        item.setXEffectType(this.xEffectType);
        item.setYEffectType(this.yEffectType);
        item.setZEffectType(this.zEffectType);
        item.setTemp(this.temp);
        item.setHumid(this.humid);
        item.setBiomeId(this.biomeId);
        item.setQuality(this.quality);
        item.setRarity(this.rarity);
        item.setProducer(this.producer);
        item.setAlcoholAmount(this.alcoholAmount);
        item.setAlcoholPercentage(this.alcoholPercentage);
        item.setMaxDuration(this.maxDuration);
        item.setAmplifierLine(this.amplifierLine);
        item.setX(newX);
        item.setY(newY);
        item.setZ(newZ);
        item.setSmell(this.smell * this.smellBuff * this.subAllBuff);
        item.setTaste(this.taste * this.tasteBuff * this.subAllBuff);
        item.setCompatibility(this.compatibility * this.compatibilityBuff * this.subAllBuff);
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

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }

    public void setParmeter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        if (id == "distribution") {
            // 分配(親)の場合
            SuperLiquorStack liquor = (SuperLiquorStack) matrix[0];
            this.initialize(liquor);
        } else if (id == "distribution_receiver") {
            // 分配(子)の場合
            SuperLiquorStack liquor = (SuperLiquorStack) matrix[0];
            this.initialize(liquor);
            this.displayName = this.displayName.append(Component.text("(" + String.valueOf(this.alcoholAmount) + "ml)").color(this.displayName.color()));
        } else {
            // SuperCraftの場合
            ArrayList<SuperXYZStack> ingredients = new ArrayList<>();
            ArrayList<SuperXYZStack> buffIngredients = new ArrayList<>();
            ArrayList<SuperXYZStack> subIngredients = new ArrayList<>();
            ArrayList<SuperXYZStack> subBuffIngredients = new ArrayList<>();
            for (SuperItemStack item : matrix) {
                if (item == null) continue;
                SuperItemType type = item.getSuperItemType();
                if (mainIngredients.contains(type)) {
                    ingredients.add((SuperXYZStack) item);
                } else if (mainBuffIngredients.contains(type)) {
                    buffIngredients.add((SuperXYZStack) item);
                } else if (subIngredients.contains(type)) {
                    subIngredients.add((SuperXYZStack) item);
                } else if (subBuffIngredients.contains(type)) {
                    subBuffIngredients.add((SuperXYZStack) item);
                }
            }
            calc(ingredients, buffIngredients, subIngredients, subBuffIngredients);
        }
    }

    private void calc(ArrayList<SuperXYZStack> ingredients, ArrayList<SuperXYZStack> buffIngredients, ArrayList<SuperXYZStack> subIngredients, ArrayList<SuperXYZStack> subBuffIngredients) {
        // ここから各種パラメータの計算
        // 1. x,y,zの計算
        x = 0;
        y = 0;
        z = 0;
        for (SuperXYZStack item : ingredients) {
            x += item.getX();
            y += item.getY();
            z += item.getZ();
        }
        x /= ingredients.size();
        y /= ingredients.size();
        z /= ingredients.size();
        // 2. x,y,zへのbuffの計算
        double buff = 1;
        for (SuperXYZStack item : buffIngredients) {
            buff = buff * (1 + (this.allBuffAmp * (item.getX() + item.getY() + item.getZ()) / 3));
            this.xBuff *= (1 + item.getX() * this.xBuffAmp);
            this.yBuff *= (1 + item.getY() * this.yBuffAmp);
            this.zBuff *= (1 + item.getZ() * this.zBuffAmp);
        }
        this.allBuff *= buff;
        // 3. subIngredientsの計算。taste, smell, compatibilityを計算する。
        // ない場合は現在のx,y,zから計算する。
        double subX = 0;
        double subY = 0;
        double subZ = 0;
        if (subIngredients.size() < 1) {
            subX = x;
            subY = y;
            subZ = z;
        } else {
            for (SuperXYZStack item : subIngredients) {
                subX += item.getX();
                subY += item.getY();
                subZ += item.getZ();
            }
            subX /= subIngredients.size();
            subY /= subIngredients.size();
            subZ /= subIngredients.size();
        }
        StatisticalCalcuration sc = new StatisticalCalcuration();
        sc.setVariable(subX, subY, subZ);
        sc.calcuration();
        double standerdDeviation = sc.getStandardDeviation();
        double max = sc.getMax();
        double min = sc.getMin();
        double mean = sc.getMean();
        this.smell = 1.1 - standerdDeviation * 3;
        this.taste = (0.1 + max - mean) / (1 - max);
        this.compatibility = (((max - min) % min) / min) * (this.compatibilityMax - this.compatibilityMin) + this.compatibilityMin;
        // 4. subBuffIngredientsの計算。taste, smell, compatibilityを計算する。
        subX = 0;
        subY = 0;
        subZ = 0;
        double subBuff = 1;
        for (SuperXYZStack item : subBuffIngredients) {
            subBuff *= (1 + (this.subAllBuffAmp * (item.getX() + item.getY() + item.getZ()) / 3));
            subX += item.getX();
            subY += item.getY();
            subZ += item.getZ();
        }
        subX /= subBuffIngredients.size();
        subY /= subBuffIngredients.size();
        subZ /= subBuffIngredients.size();
        sc = new StatisticalCalcuration();
        sc.setVariable(subX, subY, subZ);
        sc.calcuration();
        standerdDeviation = sc.getStandardDeviation();
        max = sc.getMax();
        min = sc.getMin();
        mean = sc.getMean();
        this.subAllBuff *= subBuff;
        this.smellBuff *= ((1.1 - standerdDeviation * 3) * this.smellBuffAmp + 1.0);
        this.tasteBuff *= (((0.1 + max - mean) / (1 - max)) * this.tasteBuffAmp + 1.0);
        this.compatibilityBuff *= ((((max - min) % min) / min) * (this.compatibilityMax - this.compatibilityMin) + this.compatibilityMin) * this.compatibilityBuffAmp + 1.0;
    }

    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
        // TODO 要調整
        assert item instanceof SuperLiquorStack;
        this.initialize((SuperLiquorStack) item);
        this.alcoholPercentage = this.alcoholPercentage + (1 - Math.abs(1 - Math.abs(temp))) * 0.10;
        double amountMod = Math.pow(Math.abs(1.0 - temp) * (1.0 - humid), 0.5);
        this.alcoholAmount = this.alcoholAmount * amountMod;
        // パラメータの増減量の設定：
        // 増 : + 他パラメータから引いた分 * 0.9 * (1+compatibility)
        // 減 : - そのパラメータ * 0.2 * (1+compatibility)
        // 最大のパラメータを増やし、他のパラメータを減らす。
        // 以下処理
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        if (x > y && x > z) {
            dy = -this.y * 0.2 * (1 + this.compatibility);
            dz = -this.z * 0.2 * (1 + this.compatibility);
            dx = -(dy + dz) * 0.9 * (1 + this.compatibility);
        } else if (y > x && y > z) {
            dx = -this.x * 0.2 * (1 + this.compatibility);
            dz = -this.z * 0.2 * (1 + this.compatibility);
            dy = -(dx + dz) * 0.9 * (1 + this.compatibility);
        } else {
            dx = -this.x * 0.2 * (1 + this.compatibility);
            dy = -this.y * 0.2 * (1 + this.compatibility);
            dz = -(dx + dy) * 0.9 * (1 + this.compatibility);
        }
        this.x = this.x + dx;
        this.y = this.y + dy;
        this.z = this.z + dz;
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        // TODO 要調整
        // defaultDaysの何倍の日数熟成したかでパラメータが変化
        // 10倍の日数ですべてのパラメータ1.5倍!
        // ただし、アルコール量も1/2 になる

        Duration dur = Duration.between(start, end);
        double days = dur.toDays();
        this.maturationDays = days;
        this.alcoholAmount *= calcAmountMod(days);
        this.alcoholPercentage *= calcAlcPerMod(days);
        this.tasteBuff *= calcTasteMod(days);
        this.smellBuff *= calcSmellMod(days);

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
        this.itemCount = count;
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

    public void addMainIngredient(SuperItemType type) {
        assert type.getTag().hasXYZ();
        this.mainIngredients.add(type);
    }

    public void addSubIngredient(SuperItemType type) {
        assert type.getTag().hasXYZ();
        this.subIngredients.add(type);
    }

    public void addMainBuffIngredient(SuperItemType type) {
        assert type.getTag().hasXYZ();
        this.mainBuffIngredients.add(type);
    }

    public void addSubBuffIngredient(SuperItemType type) {
        assert type.getTag().hasXYZ();
        this.subBuffIngredients.add(type);
    }

    public double getAllBuffAmp() {
        return allBuffAmp;
    }

    public void setAllBuffAmp(double allBuffAmp) {
        this.allBuffAmp = allBuffAmp;
    }

    public double getxBuffAmp() {
        return xBuffAmp;
    }

    public void setxBuffAmp(double xBuffAmp) {
        this.xBuffAmp = xBuffAmp;
    }

    public double getyBuffAmp() {
        return yBuffAmp;
    }

    public void setyBuffAmp(double yBuffAmp) {
        this.yBuffAmp = yBuffAmp;
    }

    public double getzBuffAmp() {
        return zBuffAmp;
    }

    public void setzBuffAmp(double zBuffAmp) {
        this.zBuffAmp = zBuffAmp;
    }

    public double getSubAllBuffAmp() {
        return subAllBuffAmp;
    }

    public void setSubAllBuffAmp(double subAllBuffAmp) {
        this.subAllBuffAmp = subAllBuffAmp;
    }

    public double getSmellBuffAmp() {
        return smellBuffAmp;
    }

    public void setSmellBuffAmp(double smellBuffAmp) {
        this.smellBuffAmp = smellBuffAmp;
    }

    public double getTasteBuffAmp() {
        return tasteBuffAmp;
    }

    public void setTasteBuffAmp(double tasteBuffAmp) {
        this.tasteBuffAmp = tasteBuffAmp;
    }

    public double getCompatibilityBuffAmp() {
        return compatibilityBuffAmp;
    }

    public void setCompatibilityBuffAmp(double compatibilityBuffAmp) {
        this.compatibilityBuffAmp = compatibilityBuffAmp;
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

    public int getxAmplifier() {
        return xAmplifier;
    }

    public int getyAmplifier() {
        return yAmplifier;
    }

    public int getzAmplifier() {
        return zAmplifier;
    }

    public int getxDuration() {
        return xDuration;
    }

    public int getyDuration() {
        return yDuration;
    }

    public int getzDuration() {
        return zDuration;
    }

    public double getCompatibilityMin() {
        return compatibilityMin;
    }

    public void setCompatibilityMin(double compatibilityMin) {
        this.compatibilityMin = compatibilityMin;
    }

    public double getCompatibilityMax() {
        return compatibilityMax;
    }

    public void setCompatibilityMax(double compatibilityMax) {
        this.compatibilityMax = compatibilityMax;
    }

    public double getDefaultMaturationDays() {
        return defaultMaturationDays;
    }

    public void setDefaultMaturationDays(double defaultMaturationDays) {
        this.defaultMaturationDays = defaultMaturationDays;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
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
}

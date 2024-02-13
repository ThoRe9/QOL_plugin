package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.CraftableXYZItem;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.adapter.AdapterID;
import net.okuri.qol.superItems.factory.adapter.LiquorAdapter;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperLiquorStack;
import net.okuri.qol.superItems.itemStack.SuperXYZStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private final ArrayList<SuperItemType> maturationIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> distillationIngredients = new ArrayList<>();
    private final Map<SuperItemType, DistributionBuffType> distributionIngredients = new HashMap();
    private MaturationType maturationType = MaturationType.NORMAL;

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
    private boolean copyVariable = true;
    private double baseDurationAmp = 1.0;

    private Color potionColor;
    private double useAmount = 0.0;
    private double distillationAmp = 1.0;
    private DistillationType distillationType = DistillationType.NORMAL;
    private final ArrayList<AdapterID> adapters = new ArrayList<>();

    public void setDistillationType(DistillationType distillationType) {
        this.distillationType = distillationType;
    }

    public double getDistillationAmp() {
        return distillationAmp;
    }

    public void setDistillationAmp(double distillationAmp) {
        this.distillationAmp = distillationAmp;
    }

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

    private void semiInitialize(SuperLiquorStack liquorStack) {
        super.initialize(liquorStack);
        this.xEffectType = liquorStack.getXEffectType();
        this.yEffectType = liquorStack.getYEffectType();
        this.zEffectType = liquorStack.getZEffectType();
        this.baseDuration = liquorStack.getBaseDuration();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.alcoholAmount = liquorStack.getAlcoholAmount();
        this.alcoholPercentage = liquorStack.getAlcoholPercentage();
        this.amplifierLine = liquorStack.getAmplifierLine();
        this.producer = liquorStack.getProducer();
        this.temp = liquorStack.getTemp();
        this.humid = liquorStack.getHumid();
        this.biomeId = liquorStack.getBiomeId();
        this.adapters.clear();
        this.adapters.addAll(liquorStack.getAdapters());
    }

    public void initialize(SuperLiquorStack liquorStack) {
        this.semiInitialize(liquorStack);
        ItemMeta meta = liquorStack.getItemMeta();
        this.displayName = meta.displayName();
    }





    @Override
    public SuperLiquorStack getSuperItem() {
        SuperLiquorStack item = new SuperLiquorStack(super.getSuperItem());
        double newX = item.getX();
        double newY = item.getY();
        double newZ = item.getZ();
        double newTaste = item.getTaste();
        double newSmell = item.getSmell();
        double newCompatibility = item.getCompatibility();
        double newAmount = this.alcoholAmount;
        double newAlcPercent = this.alcoholPercentage;
        if (!adapters.isEmpty()) {
            for (AdapterID adapterID : adapters) {
                LiquorAdapter liquorAdaptorClass = adapterID.getAdapterClass();
                newX += liquorAdaptorClass.getxAddition();
                newX *= liquorAdaptorClass.getxAmplifier();
                newY += liquorAdaptorClass.getyAddition();
                newY *= liquorAdaptorClass.getyAmplifier();
                newZ += liquorAdaptorClass.getzAddition();
                newZ *= liquorAdaptorClass.getzAmplifier();
                newTaste += liquorAdaptorClass.getTasteAddition();
                newTaste *= liquorAdaptorClass.getTasteAmplifier();
                newSmell += liquorAdaptorClass.getSmellAddition();
                newSmell *= liquorAdaptorClass.getSmellAmplifier();
                newCompatibility += liquorAdaptorClass.getCompatibilityAddition();
                newCompatibility *= liquorAdaptorClass.getCompatibilityAmplifier();
                newAmount += liquorAdaptorClass.getAmountAddition();
                newAmount *= liquorAdaptorClass.getAmountAmplifier();
                newAlcPercent += liquorAdaptorClass.getAlcPercentAddition();
                newAlcPercent *= liquorAdaptorClass.getAlcPercentAmplifier();
            }
        }
        newX *= newCompatibility;
        newY *= newCompatibility;
        newZ *= newCompatibility;


        this.xAmplifier = (int) Math.floor(newX * calcAmplifierAmp(newTaste));
        this.yAmplifier = (int) Math.floor(newY * calcAmplifierAmp(newTaste));
        this.zAmplifier = (int) Math.floor(newZ * calcAmplifierAmp(newTaste));
        this.xDuration = (int) Math.floor(newX * baseDuration * calcDurationAmp(newSmell) * newAmount);
        this.yDuration = (int) Math.floor(newY * baseDuration * calcDurationAmp(newSmell) * newAmount);
        this.zDuration = (int) Math.floor(newZ * baseDuration * calcDurationAmp(newSmell) * newAmount);
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
        lore.setParams(item.getX(), item.getY(), item.getZ());
        lore.setSubParams(item.getTaste(), item.getSmell(), super.getCompatibility(), super.getQuality());
        lore.setAlcParams(newAlcPercent, newAmount);
        lore.setRarity(SuperItem.getRarity(newX, newY, newZ));
        lore.addInfo(this.infoLore);
        if (maturationDays > 0) {
            lore.setMaturationDays((int) this.maturationDays);
        }
        lore.addInfo("made by " + this.producer);
        if (!adapters.isEmpty()) {
            for (AdapterID adapter : adapters) {
                lore.addAdapterID(adapter);
            }
        }
        item.setPotionColor(this.potionColor);
        item.setX(this.getX());
        item.setY(this.getY());
        item.setZ(this.getZ());
        item.setTaste(this.getTaste());
        item.setSmell(this.getSmell());
        item.setCompatibility(this.getCompatibility());
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
        if (!this.adapters.isEmpty()) {
            item.setAdapters(this.adapters);
        }
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

    private double calcDurationAmp(double smell) {
        return smell + baseDurationAmp;
    }

    private double calcAmplifierAmp(double taste) {
        return taste / this.amplifierLine;
    }

    @Override
    public SuperLiquorStack getDebugItem(int... args) {
        this.copyVariable = false;
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
            SuperLiquorStack liquor = new SuperLiquorStack(matrix[0]);
            this.semiInitialize(liquor);
            this.copyVariable = true;
        } else if (Objects.equals(id, "distribution_receiver")) {
            // 分配(子)の場合
            SuperLiquorStack liquor = new SuperLiquorStack(matrix[0]);
            liquor.setAlcoholAmount(this.alcoholAmount);
            this.semiInitialize(liquor);
            this.copyVariable = true;
            this.calcDistribution(matrix);
        } else {
            super.setMatrix(matrix, id);
            this.copyVariable = false;
        }
    }

    private void calcDistribution(SuperItemStack[] matrix) {
        double xbuff = 1;
        double ybuff = 1;
        double zbuff = 1;
        double tasteBuff = 1;
        double smellBuff = 1;
        double compatibilityBuff = 1;
        for (SuperItemStack stack : matrix) {
            if (stack == null) continue;
            SuperItemData data = stack.getSuperItemData();
            for (SuperItemType type : this.distillationIngredients) {
                if (data.isSimilar(new SuperItemData(type))) {
                    DistributionBuffType buffType = this.distributionIngredients.get(type);
                    SuperXYZStack xyzStack = new SuperXYZStack(stack);
                    if (buffType == DistributionBuffType.ALL) {
                        xbuff += xyzStack.getX() * buffType.buff;
                        ybuff += xyzStack.getY() * buffType.buff;
                        zbuff += xyzStack.getZ() * buffType.buff;
                        tasteBuff += xyzStack.getTaste() * buffType.buff;
                        smellBuff += xyzStack.getSmell() * buffType.buff;
                        compatibilityBuff += xyzStack.getCompatibility();
                    } else if (buffType == DistributionBuffType.X) {
                        xbuff += xyzStack.getX() * buffType.buff;
                    } else if (buffType == DistributionBuffType.Y) {
                        ybuff += xyzStack.getY() * buffType.buff;
                    } else if (buffType == DistributionBuffType.Z) {
                        zbuff += xyzStack.getZ() * buffType.buff;
                    } else if (buffType == DistributionBuffType.TASTE) {
                        tasteBuff += xyzStack.getTaste() * buffType.buff;
                    } else if (buffType == DistributionBuffType.SMELL) {
                        smellBuff += xyzStack.getSmell() * buffType.buff;
                    } else if (buffType == DistributionBuffType.COMPATIBILITY) {
                        compatibilityBuff += xyzStack.getCompatibility() * buffType.buff;
                    } else if (buffType == DistributionBuffType.SUB) {
                        tasteBuff += xyzStack.getTaste() * buffType.buff;
                        smellBuff += xyzStack.getSmell() * buffType.buff;
                        compatibilityBuff += xyzStack.getCompatibility() * buffType.buff;
                    }
                }
            }
        }
        super.setxBuff(super.getxBuff() * xbuff);
        super.setyBuff(super.getyBuff() * ybuff);
        super.setzBuff(super.getzBuff() * zbuff);
        super.setTasteBuff(super.getTasteBuff() * tasteBuff);
        super.setSmellBuff(super.getSmellBuff() * smellBuff);
        super.setCompatibility(super.getCompatibility() * compatibilityBuff);

    }

    private double getDistilledAlcPer(double alcPer, double sub) {
        return (1.6 * Math.exp(sub) * alcPer) / (1 + (1.6 * Math.exp(sub) - 1) * alcPer);
    }
    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {

        // TODO 要調整
        SuperLiquorStack liquor = new SuperLiquorStack(item);
        this.temp = liquor.getTemp();
        this.humid = liquor.getHumid();
        this.biomeId = liquor.getBiomeId();
        this.producer = liquor.getProducer();
        if (!item.getSuperItemData().isSimilar(this.getSuperItemData())) {
            this.distillationType = DistillationType.COPY;
        }

        if (distillationType == DistillationType.NORMAL) {
            this.semiInitialize(new SuperLiquorStack(item));
            double alcPer = this.getAlcoholPercentage();
            this.alcoholPercentage = getDistilledAlcPer(alcPer, temp / 2);
            double amountMod = (alcPer / this.alcoholPercentage);
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
            double compatibility = super.getCompatibility() * this.distillationAmp;
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
            this.copyVariable = false;
        } else if (distillationType == DistillationType.COPY) {

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
            this.adapters.clear();
            this.adapters.addAll(liquor.getAdapters());
            this.copyVariable = true;
        }
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        // TODO 要調整
        this.copyVariable = false;
        if (maturationType == MaturationType.NORMAL) {
            // defaultDaysの何倍の日数熟成したかでパラメータが変化
            // 10倍の日数ですべてのパラメータ1.5倍!
            // ただし、アルコール量も1/2 になる

            this.semiInitialize(new SuperLiquorStack(ingredients.get(0)));
            Duration dur = Duration.between(start, end);
            double days = dur.toDays();
            this.maturationDays = days;
            this.alcoholAmount *= calcAmountMod(days);
            this.alcoholPercentage *= calcAlcPerMod(days);
            super.setTasteBuff(super.getTasteBuff() * calcTasteMod(days));
            super.setSmellBuff(super.getSmellBuff() * calcSmellMod(days));
        } else if (maturationType == MaturationType.DIVLINE) {
            this.semiInitialize(new SuperLiquorStack(ingredients.get(0)));
            double days = Duration.between(start, end).toDays();
            double buff = Math.pow(days / defaultMaturationDays, 0.3 - 0.1 * Math.cos(2 * Math.PI * humid));
            super.setTasteBuff(super.getTasteBuff() * buff);
            super.setSmellBuff(super.getSmellBuff() * buff);
        } else if (maturationType == MaturationType.COPY_AND_DIVLINE) {
            SuperLiquorStack liquor = new SuperLiquorStack(ingredients.get(0));
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
            this.adapters.clear();
            this.adapters.addAll(liquor.getAdapters());
            this.copyVariable = true;
            double days = Duration.between(start, end).toDays();
            double buff = Math.pow(days / defaultMaturationDays, 0.3 - 0.1 * Math.cos(2 * Math.PI * humid));
            super.setTasteBuff(super.getTasteBuff() * buff);
            super.setSmellBuff(super.getSmellBuff() * buff);
        }

    }

    public double getMaturationDays() {
        return maturationDays;
    }

    @Override
    public double getAmount() {
        double newAmount = this.alcoholAmount;
        for (AdapterID id : this.adapters) {
            LiquorAdapter adapter = id.getAdapterClass();
            newAmount += adapter.getAmountAddition();
            newAmount *= adapter.getAmountAmplifier();
        }
        return newAmount;
    }

    @Override
    public void setAmount(SuperItemStack stack) {
        initialize(new SuperLiquorStack(stack));
    }

    public void setAlcoholAmount(double alcoholAmount) {
        this.alcoholAmount = alcoholAmount;
        this.useAmount = alcoholAmount;
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

    public void setUseAmount(double useAmount) {
        this.useAmount = useAmount;
    }

    public void setMaturationType(MaturationType type) {
        this.maturationType = type;
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

    public void addMaturationIngredient(SuperItemType type) {
        this.maturationIngredients.add(type);
    }

    public void addDistillationIngredient(SuperItemType type) {
        this.distillationIngredients.add(type);
    }

    public double getAlcoholPercentage() {
        double newPer = this.alcoholPercentage;
        for (AdapterID id : this.adapters) {
            LiquorAdapter adapter = id.getAdapterClass();
            newPer += adapter.getAlcPercentAddition();
            newPer *= adapter.getAlcPercentAmplifier();
        }
        return newPer;
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

    public double getBaseDurationAmp() {
        return baseDurationAmp;
    }

    public void setBaseDurationAmp(double baseDurationAmp) {
        this.baseDurationAmp = baseDurationAmp;
    }

    public void addDistributionIngredient(SuperItemType type, DistributionBuffType buffType) {
        this.distributionIngredients.put(type, buffType);
    }

    public void setxEffectType(PotionEffectType xEffectType) {
        this.xEffectType = xEffectType;
    }

    public void setyEffectType(PotionEffectType yEffectType) {
        this.yEffectType = yEffectType;
    }

    public void setzEffectType(PotionEffectType zEffectType) {
        this.zEffectType = zEffectType;
    }

    public void addAdapter(AdapterID adapter) {
        this.adapters.add(adapter);
    }


    public enum DistributionBuffType {
        ALL(0.01),
        X(0.03),
        Y(0.03),
        Z(0.03),
        SUB(0.02),
        TASTE(0.03),
        SMELL(0.03),
        COMPATIBILITY(0.03);
        final double buff;

        DistributionBuffType(double buff) {
            this.buff = buff;
        }
    }

    public enum DistillationType {
        NORMAL,
        COPY;
    }

    public enum MaturationType {
        NORMAL,
        DIVLINE,
        COPY_AND_DIVLINE;

    }

}

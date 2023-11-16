package net.okuri.qol.superItems.factory;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.calcuration.StatisticalCalcuration;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperXYZStack;

import java.util.ArrayList;

public abstract class CraftableXYZItem extends SuperItem implements SuperCraftable {

    private final ArrayList<SuperItemType> mainIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> subIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> mainBuffIngredients = new ArrayList<>();
    private final ArrayList<SuperItemType> subBuffIngredients = new ArrayList<>();
    private double x;
    private double y;
    private double z;
    private boolean hasTSC;
    private double smell;
    private double taste;
    private double compatibility;
    private double quality = 1.0;
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
    private Component displayName;


    public CraftableXYZItem(SuperItemType superItemType, boolean hasTSC) {
        super(superItemType);
        this.hasTSC = hasTSC;
    }

    @Override
    public SuperXYZStack getSuperItem() {
        SuperXYZStack stack = new SuperXYZStack(super.getSuperItemType(), super.getCount(), this.hasTSC);
        stack.setDisplayName(this.displayName);
        double newX = this.x * this.xBuff * this.allBuff * this.quality;
        double newY = this.y * this.yBuff * this.allBuff * this.quality;
        double newZ = this.z * this.zBuff * this.allBuff * this.quality;
        stack.setX(newX);
        stack.setY(newY);
        stack.setZ(newZ);
        stack.setQuality(this.quality);
        stack.setHasTSC(this.hasTSC);
        LoreGenerator lore = new LoreGenerator();
        if (this.hasTSC) {
            double newSmell = this.smell * this.smellBuff * this.subAllBuff;
            double newTaste = this.taste * this.tasteBuff * this.subAllBuff;
            double newCompatibility = this.compatibility * this.compatibilityBuff * this.subAllBuff;
            stack.setSmell(newSmell);
            stack.setTaste(newTaste);
            stack.setCompatibility(newCompatibility);
            lore.setIngredientLore(newX, newY, newZ, newSmell, newTaste, newCompatibility, this.quality, stack.getQOLRarity());
        } else {
            lore.setIngredientLore(newX, newY, newZ, this.quality, stack.getQOLRarity());
        }
        stack.setLore(lore);
        return stack;
    }

    @Override
    public SuperXYZStack getDebugItem(int... args) {
        return getSuperItem();
    }


    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        // SuperCraftの場合
        ArrayList<SuperXYZStack> ingredients = new ArrayList<>();
        ArrayList<SuperXYZStack> buffIngredients = new ArrayList<>();
        ArrayList<SuperXYZStack> subIngredients = new ArrayList<>();
        ArrayList<SuperXYZStack> subBuffIngredients = new ArrayList<>();
        for (SuperItemStack item : matrix) {
            if (item == null) continue;
            SuperItemType type = item.getSuperItemType();
            if (this.mainIngredients.contains(type)) {
                ingredients.add(new SuperXYZStack(item));
            } else if (this.mainBuffIngredients.contains(type)) {
                buffIngredients.add(new SuperXYZStack(item));
            } else if (this.subIngredients.contains(type)) {
                subIngredients.add(new SuperXYZStack(item));
            } else if (this.subBuffIngredients.contains(type)) {
                subBuffIngredients.add(new SuperXYZStack(item));
            }
        }
        calc(ingredients, buffIngredients, subIngredients, subBuffIngredients);
    }


    protected void initialize(SuperXYZStack stack) {
        this.x = stack.getX();
        this.y = stack.getY();
        this.z = stack.getZ();
        this.hasTSC = stack.isHasTSC();
        if (hasTSC) {
            this.smell = stack.getSmell();
            this.taste = stack.getTaste();
            this.compatibility = stack.getCompatibility();
        }
        this.quality = stack.getQuality();
    }


    protected void calc(ArrayList<SuperXYZStack> ingredients, ArrayList<SuperXYZStack> buffIngredients, ArrayList<SuperXYZStack> subIngredients, ArrayList<SuperXYZStack> subBuffIngredients) {
        // ここから各種パラメータの計算
        // 1. x,y,zの計算
        assert !ingredients.isEmpty();

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
        if (!buffIngredients.isEmpty()) {
            double buff = 1;
            for (SuperXYZStack item : buffIngredients) {
                buff = buff * (1 + (this.allBuffAmp * (item.getX() + item.getY() + item.getZ()) / 3));
                this.xBuff *= (1 + item.getX() * this.xBuffAmp);
                this.yBuff *= (1 + item.getY() * this.yBuffAmp);
                this.zBuff *= (1 + item.getZ() * this.zBuffAmp);
            }
            this.allBuff *= buff;
        }
        // 3. subIngredientsの計算。taste, smell, compatibilityを計算する。
        // ない場合は現在のx,y,zから計算する。
        double subX = 0;
        double subY = 0;
        double subZ = 0;
        if (subIngredients.isEmpty()) {
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
        double standardDeviation = sc.getStandardDeviation();
        double max = sc.getMax();
        double min = sc.getMin();
        double mean = sc.getMean();
        this.smell = 1.1 - standardDeviation * 3;
        this.taste = (0.1 + max - mean) / (1 - max);
        if (this.compatibilityMax == this.compatibilityMin) {
            this.compatibility = this.compatibilityMax;
        } else {
            this.compatibility = (((max - min) % min) / min) * (this.compatibilityMax - this.compatibilityMin) + this.compatibilityMin;
        }
        // 4. subBuffIngredientsの計算。taste, smell, compatibilityを計算する。
        if (!subBuffIngredients.isEmpty()) {
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
            standardDeviation = sc.getStandardDeviation();
            max = sc.getMax();
            min = sc.getMin();
            mean = sc.getMean();
            this.subAllBuff *= subBuff;
            this.smellBuff *= ((1.1 - standardDeviation * 3) * this.smellBuffAmp + 1.0);
            this.tasteBuff *= (((0.1 + max - mean) / (1 - max)) * this.tasteBuffAmp + 1.0);
            this.compatibilityBuff *= ((((max - min) % min) / min) * (this.compatibilityMax - this.compatibilityMin) + this.compatibilityMin) * this.compatibilityBuffAmp + 1.0;
        }
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

    public double getXBuffAmp() {
        return xBuffAmp;
    }

    public void setXBuffAmp(double xBuffAmp) {
        this.xBuffAmp = xBuffAmp;
    }

    public double getYBuffAmp() {
        return yBuffAmp;
    }

    public void setYBuffAmp(double yBuffAmp) {
        this.yBuffAmp = yBuffAmp;
    }

    public double getZBuffAmp() {
        return zBuffAmp;
    }

    public void setZBuffAmp(double zBuffAmp) {
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


    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public void setParameter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getSmell() {
        return smell;
    }

    public void setSmell(double smell) {
        this.smell = smell;
    }

    public double getTaste() {
        return taste;
    }

    public void setTaste(double taste) {
        this.taste = taste;
    }

    public double getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(double compatibility) {
        this.compatibility = compatibility;
    }

    public double getAllBuff() {
        return allBuff;
    }

    public void setAllBuff(double allBuff) {
        this.allBuff = allBuff;
    }

    public double getxBuff() {
        return xBuff;
    }

    public void setxBuff(double xBuff) {
        this.xBuff = xBuff;
    }

    public double getxBuffAmp() {
        return xBuffAmp;
    }

    public void setxBuffAmp(double xBuffAmp) {
        this.xBuffAmp = xBuffAmp;
    }

    public double getyBuff() {
        return yBuff;
    }

    public void setyBuff(double yBuff) {
        this.yBuff = yBuff;
    }

    public double getyBuffAmp() {
        return yBuffAmp;
    }

    public void setyBuffAmp(double yBuffAmp) {
        this.yBuffAmp = yBuffAmp;
    }

    public double getzBuff() {
        return zBuff;
    }

    public void setzBuff(double zBuff) {
        this.zBuff = zBuff;
    }

    public double getzBuffAmp() {
        return zBuffAmp;
    }

    public void setzBuffAmp(double zBuffAmp) {
        this.zBuffAmp = zBuffAmp;
    }

    public double getSubAllBuff() {
        return subAllBuff;
    }

    public void setSubAllBuff(double subAllBuff) {
        this.subAllBuff = subAllBuff;
    }

    public double getSmellBuff() {
        return smellBuff;
    }

    public void setSmellBuff(double smellBuff) {
        this.smellBuff = smellBuff;
    }

    public double getTasteBuff() {
        return tasteBuff;
    }

    public void setTasteBuff(double tasteBuff) {
        this.tasteBuff = tasteBuff;
    }

    public double getCompatibilityBuff() {
        return compatibilityBuff;
    }

    public void setCompatibilityBuff(double compatibilityBuff) {
        this.compatibilityBuff = compatibilityBuff;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
    }

}

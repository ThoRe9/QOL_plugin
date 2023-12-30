package net.okuri.qol.superItems.factory.adapter;

import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;

public abstract class LiquorAdapter extends Adapter {
    private int customID;
    private String info;
    private double xAddition = 0;
    private double xAmplifier = 1;
    private double yAddition = 0;
    private double yAmplifier = 1;
    private double zAddition = 0;
    private double zAmplifier = 1;
    private double tasteAddition = 0;
    private double tasteAmplifier = 1;
    private double smellAddition = 0;
    private double smellAmplifier = 1;
    private double compatibilityAddition = 0;
    private double compatibilityAmplifier = 1;
    private double amountAddition = 0;
    private double amountAmplifier = 1;
    private double alcPercentAddition = 0;
    private double alcPercentAmplifier = 1;

    public LiquorAdapter(SuperItemType superItemType, AdapterID adapterID) {
        this(superItemType, adapterID, 0);
    }

    public LiquorAdapter(SuperItemType superItemType, AdapterID adapterID, int customID) {
        super(superItemType, adapterID);
        this.customID = customID;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
        LoreGenerator loreGenerator = new LoreGenerator();
        if (this.info != null) {
            loreGenerator.addInfo(this.info);
        }
        if (this.xAddition != 0) {
            loreGenerator.setxAddition(this.xAddition);
        }
        if (this.xAmplifier != 1) {
            loreGenerator.setxAmplifier(this.xAmplifier);
        }
        if (this.yAddition != 0) {
            loreGenerator.setyAddition(this.yAddition);
        }
        if (this.yAmplifier != 1) {
            loreGenerator.setyAmplifier(this.yAmplifier);
        }
        if (this.zAddition != 0) {
            loreGenerator.setzAddition(this.zAddition);
        }
        if (this.zAmplifier != 1) {
            loreGenerator.setzAmplifier(this.zAmplifier);
        }
        if (this.tasteAddition != 0) {
            loreGenerator.setTasteAddition(this.tasteAddition);
        }
        if (this.tasteAmplifier != 1) {
            loreGenerator.setTasteAmplifier(this.tasteAmplifier);
        }
        if (this.smellAddition != 0) {
            loreGenerator.setSmellAddition(this.smellAddition);
        }
        if (this.smellAmplifier != 1) {
            loreGenerator.setSmellAmplifier(this.smellAmplifier);
        }
        if (this.compatibilityAddition != 0) {
            loreGenerator.setCompatibilityAddition(this.compatibilityAddition);
        }
        if (this.compatibilityAmplifier != 1) {
            loreGenerator.setCompatibilityAmplifier(this.compatibilityAmplifier);
        }
        if (this.amountAddition != 0) {
            loreGenerator.setAmountAddition(this.amountAddition);
        }
        if (this.amountAmplifier != 1) {
            loreGenerator.setAmountAmplifier(this.amountAmplifier);
        }
        if (this.alcPercentAddition != 0) {
            loreGenerator.setAlcPercentAddition(this.alcPercentAddition);
        }
        if (this.alcPercentAmplifier != 1) {
            loreGenerator.setAlcPercentAmplifier(this.alcPercentAmplifier);
        }
        result.setLore(loreGenerator);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }

    public int getCustomID() {
        return customID;
    }

    public void setCustomID(int customID) {
        this.customID = customID;
    }

    public double getxAddition() {
        return xAddition;
    }

    public void setxAddition(double xAddition) {
        this.xAddition = xAddition;
    }

    public double getxAmplifier() {
        return xAmplifier;
    }

    public void setxAmplifier(double xAmplifier) {
        this.xAmplifier = xAmplifier;
    }

    public double getyAddition() {
        return yAddition;
    }

    public void setyAddition(double yAddition) {
        this.yAddition = yAddition;
    }

    public double getyAmplifier() {
        return yAmplifier;
    }

    public void setyAmplifier(double yAmplifier) {
        this.yAmplifier = yAmplifier;
    }

    public double getzAddition() {
        return zAddition;
    }

    public void setzAddition(double zAddition) {
        this.zAddition = zAddition;
    }

    public double getzAmplifier() {
        return zAmplifier;
    }

    public void setzAmplifier(double zAmplifier) {
        this.zAmplifier = zAmplifier;
    }

    public double getTasteAddition() {
        return tasteAddition;
    }

    public void setTasteAddition(double tasteAddition) {
        this.tasteAddition = tasteAddition;
    }

    public double getTasteAmplifier() {
        return tasteAmplifier;
    }

    public void setTasteAmplifier(double tasteAmplifier) {
        this.tasteAmplifier = tasteAmplifier;
    }

    public double getSmellAddition() {
        return smellAddition;
    }

    public void setSmellAddition(double smellAddition) {
        this.smellAddition = smellAddition;
    }

    public double getSmellAmplifier() {
        return smellAmplifier;
    }

    public void setSmellAmplifier(double smellAmplifier) {
        this.smellAmplifier = smellAmplifier;
    }

    public double getCompatibilityAddition() {
        return compatibilityAddition;
    }

    public void setCompatibilityAddition(double compatibilityAddition) {
        this.compatibilityAddition = compatibilityAddition;
    }

    public double getCompatibilityAmplifier() {
        return compatibilityAmplifier;
    }

    public void setCompatibilityAmplifier(double compatibilityAmplifier) {
        this.compatibilityAmplifier = compatibilityAmplifier;
    }

    public double getAmountAddition() {
        return amountAddition;
    }

    public void setAmountAddition(double amountAddition) {
        this.amountAddition = amountAddition;
    }

    public double getAmountAmplifier() {
        return amountAmplifier;
    }

    public void setAmountAmplifier(double amountAmplifier) {
        this.amountAmplifier = amountAmplifier;
    }

    public double getAlcPercentAddition() {
        return alcPercentAddition;
    }

    public void setAlcPercentAddition(double alcPercentAddition) {
        this.alcPercentAddition = alcPercentAddition;
    }

    public double getAlcPercentAmplifier() {
        return alcPercentAmplifier;
    }

    public void setAlcPercentAmplifier(double alcPercentAmplifier) {
        this.alcPercentAmplifier = alcPercentAmplifier;
    }
}

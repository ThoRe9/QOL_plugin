package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.alcohol.taste.Taste;

import java.util.HashMap;
import java.util.Map;

/**
 * Liquor で特定のTaste、Alcohol濃度を持つものに対し、ブランドとバフを付与できます。
 */
public class LiquorRecipe {
    /**
     * このレシピが持つ最低のTasteの濃度 (p/L)
     */
    private final Map<Taste, Double> minimumTastes = new HashMap<>();
    /**
     * このレシピが持つ最高のTasteの濃度 (p/L)
     */
    private final Map<Taste, Double> maximumTastes = new HashMap<>();
    private String id;
    // 以下基準を表すフィールド
    private Component name;
    private int priority;
    /**
     * このレシピが持つ最低のアルコール濃度 (alc / L)
     */
    private double minimumAlcohol = -1;
    /**
     * このレシピが持つ最高のアルコール濃度 (alc / L)
     */
    private double maximumAlcohol = -1;
    /**
     * このレシピが持つ最小の発酵度
     */
    private double minimumFermentation = -1;
    /**
     * このレシピが持つ最大の発酵度
     */
    private double maximumFermentation = -1;
    // 以下バフを表すフィールド
    private double durationAmp = 1.0;
    private double levelAmp = 1.0;

    public LiquorRecipe(String id, Component name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public boolean isMatch(Liquor liquor) {
        double liquorAmount = liquor.getAmount();
        if (minimumAlcohol > 0) {
            if (liquor.getAlcoholAmount() / liquorAmount < minimumAlcohol) {
                return false;
            }
        }
        if (maximumAlcohol > 0) {
            if (liquor.getAlcoholAmount() / liquorAmount > maximumAlcohol) {
                return false;
            }
        }
        if (minimumFermentation > 0) {
            if (liquor.getFermentationDegree() < minimumFermentation) {
                return false;
            }
        }
        if (maximumFermentation > 0) {
            if (liquor.getFermentationDegree() > maximumFermentation) {
                return false;
            }
        }
        for (Taste taste : minimumTastes.keySet()) {
            if (liquor.getTastes().get(taste) != null) {
                if (liquor.getTastes().get(taste) / liquorAmount < minimumTastes.get(taste)) {
                    return false;
                }
            } else {
                return false;

            }
        }
        for (Taste taste : maximumTastes.keySet()) {
            if (liquor.getTastes().get(taste) != null) {
                if (liquor.getTastes().get(taste) / liquorAmount > maximumTastes.get(taste)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public Component getName() {
        return name;
    }

    public void addMinimumTaste(Taste taste, double value) {
        minimumTastes.put(taste, value);
    }

    public void addMaximumTaste(Taste taste, double value) {
        maximumTastes.put(taste, value);
    }

    public void setMinimumAlcohol(double minimumAlcohol) {
        this.minimumAlcohol = minimumAlcohol;
    }

    public void setMaximumAlcohol(double maximumAlcohol) {
        this.maximumAlcohol = maximumAlcohol;
    }

    public void setMinimumFermentation(double minimumFermentation) {
        this.minimumFermentation = minimumFermentation;
    }

    public void setMaximumFermentation(double maximumFermentation) {
        this.maximumFermentation = maximumFermentation;
    }

    public double getDurationAmp() {
        return durationAmp;
    }

    public void setDurationAmp(double durationAmp) {
        this.durationAmp = durationAmp;
    }

    public double getLevelAmp() {
        return levelAmp;
    }

    public void setLevelAmp(double levelAmp) {
        this.levelAmp = levelAmp;
    }

    public int getPriority() {
        return priority;
    }
}

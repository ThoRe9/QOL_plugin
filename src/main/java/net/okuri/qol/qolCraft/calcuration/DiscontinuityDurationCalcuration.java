package net.okuri.qol.qolCraft.calcuration;

import java.util.ArrayList;

public class DiscontinuityDurationCalcuration extends PotionEffectCalcuration{

    // ポーション効果を対応する1つのパラメータと、divLineから計算します
    // maxDurationは最大の効果時間です。それぞれのパラメータはmaxDurationの倍率を表します。
    // divLineで割ったあまりが効果時間、商が効果の強さになります。
    // amplifierは効果の強さの倍率です。それぞれのパラメータ自体にかけられます。
    // durationAmplifierは効果時間の倍率です。それぞれの効果時間にかけられます。


    private final ArrayList<Double> variables = new ArrayList<>();
    private double divLine;
    private int maxDuration;
    private double amplifier = 1.0;
    private double durationAmplifier = 1.0;

    @Override
    public void setVariable(double... variables) {
        this.divLine = variables[0];
        this.maxDuration = (int) variables[1];
        // 3番目以降はvariablesに入れる
        // 3番目がない場合はエラーを出す
        if (variables.length < 3) {
            throw new IllegalArgumentException("Variables are not long enough.");
        }
        for (int i = 2; i < variables.length; i++) {
            this.variables.add(variables[i]);
        }
    }
    @Override
    public void calcuration() {
        // PotionEffectsがvariablesの数より少ない場合はエラーを出す
        if (this.potionEffects.size() < this.variables.size()) {
            throw new IllegalArgumentException("PotionEffects are not long enough.");
        }

        for (int i = 0; i < this.variables.size(); i++) {
            if (this.variables.get(i) < this.divLine) {
                this.potionEffects.get(i).withDuration((int)(this.maxDuration * ((this.variables.get(i) * this.amplifier) % this.divLine) * this.durationAmplifier));
                this.potionEffects.get(i).withAmplifier((int)(this.variables.get(i) * this.amplifier / this.divLine));
            }
        }
    }

    public void setAmplifier(double amplifier) {
        this.amplifier = amplifier;
    }
    public double getAmplifier() {
        return this.amplifier;
    }
    public void setDurationAmplifier(double durationAmplifier) {
        this.durationAmplifier = durationAmplifier;
    }
    public double getDurationAmplifier() {
        return this.durationAmplifier;
    }
    public double getVariable(int index) {
        return this.variables.get(index);
    }
    public int getEffectSize() {
        return this.potionEffects.size();
    }
}

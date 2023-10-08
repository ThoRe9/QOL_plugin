package net.okuri.qol.qolCraft.calcuration;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public abstract class PotionEffectCalcuration implements Calcuration {
    protected ArrayList<PotionEffect> potionEffects;

    public ArrayList<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public void setEffectType(int index, PotionEffectType effectType) {
        potionEffects.set(index, new PotionEffect(effectType, 0, 0));
    }

    @Override
    public ArrayList<Double> getAns() {
        ArrayList<Double> ans = new ArrayList<>();
        for (PotionEffect potionEffect : potionEffects) {
            ans.add((double) potionEffect.getDuration());
            ans.add((double) potionEffect.getAmplifier());
        }
        return ans;
    }


}

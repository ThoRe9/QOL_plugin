package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.loreGenerator.LiquorIngredientLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Liquor extends LiquorIngredient implements Distributable, DistributionReceiver {
    private ArrayList<PotionEffect> potionEffects = new ArrayList<>();

    public Liquor(String name) {
        super(SuperItemType.LIQUOR);
    }

    public Liquor() {
        super(SuperItemType.LIQUOR);
    }

    public Liquor(SuperItemType type) {
        super(type);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        stack.setDisplayName(Component.text("酒", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        setPotionEffects();
        for (PotionEffect effect : this.potionEffects) {
            meta.addCustomEffect(effect, true);
        }

        LiquorIngredientLore lore = new LiquorIngredientLore(super.getLiquorAmount(), super.getAlcoholAmount(), super.getDelicacy(), super.getFermentationDegree(), super.getEffectRate());
        for (Taste taste : super.getTastes().keySet()) {
            lore.addTaste(taste, super.getTastes().get(taste));
        }
        LoreGenerator gen = new LoreGenerator();
        gen.addLore(lore);
        gen.setLore(meta);
        stack.setItemMeta(meta);
        return stack;
    }

    private void setPotionEffects() {
        class TasteEffect {
            PotionEffectType type;
            double durationRate;
            double amplifierRate;
            double value;
        }
        double durationBuff = 1;
        double amplifierBuff = 1;
        ArrayList<TasteEffect> potionEffectTypes = new ArrayList<>();

        for (Map.Entry<Taste, Double> taste : this.getTastes().entrySet()) {
            if (taste.getKey().hasPotionInfo()) {
                TasteEffect effect = new TasteEffect();
                effect.type = taste.getKey().getEffectType();
                effect.durationRate = taste.getKey().getEffectDuration();
                effect.amplifierRate = taste.getKey().getEffectAmplifier();
                effect.value = taste.getValue();
                potionEffectTypes.add(effect);
            }
            if (taste.getKey().hasBuffInfo()) {
                durationBuff *= Math.pow(taste.getKey().getDurationAmplifier(), taste.getValue());
                amplifierBuff *= Math.pow(taste.getKey().getLevelAmplifier(), taste.getValue());
            }
        }

        for (TasteEffect effect : potionEffectTypes) {
            int duration = (int) (effect.durationRate * durationBuff * calcDelicacyBuff(effect.value * super.getEffectRate()));
            int amplifier = (int) (effect.amplifierRate * amplifierBuff * calcDelicacyBuff(effect.value * super.getEffectRate()));
            this.potionEffects.add(new PotionEffect(effect.type, duration, amplifier));
        }
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        if (Objects.equals(id, "distribution")) {
            // 分配(親)の場合
            SuperItemStack liquor = matrix[0];
            super.setMatrix(new SuperItemStack[]{liquor}, "initialize");
        } else if (Objects.equals(id, "distribution_receiver")) {
            // 分配(子)の場合
            SuperItemStack liquor = new SuperItemStack(matrix[0]);
            super.setRecentAmount(this.getAmount());
            super.setMatrix(new SuperItemStack[]{liquor}, "initialize");
        } else {
            super.setMatrix(matrix, id);
        }
    }

    private double calcDelicacyBuff(double param) {
        int count = getTastes().size();
        double delicacy = getDelicacy() / getAmount();
        double factor = 0.1 * Math.pow(delicacy, 2);
        return Math.pow(param, factor + 1) / (Math.pow(count * 0.5, factor));
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        Bukkit.getLogger().info("Liquor.isDistributable: " + this.getAmount());
        Bukkit.getLogger().info("Liquor.isDistributable: " + (smallBottleAmount * smallBottleCount));
        return this.getAmount() >= (smallBottleAmount * smallBottleCount);
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {
        super.setRecentAmount(this.getAmount() - (smallBottleAmount * smallBottleCounts));
        Bukkit.getLogger().info("Liquor.distribute: " + this.getAmount());
        Bukkit.getLogger().info("Liquor.distribute: " + super.getRecentAmount());
        this.adjustTasteValue();
    }

    @Override
    public void receive(int count) {
        this.setCount(count);
        this.adjustTasteValue();
    }

    @Override
    public double getAmount() {
        return super.getLiquorAmount();
    }

    @Override
    public void setAmount(SuperItemStack stack) {
        super.setMatrix(new SuperItemStack[]{stack}, "initialize");
    }
}

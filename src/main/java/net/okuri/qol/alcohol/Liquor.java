package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.loreGenerator.LiquorIngredientLore;
import net.okuri.qol.loreGenerator.LiquorLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Liquor extends LiquorIngredient implements Distributable, DistributionReceiver {
    private final ArrayList<PotionEffect> potionEffects = new ArrayList<>();
    private LiquorLore lore = new LiquorLore();
    private Component name = Component.text("酒", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
    private double drinkCost = 0;

    public Liquor() {
        super(SuperItemType.LIQUOR);
        super.setConsumable(true);
    }

    public Liquor(SuperItemType type) {
        super(type);
        super.setConsumable(true);
    }

    public Liquor(SuperItemStack liquor) {
        super(liquor);
    }

    @Override
    public SuperItemStack getSuperItem() {
        super.canCraft = true;
        SuperItemStack stack = super.getSuperItem();
        if (stack == null) {
            return null;
        }

        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        setPotionEffects();
        for (PotionEffect effect : this.potionEffects) {
            meta.addCustomEffect(effect, true);
        }
        this.calcDrinkCost();
        PDCC.set(meta, PDCKey.DRINK_COST, this.drinkCost);
        this.lore.setDrinkCost(this.drinkCost);
        LiquorIngredientLore lore = new LiquorIngredientLore(super.getLiquorAmount(), super.getAlcoholAmount(), super.getMaxAmount(), super.getDelicacy(), super.getFermentationDegree(), super.getEffectRate());
        for (Taste taste : super.getTastes().keySet()) {
            lore.addTaste(taste, super.getTastes().get(taste));
        }
        LoreGenerator gen = new LoreGenerator();
        gen.addLore(lore);
        gen.addLore(this.lore);
        gen.setLore(meta);
        meta.displayName(this.name);
        stack.setItemMeta(meta);
        return stack;

    }

    private void setPotionEffects() {
        class TasteEffect {
            Taste taste;
            PotionEffectType type;
            double durationRate;
            double amplifierRate;
            double value;
            double fermentationBuff;
        }
        this.lore = new LiquorLore();
        double durationBuff = 1;
        double amplifierBuff = 1;
        double delicacyBuff = 1;
        double effectRate = super.getEffectRate();
        boolean recipeFlag = false;
        ArrayList<TasteEffect> potionEffectTypes = new ArrayList<>();

        for (Map.Entry<Taste, Double> taste : this.getTastes().entrySet()) {
            if (taste.getKey().hasPotionInfo()) {
                TasteEffect effect = new TasteEffect();
                effect.taste = taste.getKey();
                effect.type = taste.getKey().getEffectType();
                effect.durationRate = taste.getKey().getEffectDuration();
                effect.amplifierRate = taste.getKey().getEffectAmplifier();
                effect.value = taste.getValue();
                effect.fermentationBuff = taste.getKey().getFermentationBuff(super.getFermentationDegree());
                potionEffectTypes.add(effect);
            }
            if (taste.getKey().hasBuffInfo()) {
                durationBuff *= Math.pow(taste.getKey().getDurationAmplifier(), taste.getValue());
                amplifierBuff *= Math.pow(taste.getKey().getLevelAmplifier(), taste.getValue());
            }
            if (taste.getKey().hasEffectRateAffection()) {
                effectRate = taste.getKey().getFixedEffectRate(effectRate);
            }
            if (taste.getKey().hasDelicacyBuff()) {
                delicacyBuff *= Math.pow(taste.getKey().getDelicacyBuff(), taste.getValue() / super.getLiquorAmount());
            }
        }
        for (LiquorRecipe recipe : LiquorRecipeController.instance.getRecipes()) {
            if (recipe.isMatch(this)) {
                durationBuff *= recipe.getDurationAmp();
                amplifierBuff *= recipe.getLevelAmp();
                this.lore.addRecipe(recipe);
                if (!recipeFlag) {
                    this.name = recipe.getName();
                    recipeFlag = true;
                } else {
                    this.name = this.name.append(Component.text(" ")).append(recipe.getName());
                }
            }
        }
        this.setDelicacy(delicacyBuff * super.getDelicacy());
        this.lore.setTotalDelicacyBuff(delicacyBuff);

        for (TasteEffect effect : potionEffectTypes) {
            double ampParam = calcDelicacyBuff(effect.value) * (1 - effectRate);
            double durParam = calcDelicacyBuff(effect.value) * effectRate;
            int duration = (int) (effect.durationRate * durationBuff * durParam * effect.fermentationBuff);
            int amplifier = (int) (effect.amplifierRate * amplifierBuff * ampParam * effect.fermentationBuff);
            this.potionEffects.add(new PotionEffect(effect.type, duration, amplifier));

            this.lore.addTasteEffect(effect.taste, ampParam, durParam, effect.fermentationBuff, calcDelicacyBuff(effect.value) / effect.value);
        }
        this.lore.setTotalAmpBuff(amplifierBuff);
        this.lore.setTotalDurBuff(durationBuff);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        if (Objects.equals(id, "distribution")) {
            // 分配(親)の場合
            SuperItemStack liquor = matrix[0];
            super.setMatrix(new SuperItemStack[]{liquor}, "initialize");
            this.name = Component.text().append(matrix[0].getItemMeta().displayName()).build();
            this.drinkCost = PDCC.get(liquor.getItemMeta(), PDCKey.DRINK_COST);
        } else if (Objects.equals(id, "distribution_receiver")) {
            // 分配(子)の場合
            SuperItemStack liquor = new SuperItemStack(matrix[0]);
            super.setRecentAmount(this.getAmount());
            super.setMatrix(new SuperItemStack[]{liquor}, "initialize");
            // displayNameの深いコピーを作成
            this.name = Component.text().append(liquor.displayName()).build();
            this.drinkCost = PDCC.get(liquor.getItemMeta(), PDCKey.DRINK_COST);
        } else if ("initialize".equals(id)) {
            super.setMatrix(matrix, id);
            this.name = Component.text().append(matrix[0].getItemMeta().displayName()).build();

        } else if ("add".equals(id)) {
            super.setMatrix(matrix, id);
        } else {
            super.setMatrix(matrix, id);
        }
    }

    private double calcDelicacyBuff(double param) {
        int count = getTastes().size();
        double delicacy = getDelicacy();
        double factor = 0.1 * Math.pow(delicacy, 2);
        return Math.pow(param, factor + 1) / (Math.pow(count * 0.5, factor));
    }

    private void calcDrinkCost() {
        this.drinkCost = Math.pow((this.getLiquorAmount() * 10), 2) * Math.pow((this.getAlcoholAmount() * 1000), 2) * this.getDelicacy();
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        return this.getAmount() >= (smallBottleAmount * smallBottleCount);
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {
        super.setRecentAmount(this.getAmount() - (smallBottleAmount * smallBottleCounts));
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

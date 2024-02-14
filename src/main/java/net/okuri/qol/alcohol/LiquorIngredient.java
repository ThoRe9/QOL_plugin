package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.alcohol.taste.TasteController;
import net.okuri.qol.loreGenerator.LiquorIngredientLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LiquorIngredient extends SuperItem implements SuperCraftable, Maturable, Distillable {
    private final Map<Taste, Double> tastes = new HashMap<>();
    /**
     * 含まれるアルコールの絶対量。1 = 1L
     */
    private double alcoholAmount;
    /**
     * 酒の量。1 = 1L
     */
    private double liquorAmount;
    /**
     * 新たに設定したい酒の量。これを設定してからadjustを呼ぶことで、酒の量を調整できる。
     */
    private double recentAmount;
    /**
     * 味の繊細さ。正味のパラメータに対する影響度
     */
    private double delicacy;
    /**
     * 発酵度。1日の発酵で1増加
     */
    private double fermentationDegree = 0;
    /**
     * 入れられた原料の数。3倍以上の場合は生成できない
     */
    private int ingredientCount = 0;
    /**
     * TasteのパラメータのDurationに振られる割合。 1 = 全てDurationに適用。 0 = 全てAmplifierに適用。
     */
    private double effectRate = 0.5;
    /**
     * 内部用フィールド。生成できるかできないかを判断する
     */
    private boolean canCraft = true;

    /**
     * クラフトで生成するLiquorIngredientを生成します。
     *
     * @param liquorAmount  生成する酒の量。1 = 1L
     * @param alcoholAmount 含まれるアルコールの絶対量。1 = 1L
     * @param tastes        ベースとなるアイテムの味
     * @param delicacy      原料の味の繊細さ
     */
    public LiquorIngredient(double liquorAmount, double alcoholAmount, Map<Taste, Double> tastes, double delicacy) {
        super(SuperItemType.LIQUOR_INGREDIENT);
        assert liquorAmount >= alcoholAmount && alcoholAmount > 0;
        this.liquorAmount = liquorAmount;
        this.alcoholAmount = alcoholAmount;
        this.delicacy = delicacy;
        this.tastes.putAll(tastes);
        this.tastes.put(TasteController.getController().getTaste("alcohol"), alcoholAmount);
        super.setConsumable(false);
    }

    public LiquorIngredient() {
        super(SuperItemType.LIQUOR_INGREDIENT);
        super.setConsumable(false);
    }

    public LiquorIngredient(SuperItemType type) {
        super(type);
        assert new SuperItemData(SuperItemTag.LIQUOR_INGREDIENT).isSimilar(new SuperItemData(type));
        super.setConsumable(false);
    }

    private void initialize(SuperItemStack stack) {
        assert stack.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.LIQUOR_INGREDIENT));
        ItemMeta meta = stack.getItemMeta();
        this.alcoholAmount = 0;
        this.liquorAmount = 0;
        this.tastes.clear();
        this.delicacy = 0;
        this.fermentationDegree = 0;
        this.ingredientCount = 0;
        super.setCount(stack.getAmount());
        this.effectRate = (double) PDCC.get(meta, PDCKey.LIQUOR_EFFECT_RATIO);
        this.add(stack);
    }

    private void add(SuperItemStack stack) {
        assert stack.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.LIQUOR_INGREDIENT));
        ItemMeta meta = stack.getItemMeta();
        this.alcoholAmount += (double) PDCC.get(meta, PDCKey.ALCOHOL_AMOUNT);
        this.liquorAmount += (double) PDCC.get(meta, PDCKey.LIQUOR_AMOUNT);
        Map<Taste, Double> tastes = PDCC.getTastes(meta);
        for (Map.Entry<Taste, Double> entry : tastes.entrySet()) {
            if (this.tastes.containsKey(entry.getKey())) {
                this.tastes.put(entry.getKey(), this.tastes.get(entry.getKey()) + entry.getValue());
            } else {
                this.tastes.put(entry.getKey(), entry.getValue());
            }
        }
        this.delicacy += (double) PDCC.get(meta, PDCKey.DELICACY);
        this.fermentationDegree += (double) PDCC.get(meta, PDCKey.FERMENTATION_DEGREE);
        this.ingredientCount += (int) PDCC.get(meta, PDCKey.INGREDIENT_COUNT);
        this.effectRate = this.effectRate * 0.5 + ((double) PDCC.get(meta, PDCKey.LIQUOR_EFFECT_RATIO)) * 0.5;
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.canCraft = true;
        boolean flag = false;
        double resourceDelicacy = -1;
        if ("initialize".equals(id)) {
            this.initialize(matrix[0]);
            return;
        }
        for (SuperItemStack item : matrix) {
            // ingredient ならば、それを足す
            if (item.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.LIQUOR_INGREDIENT)) && !flag) {
                this.initialize(item);
            } else if (item.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.LIQUOR_INGREDIENT))) {
                this.add(item);
            }

            // 原料の味を取得
            if (item.getSuperItemData().isSimilar(new SuperItemData(SuperItemTag.LIQUOR_RESOURCE))) {
                Map<Taste, Double> tastes = PDCC.getTastes(item.getItemMeta());
                for (Map.Entry<Taste, Double> entry : tastes.entrySet()) {
                    if (this.tastes.containsKey(entry.getKey())) {
                        this.tastes.put(entry.getKey(), this.tastes.get(entry.getKey()) + entry.getValue());
                    } else {
                        this.tastes.put(entry.getKey(), entry.getValue());
                    }
                }
                resourceDelicacy = (double) PDCC.get(item.getItemMeta(), PDCKey.DELICACY);
                this.ingredientCount++;
            }
            // 原料が水の場合、水を足す
            if (item.getSuperItemData().isSimilar(new SuperItemData(Material.WATER_BUCKET))) {
                this.liquorAmount += 1;
            }
        }
        if (this.ingredientCount / this.liquorAmount > 3) {
            this.canCraft = false;
        }
        if (resourceDelicacy != -1) {
            if (this.delicacy == 0) {
                this.delicacy = resourceDelicacy;
            } else {
                this.delicacy = (1.9 * resourceDelicacy * this.delicacy) / (resourceDelicacy + this.delicacy);
            }
        }
    }


    @Override
    public SuperItemStack getSuperItem() {
        if (!this.canCraft) {
            return null;
        }

        SuperItemStack stack = super.getSuperItem();
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        meta.setColor(Color.WHITE);
        meta.displayName(Component.text("Liquor Ingredient").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD));
        PDCC.setLiquorIngredient(meta, this);

        LiquorIngredientLore lore = new LiquorIngredientLore(this.liquorAmount, this.alcoholAmount, this.delicacy, this.fermentationDegree, this.effectRate);
        for (Map.Entry<Taste, Double> entry : this.tastes.entrySet()) {
            lore.addTaste(entry.getKey(), entry.getValue());
        }
        LoreGenerator l = new LoreGenerator();
        l.addLore(lore);
        l.setLore(meta);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        SuperItemStack ingredient = ingredients.get(0);
        double fermentationRate = (double) PDCC.get(ingredient.getItemMeta(), PDCKey.FERMENTATION_RATE);
        double alcoholRate = (double) PDCC.get(ingredient.getItemMeta(), PDCKey.FERMENTATION_ALC_RATE);
        double days = Duration.between(start, end).toDays();
        this.initialize(ingredient);
        double fermentation = fermentationRate * days;
        double alc = 0;
        for (Map.Entry<Taste, Double> entry : this.tastes.entrySet()) {
            double buff = Math.pow(1 - entry.getKey().getDegradability(), fermentation);
            double decrease = entry.getValue() * (1 - buff);
            this.tastes.put(entry.getKey(), entry.getValue() * buff);
            alc += decrease * alcoholRate;
        }
        this.alcoholAmount += alc;
        this.fermentationDegree += fermentation;
    }

    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
        this.initialize(item);
        this.alcoholAmount *= 0.9;
        this.liquorAmount *= 0.5;
        for (Map.Entry<Taste, Double> entry : this.tastes.entrySet()) {
            this.tastes.put(entry.getKey(), entry.getValue() * entry.getKey().getVolatility());
        }
    }


    public Map<Taste, Double> getTastes() {
        return tastes;
    }

    public double getAlcoholAmount() {
        return alcoholAmount;
    }

    public double getLiquorAmount() {
        return liquorAmount;
    }

    public double getDelicacy() {
        return delicacy;
    }

    public double getFermentationDegree() {
        return fermentationDegree;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public double getEffectRate() {
        return effectRate;
    }

    public double getRecentAmount() {
        return recentAmount;
    }

    public void setRecentAmount(double recentAmount) {
        this.recentAmount = recentAmount;
    }

    public void adjustTasteValue() {
        for (Map.Entry<Taste, Double> taste : this.getTastes().entrySet()) {
            taste.setValue(taste.getValue() * (this.recentAmount / this.liquorAmount));
        }
        this.liquorAmount = this.recentAmount;
    }






}

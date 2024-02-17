package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.okuri.qol.alcohol.LiquorRecipe;
import net.okuri.qol.alcohol.taste.Taste;

import java.util.ArrayList;

class TasteEffectInfo {
    Taste taste;
    double ampParam;
    double durParam;
    double fermentationBuff;
    double delicacyBuff;
}

public class LiquorLore extends Lore {

    private final ArrayList<TasteEffectInfo> effects = new ArrayList<>();
    private double totalDurBuff;
    private double totalAmpBuff;
    private double totalDelicacyBuff;
    private double drinkCost;
    private final ArrayList<LiquorRecipe> recipes = new ArrayList<>();

    public LiquorLore() {
        super(3, "Liquor", NamedTextColor.GOLD);
    }

    @Override
    public void generateLore() {
        addLore(title);
        for (TasteEffectInfo info : effects) {
            double[] lore = new double[]{info.ampParam, info.taste.getEffectAmplifier(), info.durParam, info.taste.getEffectDuration() / 1200, info.fermentationBuff, info.delicacyBuff};
            String[] units = new String[]{"p", "lv/p", "p", "min/p", "倍", "倍"};
            addDoubleArrayLore(info.taste.getDisplayName(), info.taste.getColor(), lore, units);
        }
        if (!recipes.isEmpty()) {
            addInfoLore("レシピ：");
            for (LiquorRecipe recipe : recipes) {
                double[] rl = new double[]{recipe.getLevelAmp(), recipe.getDurationAmp()};
                String[] units = new String[]{"倍", "倍"};
                addDoubleArrayLore(PlainTextComponentSerializer.plainText().serialize(recipe.getName()), recipe.getName().color(), rl, units);
            }
        }
        super.addAmpLore("レベルの増幅率", Lore.A_COLOR, totalAmpBuff);
        super.addAmpLore("持続時間の増幅率", Lore.B_COLOR, totalDurBuff);
        super.addAmpLore("くせの強さの増幅率", Lore.C_COLOR, totalDelicacyBuff);
        super.addParamLore("飲レベル", Lore.D_COLOR, drinkCost);
    }

    public void addTasteEffect(Taste taste, double ampParam, double durParam, double fermentationBuff, double delicacyBuff) {
        TasteEffectInfo info = new TasteEffectInfo();
        info.taste = taste;
        info.ampParam = ampParam;
        info.durParam = durParam;
        info.fermentationBuff = fermentationBuff;
        info.delicacyBuff = delicacyBuff;
        effects.add(info);
    }

    public void setTotalDurBuff(double totalDurBuff) {
        this.totalDurBuff = totalDurBuff;
    }

    public void setTotalAmpBuff(double totalAmpBuff) {
        this.totalAmpBuff = totalAmpBuff;
    }

    public void setTotalDelicacyBuff(double totalDelicacyBuff) {
        this.totalDelicacyBuff = totalDelicacyBuff;
    }

    public void addRecipe(LiquorRecipe recipe) {
        recipes.add(recipe);
    }

    public void setDrinkCost(double drinkCost) {
        this.drinkCost = drinkCost;
    }

}

package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class Shochu extends Sake implements Distillable{
    public Shochu(){
        super.type = SuperItemType.SHOCHU;
    }

    @Override
    public void setDistillationVariable(ItemStack item, double temp, double humid) {
        super.ingredient = item;
        super.initialize();
        super.alcPer = calcAlcPer(1-Math.abs(1-Math.abs(temp)));
        double amountMod = Math.pow(Math.abs(1.0-temp) * (1.0-humid), 0.5);
        super.maxAmount = 1800.0;
        super.amount = super.maxAmount * amountMod;
        // パラメータの増減量の設定：
        // 増 : + 他パラメータから引いた分 * 0.9 * (1+compatibility)
        // 減 : - そのパラメータ * 0.2 * (1+compatibility)
        // ingredientTypeがpolished_riceの場合はx, potatoなら y, barleyなら zを増やす。
        // 以下処理
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        if (ingredientType == SuperItemType.POLISHED_RICE){
            dy = - super.y * 0.2 * (1+super.compatibilty);
            dz = - super.z * 0.2 * (1+super.compatibilty);
            dx = - (dy + dz) * 0.9 * (1+super.compatibilty);
        } else if(ingredientType == SuperItemType.POTATO){
            dx = - super.x * 0.2 * (1+super.compatibilty);
            dz = - super.z * 0.2 * (1+super.compatibilty);
            dy = - (dx + dz) * 0.9 * (1+super.compatibilty);
        } else if(ingredientType == SuperItemType.BARLEY){
            dx = - super.x * 0.2 * (1+super.compatibilty);
            dy = - super.y * 0.2 * (1+super.compatibilty);
            dz = - (dx + dy) * 0.9 * (1+super.compatibilty);
        } else{
            throw new IllegalArgumentException("This item is not SakeIngredient");
        }
        super.x = super.x + dx;
        super.y = super.y + dy;
        super.z = super.z + dz;

        this.setting();
    }
    private double calcAlcPer(double var){
        return 0.20+ var * 0.10;
    }

    public ItemStack getSuperItem() {
        ItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta)result.getItemMeta();
        String superName = "";
        if (super.compatibilty >= 0.9) superName = "本格";
        meta.displayName(Component.text(superName + this.getIngredientName() + "焼酎"));
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Shochu!!");
        lore.addInfoLore("in a big bottle!!");
        lore.addInfoLore(this.sakeType.kanji + " " + this.tasteType.kanji + " " + this.alcType.name);
        lore.addInfoLore("Made from" + this.getIngredientName());
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Taste Richness", this.tasteRichness);
        lore.addParametersLore("Smell Richness", this.smellRichness);
        lore.addParametersLore("Compatibility", this.compatibilty);
        lore.addParametersLore("Alcohol Percentage", this.alcPer, true);
        lore.addParametersLore("Amount", this.amount, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }
    private String getIngredientName(){
        if (this.ingredientType == SuperItemType.POLISHED_RICE){
            return "米";
        } else if(this.ingredientType == SuperItemType.POTATO){
            return "芋";
        } else if(this.ingredientType == SuperItemType.BARLEY){
            return "麦";
        } else {
            return "不明";
        }
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        setDistillationVariable(new SakeIngredient().getDebugItem(args), 0.0, 0.0);
        return getSuperItem();
    }

    @Override
    protected void setting() {
        super.setting();
        // ingredientTypeがpolished_riceの場合はresistance
        // ingredientTypeがpotatoの場合はfire_resistance
        // ingredientTypeがbarleyの場合はregeneration
        // 以外の効果を消す。以下処理
        if (super.ingredientType == SuperItemType.POLISHED_RICE){
            super.fireResistAmp = 0;
            super.fireResistDuration = 0;
            super.regenAmp = 0;
            super.regenDuration = 0;
        } else if(super.ingredientType == SuperItemType.POTATO){
            super.registanceAmp = 0;
            super.registanceDuration = 0;
            super.regenAmp = 0;
            super.regenDuration = 0;
        } else if (super.ingredientType == SuperItemType.BARLEY) {
            super.registanceAmp = 0;
            super.registanceDuration = 0;
            super.fireResistAmp = 0;
            super.fireResistDuration = 0;
        } else{
            throw new IllegalArgumentException("This item is not SakeIngredient");
        }
    }
}

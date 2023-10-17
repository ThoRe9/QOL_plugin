package net.okuri.qol.superItems.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class HotSake extends SakeBottle implements Distillable, Distributable {
    public HotSake(){
        super.amount = 170.0;
        super.type = SuperItemType.HOT_SAKE;
    }
    @Override
    public void setDistillationVariable(ItemStack item, double temp, double humid) {
        super.initialize(item);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        double rawComp = PDCC.get(meta, PDCKey.COMPATIBILITY);
        double comp = 0.095 / rawComp;
        super.compatibility = comp;
        super.x = super.x * comp / rawComp;
        super.y = super.y * comp / rawComp;
        super.z = super.z * comp / rawComp;
        super.setting();
        this.rarity = SuperItem.getRarity(super.x, super.y, super.z);
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = super.getSuperItem();
        PotionMeta meta =(PotionMeta) result.getItemMeta();
        meta.displayName(Component.text("熱燗").color(NamedTextColor.RED));
        meta.setCustomModelData(super.type.getCustomModelData());

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Sake!!");
        lore.addInfoLore("Hot!! Spicy!! Tasty!!");
        lore.addInfoLore(this.sakeType.kanji + " " + this.tasteType.kanji + " " + this.alcType.name);
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Taste Richness", this.tasteRichness);
        lore.addParametersLore("Smell Richness", this.smellRichness);
        lore.addParametersLore("Compatibility", this.compatibility);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio, true);
        lore.addParametersLore("Maturation Days", this.days, true);
        lore.addParametersLore("Alcohol Percentage", this.alcPer, true);
        lore.addParametersLore("Amount", this.amount, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        this.setDistillationVariable(new SakeBottle().getDebugItem(args), 0.0, 0.0);
        return this.getSuperItem();
    }
}

package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

public class SakeBottle extends Sake implements DistributionReceiver, Distributable {

    public SakeBottle() {
        super.amount = 170.0;
        super.type = SuperItemType.SAKE_1GO;
    }

    @Override
    public void receive(int count) {
        super.count = count;
    }

    @Override
    public double getAmount() {
        return super.amount;
    }

    @Override
    public void setMatrix(ItemStack[] matrix, String id) {
        ItemStack bigBottle = matrix[0];
        super.initialize(bigBottle);
        if (Objects.equals(id, "sake_1go")){
            super.amount = 170.0;
        }
        super.setting();
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        ItemStack bigBottle = new Sake1ShoBottle().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 170.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.displayName(Component.text("徳利").color(NamedTextColor.GOLD));
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Sake!!");
        lore.addInfoLore("in a bottle!!");
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
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCount;
        return !(amount - decline < 0);
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCounts;
        super.amount = amount - decline;
        super.setting();
    }

    @Override
    public SuperItemType getType() {
        return this.type;
    }
}

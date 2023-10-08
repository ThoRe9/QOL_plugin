package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class ShochuOchoko extends Shochu implements DistributionReceiver {
    public ShochuOchoko() {
        super.amount = 35.0;
        super.type = SuperItemType.SHOCHU_OCHOKO;
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
        super.amount = 35.0;
        super.setting();
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        ItemStack bigBottle = new ShochuBottle().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 35.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        result.setAmount(super.count);
        meta.displayName(Component.text("お猪口(焼酎)").color(NamedTextColor.GOLD));
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Shochu!!");
        lore.addInfoLore("in a cup!!");
        lore.addInfoLore("Made from" + this.getIngredientName());
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Taste Richness", this.tasteRichness);
        lore.addParametersLore("Smell Richness", this.smellRichness);
        lore.addParametersLore("Compatibility", this.compatibility);
        lore.addParametersLore("Alcohol Percentage", this.alcPer, true);
        lore.addParametersLore("Amount", this.amount, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }
}

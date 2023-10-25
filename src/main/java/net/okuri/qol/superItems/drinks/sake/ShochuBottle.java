package net.okuri.qol.superItems.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.meta.PotionMeta;

public class ShochuBottle extends Shochu implements DistributionReceiver, Distributable {
    public ShochuBottle() {
        super(SuperItemType.SHOCHU_1GO);
        super.amount = 170.0;
    }

    public ShochuBottle(SuperItemStack stack) {
        super(SuperItemType.SHOCHU_1GO, stack);
        // superItemType で得られるclassがShochuBottleを継承していない場合はエラーを吐く

        initialize(stack);
    }

    public ShochuBottle(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがShochuBottleを継承していない場合はエラーを吐く
        if (!ShochuBottle.class.isAssignableFrom(SuperItemType.getSuperItemClass(superItemType).getClass())) {
            throw new IllegalArgumentException("superItemType must be ShochuBottle or its subclass");
        }
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
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
        result.setAmount(super.count);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.displayName(Component.text("徳利(焼酎)").color(NamedTextColor.GOLD));
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Shochu!!");
        lore.addInfoLore("in a bottle!!");
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

    @Override
    public SuperItemStack getDebugItem(int... args) {
        SuperItemStack bigBottle = new Shochu().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 170.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        SuperItemStack bigBottle = matrix[0];
        super.initialize(bigBottle);
        super.amount = 170.0;
        super.setting();
    }
}

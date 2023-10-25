package net.okuri.qol.superItems.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.meta.PotionMeta;

public class ShochuOchoko extends Shochu implements DistributionReceiver {
    public ShochuOchoko() {
        super(SuperItemType.SHOCHU_OCHOKO);
        super.amount = 35.0;
    }

    public ShochuOchoko(SuperItemStack stack) {
        super(SuperItemType.SHOCHU_OCHOKO, stack);
        // superItemType で得られるclassがShochuOchokoを継承していない場合はエラーを吐く

        initialize(stack);
    }

    public ShochuOchoko(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがShochuOchokoを継承していない場合はエラーを吐く
        if (!ShochuOchoko.class.isAssignableFrom(SuperItemType.getSuperItemClass(superItemType).getClass())) {
            throw new IllegalArgumentException("superItemType must be ShochuOchoko or its subclass");
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
    public void setMatrix(SuperItemStack[] matrix, String id) {
        SuperItemStack bigBottle = matrix[0];
        super.initialize(bigBottle);
        super.amount = 35.0;
        super.setting();
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        SuperItemStack bigBottle = new ShochuBottle().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 35.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
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

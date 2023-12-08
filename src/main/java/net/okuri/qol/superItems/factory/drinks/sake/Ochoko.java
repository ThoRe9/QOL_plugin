package net.okuri.qol.superItems.factory.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

public class Ochoko extends Sake implements DistributionReceiver {
    private Component display;

    public Ochoko() {
        super(SuperItemType.SAKE_OCHOKO);
        super.amount = 35.0;
    }

    public Ochoko(SuperItemStack stack) {
        super(SuperItemType.SAKE_INGREDIENT, stack);
        // superItemType で得られるclassがSakeを継承していない場合はエラーを吐く
        initialize(stack);
    }

    public Ochoko(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがOchokoを継承していない場合はエラーを吐く
        if (!Ochoko.class.isAssignableFrom(SuperItemType.getSuperItemClass(superItemType).getClass())) {
            throw new IllegalArgumentException("superItemType must be Ochoko or its subclass");
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
        if(Objects.equals(id, "hot_sake_ochoko")){
            display = Component.text("お猪口(熱燗)").color(NamedTextColor.RED);
        } else if(Objects.equals(id, "sake_ochoko")){
            display = Component.text("お猪口").color(NamedTextColor.GOLD);
        }
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        SuperItemStack bigBottle = new Sake1ShoBottle().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 35.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.displayName(display);
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("JAPANESE Sake!!");
        lore.addInfo("in a cup!!");
        lore.addInfo(this.sakeType.kanji + " " + this.tasteType.kanji + " " + this.alcType.name);
        lore.setParams(this.x, this.y, this.z);
        lore.setSubParams(this.tasteRichness, this.smellRichness, this.compatibility, this.quality);
        lore.setAlcParams(this.alcPer, this.amount);
        lore.setRarity(this.rarity);
        lore.setMaturationDays((int) this.days);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio);
        meta.lore(lore.generate());

        result.setItemMeta(meta);
        return result;
    }

}

package net.okuri.qol.superItems.factory.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionReceiver;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

public class SakeBottle extends Sake implements DistributionReceiver, Distributable {

    public SakeBottle() {
        super(SuperItemType.SAKE_1GO);
        super.amount = 170.0;
    }

    public SakeBottle(SuperItemType type, SuperItemStack stack) {
        super(type, stack);
        // superItemType で得られるclassがSakeBottleを継承していない場合はエラーを吐く
        initialize(stack);
    }

    public SakeBottle(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがSakeBotleを継承していない場合はエラーを吐く
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
        if (Objects.equals(id, "sake_1go")){
            super.amount = 170.0;
        }
        super.setting();
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        SuperItemStack bigBottle = new Sake1ShoBottle().getDebugItem(args);
        super.initialize(bigBottle);
        super.amount = 170.0;
        super.setting();
        return this.getSuperItem();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.displayName(Component.text("徳利").color(NamedTextColor.GOLD));
        PDCC.set(meta, PDCKey.CONSUMABLE, true);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("JAPANESE Sake!!");
        lore.addInfo("in a bottle!!");
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
    public void setAmount(SuperItemStack stack) {
        initialize(stack);
    }
}

package net.okuri.qol.superItems.factory.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class HotSake extends SakeBottle implements Distillable, Distributable {
    public HotSake(){
        super(SuperItemType.HOT_SAKE);
        super.amount = 170.0;
    }

    public HotSake(SuperItemStack stack) {
        super(SuperItemType.HOT_SAKE, stack);
        initialize(stack);
    }

    public HotSake(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがHotSakeを継承していない場合はエラーを吐く
        if (!HotSake.class.isAssignableFrom(SuperItemType.getSuperItemClass(superItemType).getClass())) {
            throw new IllegalArgumentException("superItemType must be Sake or its subclass");
        }
    }
    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
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
    public SuperItemStack getSuperItem() {
        SuperItemStack result = super.getSuperItem();
        PotionMeta meta =(PotionMeta) result.getItemMeta();
        meta.displayName(Component.text("熱燗").color(NamedTextColor.RED));

        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("JAPANESE Sake!!");
        lore.addInfo("Hot!! Spicy!! Tasty!!");
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
    public SuperItemStack getDebugItem(int... args) {
        this.setDistillationVariable(new SakeBottle().getDebugItem(args), 0.0, 0.0);
        return this.getSuperItem();
    }

    @Override
    public void setAmount(SuperItemStack stack) {
        initialize(stack);
    }
}

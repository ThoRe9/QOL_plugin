package net.okuri.qol.superItems.factory.drinks.sake;

import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sake1ShoBottle extends Sake implements Maturable, Distributable {
    public Sake1ShoBottle() {
        super(SuperItemType.SAKE_1SHO);
        this.maxAmount = 1800.0;
    }

    public Sake1ShoBottle(SuperItemStack stack) {
        super(SuperItemType.SAKE_1SHO, stack);
        // superItemType で得られるclassがSake1ShoBottleを継承していない場合はエラーを吐く
        initialize(stack);
    }

    public Sake1ShoBottle(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがSake1ShoBottleを継承していない場合はエラーを吐く
        if (!Sake1ShoBottle.class.isAssignableFrom(SuperItemType.getSuperItemClass(superItemType).getClass())) {
            throw new IllegalArgumentException("superItemType must be Sake1ShoBottle or its subclass");
        }
    }



    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        super.ingredient = ingredients.get(0);
        Duration dur = Duration.between(start, end);
        super.days = dur.toDays();
        super.temp = temp;
        super.humid = humid;
        initialize();
    }

    @Override
    public SuperItemStack getSuperItem() {

        SuperItemStack result = super.getSuperItem();
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.setColor(Color.WHITE);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("JAPANESE Sake!! BIG BOTTLE!!");
        lore.addInfo(super.sakeType.kanji + " " + super.tasteType.kanji + " " + super.alcType.name);
        lore.setParams(super.x, super.y, super.z);
        lore.setSubParams(super.tasteRichness, super.smellRichness, super.compatibility, super.quality);
        lore.setAlcParams(super.alcPer, super.amount);
        lore.setRarity(super.rarity);
        lore.setMaturationDays((int) super.days);
        lore.setRarity(super.rarity);
        lore.addParametersLore("Rice Polishing Ratio", super.ricePolishingRatio);
        meta.lore(lore.generate());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        super.ingredient = new SakeIngredient().getDebugItem(args);
        super.days = 1.0;
        super.temp = 0.0;
        super.humid = 0.0;
        super.initialize();
        return getSuperItem();
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        SuperItemStack item = matrix[0];
        initialize(item);
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCount;
        return !(amount - decline < 0);
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCount) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCount;
        super.amount = amount - decline;
        super.setting();
    }

    @Override
    public void setAmount(SuperItemStack stack) {
        initialize(stack);
    }

}

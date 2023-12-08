package net.okuri.qol.superItems.factory.ingredient;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.calcuration.StatisticalCalcuration;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class Koji extends SuperItem implements SuperCraftable {
    private ItemStack rice;
    private double riceX;
    private double riceY;
    private double riceZ;
    private double ricePolishingRatio;
    private double standerdDeviation;
    private double compatibility;
    private double max;
    private double min;
    private double mean;
    private double taste;
    private double smell;
    private double quality;
    private int rarity;
    private double temp;
    private double humid;

    public Koji() {
        super(SuperItemType.KOJI);
    }

    public Koji(SuperItemStack koji) {
        super(SuperItemType.KOJI, koji);
        this.smell = PDCC.get(koji.getItemMeta(), PDCKey.SMELL_RICHNESS);
        this.taste = PDCC.get(koji.getItemMeta(), PDCKey.TASTE_RICHNESS);
        this.compatibility = PDCC.get(koji.getItemMeta(), PDCKey.COMPATIBILITY);
        this.quality = PDCC.get(koji.getItemMeta(), PDCKey.QUALITY);
        this.rarity = PDCC.get(koji.getItemMeta(), PDCKey.RARITY);
        this.ricePolishingRatio = PDCC.get(koji.getItemMeta(), PDCKey.RICE_POLISHING_RATIO);
        this.temp = PDCC.get(koji.getItemMeta(), PDCKey.TEMP);
        this.humid = PDCC.get(koji.getItemMeta(), PDCKey.HUMID);
    }
    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.rice = matrix[1];
        setting();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType(), getAmount());
        PotionMeta meta = (PotionMeta) result.getItemMeta();


        PDCC.set(meta, PDCKey.SMELL_RICHNESS, this.smell);
        PDCC.set(meta, PDCKey.TASTE_RICHNESS, this.taste);
        PDCC.set(meta, PDCKey.COMPATIBILITY, this.compatibility);
        PDCC.set(meta, PDCKey.QUALITY, this.quality);
        PDCC.set(meta, PDCKey.RARITY, this.rarity);
        PDCC.set(meta, PDCKey.TEMP, this.temp);
        PDCC.set(meta, PDCKey.HUMID, this.humid);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);
        PDCC.set(meta, PDCKey.CONSUMABLE, false);

        meta.displayName(Component.text("Koji").color(net.kyori.adventure.text.format.NamedTextColor.GOLD));
        meta.setColor(Color.WHITE);

        LoreGenerator lg = new LoreGenerator();
        lg.addInfo("Used for making sake!");
        lg.setSubParams(this.taste, this.smell, this.compatibility, this.quality);
        lg.setRarity(this.rarity);
        lg.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio);
        meta.lore(lg.generate());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.rice = new PolishedRice().getDebugItem(args);
        setting();
        return this.getSuperItem();
    }

    private void setting(){
        this.riceX = PDCC.get(this.rice.getItemMeta(), PDCKey.X);
        this.riceY = PDCC.get(this.rice.getItemMeta(), PDCKey.Y);
        this.riceZ = PDCC.get(this.rice.getItemMeta(), PDCKey.Z);
        this.ricePolishingRatio = PDCC.get(this.rice.getItemMeta(), PDCKey.RICE_POLISHING_RATIO);
        this.temp = PDCC.get(this.rice.getItemMeta(), PDCKey.TEMP);
        this.humid = PDCC.get(this.rice.getItemMeta(), PDCKey.HUMID);
        this.quality = PDCC.get(this.rice.getItemMeta(), PDCKey.QUALITY);
        this.rarity = PDCC.get(this.rice.getItemMeta(), PDCKey.RARITY);

        StatisticalCalcuration sc = new StatisticalCalcuration();
        sc.setVariable(this.riceX, this.riceY, this.riceZ);
        sc.calcuration();
        this.standerdDeviation = sc.getStandardDeviation();
        this.max = sc.getMax();
        this.min = sc.getMin();
        this.mean = sc.getMean();
        this.smell = 1.1-this.standerdDeviation*3;
        this.taste = (0.1+max-mean)/(1-max);
        this.compatibility = ((this.max - this.min)%this.min)/this.min;

    }
    private int getAmount(){
        return (int) (this.ricePolishingRatio*10 -3);
    }
}

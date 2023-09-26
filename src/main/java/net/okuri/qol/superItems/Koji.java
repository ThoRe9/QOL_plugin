package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.calcuration.StatisticalCalcuration;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class Koji implements SuperCraftable {
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
    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.rice = matrix[1];
        setting();
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) result.getItemMeta();

        PDCC.setSuperItem(meta, SuperItemType.KOJI, this.smell, this.taste, this.compatibility, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);
        PDCC.set(meta, PDCKey.CONSUMABLE, false);

        meta.displayName(Component.text("麹").color(net.kyori.adventure.text.format.NamedTextColor.GOLD));
        meta.setColor(Color.WHITE);

        LoreGenerator lg = new LoreGenerator();
        lg.addInfoLore("Used for making sake!");
        lg.addParametersLore("Smell Richness", this.smell);
        lg.addParametersLore("Taste Richness", this.taste);
        lg.addParametersLore("Peculiarity", this.compatibility);
        lg.addParametersLore("Quality", this.quality);
        lg.addRarityLore(this.rarity);
        lg.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio);
        meta.lore(lg.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        ItemStack rice = new PolishedRice().getDebugItem(args);
        this.rice = rice;
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
}

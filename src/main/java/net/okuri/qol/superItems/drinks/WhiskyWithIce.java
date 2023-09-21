package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class WhiskyWithIce implements SuperCraftable {
    private ItemStack whisky = null;
    private SuperItemType superItemType = SuperItemType.WHISKY_WITH_ICE;
    private ItemStack[] matrix = null;
    private double x;
    private double y;
    private double z;
    private double divLine;
    private double quality;
    private double rarity;
    private double temp;
    private double humid;
    private int days;
    private double alcoholAmount = 30.0;
    private double alcoholPer = 0.40;

    @Override
    public ItemStack getSuperItem() {
        ItemStack WWI = new ItemStack(Material.POTION, 3);
        PotionMeta WWImeta = (PotionMeta) WWI.getItemMeta();
        PotionMeta Wmeta = (PotionMeta) this.whisky.getItemMeta();
        WWImeta.setCustomModelData(this.superItemType.getCustomModelData());

        PDCC.setLiquor(WWImeta, this.superItemType, this.alcoholAmount, this.alcoholPer, this.x, this.y, this.z, this.divLine, this.quality, this.rarity, this.temp, this.humid, this.days);

        WWImeta.displayName(Component.text("Whisky with Ice").color(NamedTextColor.GOLD));
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addInfoLore("Whisky on the rocks!");
        loreGenerator.addParametersLore("Alcohol: ", this.alcoholPer, true);
        loreGenerator.addParametersLore("Amount: ", this.alcoholAmount, true);
        WWImeta.lore(loreGenerator.generateLore());
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(0), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(1), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(2), true);
        WWImeta.setColor(Color.fromBGR(10, 70, 170));
        WWI.setItemMeta(WWImeta);

        return WWI;
    }
    @Override
    public ItemStack getDebugItem(int... args) {
        this.whisky = new Whisky().getDebugItem();
        return getSuperItem();
    }
    public WhiskyWithIce(ItemStack[] matrix) {
        setting(matrix[4]);
    }
    public WhiskyWithIce(ItemStack whisky) {
        setting(whisky);
    }

    public WhiskyWithIce() {
    }
    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        setting(matrix[4]);
    }

    private void setting(ItemStack whisky) {
        this.whisky = whisky;
        this.alcoholPer = PDCC.get(whisky, PDCKey.ALCOHOL_PERCENTAGE);
        ItemMeta meta = whisky.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.divLine = PDCC.get(meta, PDCKey.DIVLINE);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.rarity = PDCC.get(meta, PDCKey.RARITY);
        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);
        this.days = PDCC.get(meta, PDCKey.MATURATION);

    }
}

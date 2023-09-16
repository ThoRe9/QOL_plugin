package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;

import net.okuri.qol.drinks.maturation.Whisky;
import net.okuri.qol.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class WhiskyWithIce extends SuperCraftable {
    private ItemStack whisky = null;
    private SuperItemType superItemType = SuperItemType.WHISKY_WITH_ICE;
    private ItemStack[] matrix = null;
    private double alcoholAmount = 30.0;
    private double alcoholPer = 0.40;

    @Override
    public ItemStack getSuperItem() {
        ItemStack WWI = new ItemStack(Material.POTION, 3);
        PotionMeta WWImeta = (PotionMeta) WWI.getItemMeta();
        PotionMeta Wmeta = (PotionMeta) this.whisky.getItemMeta();
        WWImeta.setCustomModelData(this.superItemType.getCustomModelData());
        WWImeta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.toString());
        WWImeta.getPersistentDataContainer().set(Alcohol.alcKey, PersistentDataType.BOOLEAN, true);
        WWImeta.getPersistentDataContainer().set(Alcohol.alcPerKey, PersistentDataType.DOUBLE, this.alcoholPer);
        WWImeta.getPersistentDataContainer().set(Alcohol.alcAmountKey, PersistentDataType.DOUBLE, this.alcoholAmount);
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
        this.alcoholPer = whisky.getItemMeta().getPersistentDataContainer().get(Alcohol.alcPerKey, PersistentDataType.DOUBLE);
    }
}

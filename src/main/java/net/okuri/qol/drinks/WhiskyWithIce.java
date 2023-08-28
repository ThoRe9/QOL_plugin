package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;

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

    @Override
    public ItemStack getSuperItem() {
        ItemStack WWI = new ItemStack(Material.POTION, 3);
        PotionMeta WWImeta = (PotionMeta) WWI.getItemMeta();
        PotionMeta Wmeta = (PotionMeta) this.whisky.getItemMeta();
        WWImeta.setCustomModelData(this.superItemType.getCustomModelData());
        WWImeta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.toString());
        WWImeta.displayName(Component.text("Whisky with Ice").color(NamedTextColor.GOLD));
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addInfoLore("Whisky on the rocks!");
        WWImeta.lore(loreGenerator.generateLore());
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(0), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(1), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(2), true);
        WWImeta.setColor(Color.fromBGR(10, 70, 170));
        WWI.setItemMeta(WWImeta);

        return WWI;
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
    }
}

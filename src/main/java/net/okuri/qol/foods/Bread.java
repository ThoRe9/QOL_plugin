package net.okuri.qol.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Bread extends SuperCraftable {
    private ItemStack[] matrix = null;
    private ItemStack result = null;
    private SuperItemType superItemType = SuperItemType.BREAD;
    private double par = 0.0;
    private Component display = Component.text("Bread").color(NamedTextColor.DARK_GREEN);
    private NamespacedKey wheatkey = SuperWheat.ykey;
    public static NamespacedKey parkey = new NamespacedKey("qol", "bread_par");

    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        // settingにmatrixの0~2をわたす
        setting(new ItemStack[]{matrix[3], matrix[4], matrix[5]});
    }
    private void setting(ItemStack[] wheats) {
        // wheatのyパラメータの平均値を引き継ぐ。
        double sum = 0;
        for (ItemStack wheat : wheats) {
            sum += wheat.getItemMeta().getPersistentDataContainer().get(wheatkey, PersistentDataType.DOUBLE);
        }
        this.par = sum / wheats.length;
    }
    public Bread(){
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack bread = new ItemStack(Material.BREAD, 1);
        ItemMeta meta = bread.getItemMeta();
        meta.displayName(display);
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Bread!");
        lore.addParametersLore("Parameter", par*10);
        meta.lore(lore.generateLore());
        meta.getPersistentDataContainer().set(parkey, PersistentDataType.DOUBLE, this.par);
        meta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.getStringType());
        bread.setItemMeta(meta);
        return bread;
    }
}

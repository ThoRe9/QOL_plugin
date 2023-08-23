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
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Bread extends SuperCraftable {
    private ItemStack[] matrix = null;
    private ItemStack result = null;
    private SuperItemType superItemType = SuperItemType.BREAD;
    private double par = 0.0;
    private int foodLevel = 5;
    private float foodSaturation = 6.0f;
    private Sound sound = Sound.ENTITY_GENERIC_EAT;
    protected Component display ;
    protected NamespacedKey wheatkey ;
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
        this.foodLevel = (int) Math.floor(this.foodLevel * (1 + this.par));
        this.foodSaturation = (float) (this.foodSaturation * (1 + this.par));
    }
    public Bread(){
        this.display = Component.text("Bread").color(NamedTextColor.DARK_GREEN);
        this.wheatkey = SuperWheat.ykey;
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack bread = new ItemStack(Material.BREAD, 1);
        ItemMeta meta = bread.getItemMeta();
        meta.displayName(display);
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Bread!");
        lore.addFoodLevelLore(foodLevel);
        lore.addFoodSaturationLore(foodSaturation);
        lore.addParametersLore("Parameter", par*10);
        meta.lore(lore.generateLore());
        meta.getPersistentDataContainer().set(parkey, PersistentDataType.DOUBLE, this.par);
        meta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.getStringType());
        meta.getPersistentDataContainer().set(Food.foodLevelKey, PersistentDataType.INTEGER, this.foodLevel);
        meta.getPersistentDataContainer().set(Food.foodSaturationKey, PersistentDataType.FLOAT, this.foodSaturation);
        meta.getPersistentDataContainer().set(Food.FoodSoundKey, PersistentDataType.STRING, this.sound.toString());
        meta.getPersistentDataContainer().set(Food.eatableKey, PersistentDataType.BOOLEAN, true);
        bread.setItemMeta(meta);
        return bread;
    }
}

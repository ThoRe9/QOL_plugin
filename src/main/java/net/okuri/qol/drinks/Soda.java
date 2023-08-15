package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperCoal;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class Soda extends DrinkCraftable{
    public static final NamespacedKey strengthkey = new NamespacedKey("qol", "qol_strength");
    private ItemStack[] matrix = null;
    private ItemStack result = null;
    private double strength = 1.0;
    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        // settingにmatrixの0~2をわたす
        setting(new ItemStack[]{matrix[0], matrix[1], matrix[2]});
    }
    private void setting(ItemStack[] coals) {
        // coalのrarity * qualityの平均値をstrengthにする
        double sum = 0;
        for (ItemStack coal : coals) {
            sum += coal.getItemMeta().getPersistentDataContainer().get(SuperCoal.raritykey, PersistentDataType.DOUBLE) * coal.getItemMeta().getPersistentDataContainer().get(SuperCoal.qualitykey, PersistentDataType.DOUBLE);
        }
        this.strength = sum / coals.length;
    }
    public Soda(){
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack soda = new ItemStack(Material.POTION, 3);
        PotionMeta meta = (PotionMeta)soda.getItemMeta();
        // strengthをPersistentDataContainerに保存
        meta.getPersistentDataContainer().set(strengthkey, PersistentDataType.DOUBLE, (1-this.strength) * 100);
        // displayname, loreを設定
        meta.displayName(Component.text("Soda").color(NamedTextColor.AQUA));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Sparkling water!");
        lore.addParametersLore("Strength", this.strength);
        meta.lore(lore.generateLore());

        meta.setColor(Color.AQUA);

        soda.setItemMeta(meta);
        return soda;
    }
}

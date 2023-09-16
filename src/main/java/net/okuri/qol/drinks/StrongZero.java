package net.okuri.qol.drinks;

import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superCraft.SuperCraftable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrongZero extends SuperCraftable {
    private ItemStack[] matrix = null;
    private ItemStack rice = null;
    private ItemStack soda = null;
    public static NamespacedKey st0key = new NamespacedKey("qol", "strongzero");

    @Override
    public void setMatrix(ItemStack[] matrix){
        this.matrix = matrix;
        setting(matrix[4], matrix[7]);
    }

    private void setting(ItemStack rice, ItemStack soda){
        this.rice = rice;
        this.soda = soda;
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack strongZero = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta)strongZero.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000, 1), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1000, 1), true);
        meta.getPersistentDataContainer().set(st0key, PersistentDataType.STRING, "strongzero");
        meta.getPersistentDataContainer().set(Alcohol.alcPerKey,PersistentDataType.DOUBLE, 0.9);
        meta.getPersistentDataContainer().set(Alcohol.alcAmountKey,PersistentDataType.DOUBLE, 300.0);
        meta.setDisplayName("Strong Zero");
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("This is japanese culture!!!!!");
        lore.addImportantLore("SO STRONG");
        lore.addParametersLore("Alcohol", 0.9, true);
        lore.addParametersLore("Amount", 300.0, true);
        meta.lore(lore.generateLore());
        strongZero.setItemMeta(meta);

        return strongZero;
    }
    @Override
    public ItemStack getDebugItem(int... args){
        return getSuperItem();
    }
}

package net.okuri.qol.superItems.factory.drinks;

import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrongZero extends SuperItem implements SuperCraftable {
    private SuperItemStack[] matrix = null;
    private SuperItemStack rice = null;
    private SuperItemStack soda = null;
    public static NamespacedKey st0key = new NamespacedKey("qol", "strongzero");
    private final double alcPer = 0.09;
    private final double alcAmount = 300.0;

    public StrongZero() {
        super(Material.POTION);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.matrix = matrix;
        setting(matrix[4], matrix[7]);
    }

    private void setting(SuperItemStack rice, SuperItemStack soda) {
        this.rice = rice;
        this.soda = soda;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack strongZero = new SuperItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) strongZero.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000, 1), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1000, 1), true);
        meta.getPersistentDataContainer().set(st0key, PersistentDataType.STRING, "strongzero");
        PDCC.set(meta, PDCKey.ALCOHOL, true);
        PDCC.set(meta, PDCKey.ALCOHOL_LEVEL, alcPer);
        PDCC.set(meta, PDCKey.ALCOHOL_AMOUNT, alcAmount);
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
    public SuperItemStack getDebugItem(int... args) {
        return getSuperItem();
    }
}

package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class Soda implements SuperCraftable {
    private ItemStack[] matrix = null;
    private ItemStack result = null;
    private double strength = 1.0;
    private SuperItemType superItemType = SuperItemType.SODA;
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
            double r = PDCC.get(coal, PDCKey.X);
            double q = PDCC.get(coal, PDCKey.QUALITY);
            sum += (1+ r) * q;
        }
        this.strength = sum / coals.length;
    }
    public Soda(){
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack soda = new ItemStack(Material.POTION, 3);
        PotionMeta meta = (PotionMeta)soda.getItemMeta();
        // strength, SuperItemTypeをPersistentDataContainerに保存
        PDCC.set(meta,PDCKey.SODA_STRENGTH, strength);
        PDCC.set(meta,PDCKey.TYPE, superItemType.toString());
        // displayname, loreを設定
        meta.displayName(Component.text("Soda").color(NamedTextColor.AQUA));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Sparkling water!");
        lore.addParametersLore("Strength", (strength-1)*10);
        meta.lore(lore.generateLore());

        meta.setColor(Color.AQUA);

        soda.setItemMeta(meta);
        return soda;
    }
    @Override
    public ItemStack getDebugItem(int... args) {
        this.strength = 1.0;
        return getSuperItem();
    }
}

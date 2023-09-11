package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

public class SuperCoal extends SuperItem {
    private SuperItemType superItemType = SuperItemType.COAL;
    private ItemStack itemStack = new ItemStack(Material.COAL, 1);
    private int y;
    // qualityは、全体の効果時間への倍率。
    private double quality;
    // rarityは掘ったy座標に依存している。(マイクラの生成確立に反比例する)
    private double rarity;
    public static final NamespacedKey raritykey = new NamespacedKey("qol", "super_coal_rarity");
    public static final NamespacedKey qualitykey = new NamespacedKey("qol", "super_coal_quality");
    public SuperCoal(){
    }

    public SuperCoal(int y, double quality) {
        this.y = y;
        this.quality = quality;
    }
    public ItemStack getSuperItem() {
        this.calcRarity();
        // PersistentDataContainer にデータを保存
        NamespacedKey typekey = SuperItemType.typeKey;

        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(typekey, PersistentDataType.STRING, this.superItemType.getStringType());
        meta.getPersistentDataContainer().set(raritykey, PersistentDataType.DOUBLE, this.rarity);
        meta.getPersistentDataContainer().set(qualitykey, PersistentDataType.DOUBLE, this.quality);

        //名前を変更
        Component display;
        display = Component.text("Super Coal").color(NamedTextColor.DARK_GRAY);
        meta.displayName(display);

        //Loreを変更
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addParametersLore("Quality", this.quality);
        loreGenerator.addRarityLore((int)(this.rarity * 10));
        meta.lore(loreGenerator.generateLore());

        this.itemStack.setItemMeta(meta);
        return this.itemStack;

    }
    @Override
    public ItemStack getDebugItem(int[] args) {
        this.y = 90;
        this.quality = 1.0;
        return this.getSuperItem();
    }

    private void calcRarity(){
        this.rarity = (double) Math.abs(90 - this.y) / 270;
    }
}

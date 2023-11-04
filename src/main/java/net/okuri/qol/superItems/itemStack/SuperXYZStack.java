package net.okuri.qol.superItems.itemStack;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class SuperXYZStack extends SuperItemStack {
    // このクラスはSuperItemStackを継承したクラスです。
    // このクラスはtagにLIQUOR,RESOURCE, INGREDIENTを持つSuperItemStack全般を表します。
    private double x = 0;
    private double y = 0;
    private double z = 0;

    public SuperXYZStack(ItemStack stack) {
        // 浅いコピーではなく、深いコピーを行っている
        // そのため、このクラスをいじっても元のアイテム情報は変化しないことに注意
        super(stack);
        ItemMeta meta = super.getItemMeta();
        x = PDCC.get(meta, PDCKey.X);
        y = PDCC.get(meta, PDCKey.Y);
        z = PDCC.get(meta, PDCKey.Z);
    }

    public SuperXYZStack(SuperItemStack stack) {
        super(stack);
        ItemMeta meta = super.getItemMeta();
        x = PDCC.get(meta, PDCKey.X);
        y = PDCC.get(meta, PDCKey.Y);
        z = PDCC.get(meta, PDCKey.Z);
    }

    public SuperXYZStack(Material material) {
        this(material, 1);
    }

    public SuperXYZStack(Material material, int amount) {
        super(material, amount);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        super.setItemMeta(meta);
    }

    public SuperXYZStack(@NotNull SuperItemType type) {
        this(type, 1);
    }

    public SuperXYZStack(@NotNull SuperItemType type, int amount) {
        super(type, amount);
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        PDCC.set(meta, PDCKey.Y, y);
        PDCC.set(meta, PDCKey.Z, z);
        super.setItemMeta(meta);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.X, x);
        super.setItemMeta(meta);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Y, y);
        super.setItemMeta(meta);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        ItemMeta meta = super.getItemMeta();
        PDCC.set(meta, PDCKey.Z, z);
        super.setItemMeta(meta);
    }
}

package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class SuperResource implements SuperItem {
    // SuperResource の設定方法
    // 1. コンストラクタを設定する・
    // 2. superResourcesに追加する。

    // 以下設定必須項目
    Component name;
    String info;
    final Material material;
    final Material blockMaterial;
    SuperItemType superItemType;
    final int probabilityPercentage;

    // ここまで
    int Px;
    int Py;
    int Pz;
    double temp;
    double humid;
    int biomeId;
    double quality;
    double base = 0.0;
    double x;
    double y;
    double z;
    int rarity;

    public SuperResource(Component name, String info, Material material, Material blockMaterial, SuperItemType superItemType, int probabilityPercentage) {
        this.name = name;
        this.info = info;
        this.material = material;
        this.blockMaterial = blockMaterial;
        this.superItemType = superItemType;
        this.probabilityPercentage = probabilityPercentage;
    }

    public SuperResource(Material material, Material blockMaterial, SuperItemType superItemType, int probabilityPercentage) {
        this.name = Component.text("Super" + material.name());
        this.info = "Super" + material.name();
        this.material = material;
        this.blockMaterial = blockMaterial;
        this.superItemType = superItemType;
        this.probabilityPercentage = probabilityPercentage;
    }

    public void setResVariables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality) {
        this.Px = Px;
        this.Py = Py;
        this.Pz = Pz;
        this.temp = temp;
        this.humid = humid;
        this.biomeId = biomeId;
        this.quality = quality;

        CirculeDistribution cd = new CirculeDistribution();
        cd.setVariable(base + biomeId, biomeId, 1, 3);
        cd.calcuration();
        this.x = cd.getAns().get(0) * quality;
        this.y = cd.getAns().get(1) * quality;
        this.z = cd.getAns().get(2) * quality;

        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = new ItemStack(material);
        ItemMeta meta = result.getItemMeta();
        meta.displayName(name);
        meta.setCustomModelData(superItemType.getCustomModelData());

        PDCC.setSuperItem(meta, this);
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore(info);
        lore.addParametersLore("x", x);
        lore.addParametersLore("y", y);
        lore.addParametersLore("z", z);
        lore.addParametersLore("quality", quality);
        lore.addRarityLore(rarity);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    public Component getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Material getMaterial() {
        return material;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public SuperItemType getSuperItemType() {
        return superItemType;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getQuality() {
        return quality;
    }

    public double getProbability() {
        return probabilityPercentage;
    }

    public double getRarity() {
        return rarity;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumid() {
        return humid;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBase(double base) {
        this.base = base;
    }
}

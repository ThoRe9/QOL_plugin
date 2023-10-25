package net.okuri.qol.superItems.resources;

import net.kyori.adventure.text.Component;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import net.okuri.qol.qolCraft.calcuration.RandFromXYZ;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class SuperResource extends SuperItem {
    // SuperResource の設定方法
    // 1. コンストラクタを設定する・
    // 2. superResourcesに追加する。

    // 以下設定必須項目
    Component name;
    String info;
    final Material material;
    final Material blockMaterial;
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
    Player producer;

    public SuperResource(Component name, String info, Material material, Material blockMaterial, SuperItemType superItemType, int probabilityPercentage) {
        super(superItemType);
        this.name = name;
        this.info = info;
        this.material = material;
        this.blockMaterial = blockMaterial;
        this.probabilityPercentage = probabilityPercentage;
    }

    public SuperResource(Material material, Material blockMaterial, SuperItemType superItemType, int probabilityPercentage) {
        super(superItemType);
        this.name = Component.text("Super" + material.name());
        this.info = "Super" + material.name();
        this.material = material;
        this.blockMaterial = blockMaterial;
        this.probabilityPercentage = probabilityPercentage;
    }

    public void setResVariables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality, Player producer) {
        this.Px = Px;
        this.Py = Py;
        this.Pz = Pz;
        this.temp = temp;
        this.humid = humid;
        this.biomeId = biomeId;
        this.quality = quality;
        this.producer = producer;

        // パラメータ計算 (SuperWheatのものを拝借)
        RandFromXYZ rand = new RandFromXYZ();
        rand.setVariable(this.Px, this.Py, this.Pz, this.biomeId, 0.05);
        rand.calcuration();
        ArrayList<Double> correction = rand.getAns();
        CirculeDistribution calc = new CirculeDistribution();
        calc.setVariable(this.temp + 0.75, 0.5, 1, 3);
        calc.setCorrection(correction);
        calc.calcuration();
        ArrayList<Double> ans = calc.getAns();
        this.x = ans.get(0);
        this.y = ans.get(1);
        this.z = ans.get(2);

        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType());
        ItemMeta meta = result.getItemMeta();
        meta.displayName(name);
        meta.setCustomModelData(super.getSuperItemType().getCustomModelData());

        PDCC.setSuperResource(meta, this);

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

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.setResVariables(90, 90, 90, 0.5, 0.5, 10, 1.0, (Player) org.bukkit.Bukkit.getOfflinePlayer("okuri0131"));
        return this.getSuperItem();
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

    public int getRarity() {
        return rarity;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumid() {
        return humid;
    }

    public String getProducerName() {
        return producer.getName();
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

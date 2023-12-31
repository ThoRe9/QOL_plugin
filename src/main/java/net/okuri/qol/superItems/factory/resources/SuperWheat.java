package net.okuri.qol.superItems.factory.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import net.okuri.qol.qolCraft.calcuration.RandFromXYZ;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SuperWheat extends SuperResource {

    public SuperWheat(SuperItemType type) {
        super(Material.WHEAT, SuperItemType.WHEAT, 3);
        if (type == SuperItemType.RYE || type == SuperItemType.BARLEY || type == SuperItemType.WHEAT || type == SuperItemType.RICE) {
            this.setSuperItemType(type);
        } else {
            this.setSuperItemType(SuperItemType.WHEAT);
        }
    }

    public SuperWheat() {

        super(Material.WHEAT, SuperItemType.WHEAT, 3);
    }

    @Override
    public void setResVariables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality, Player producer) {
        this.Px = Px;
        this.Py = Py;
        this.Pz = Pz;
        this.temp = temp;
        this.humid = humid;
        this.biomeId = biomeId;
        this.quality = quality;
        this.producer = producer;

        // パラメータ計算
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
        if (temp <= 0) {
            this.setSuperItemType(SuperItemType.RYE);
        } else if (temp <= 0.70) {
            this.setSuperItemType(SuperItemType.BARLEY);
        } else if (temp <= 0.85) {
            this.setSuperItemType(SuperItemType.WHEAT);
        } else {
            this.setSuperItemType(SuperItemType.RICE);
        }
    }

    public SuperResourceStack getSuperItem() {
        SuperResourceStack wheat = new SuperResourceStack(this.getSuperItemType(), this);
        // PersistentDataContainer にデータを保存
        ItemMeta meta = wheat.getItemMeta();
        // 名前を設定
        Component display;
        SuperItemType type = this.getSuperItemType();
        if (type == SuperItemType.RYE) {
            display = Component.text("rye").color(NamedTextColor.GOLD);
        } else if (type == SuperItemType.BARLEY) {
            display = Component.text("barley").color(NamedTextColor.GOLD);
        } else if (type == SuperItemType.WHEAT) {
            display = Component.text("wheat").color(NamedTextColor.GOLD);
        } else {
            display = Component.text("rice").color(NamedTextColor.RED);
        }
        meta.displayName(display);

        // 説明を設定
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addInfoLore("This is a super wheat!");
        loreGenerator.addParametersLore("Px", this.x);
        loreGenerator.addParametersLore("Py", this.y);
        loreGenerator.addParametersLore("Pz", this.z);
        loreGenerator.addParametersLore("quality", this.quality);
        meta.lore(loreGenerator.generateLore());

        wheat.setItemMeta(meta);

        return wheat;
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {

        double ttemp = 0.5;
        if (args.length == 1) {
            int type = args[0];
            switch (type) {
                case 0:
                    this.setSuperItemType(SuperItemType.RYE);
                    break;
                case 1:
                    this.setSuperItemType(SuperItemType.BARLEY);
                    break;
                case 2:
                    this.setSuperItemType(SuperItemType.WHEAT);
                    break;
                case 3:
                    this.setSuperItemType(SuperItemType.RICE);
                    break;
                default:
                    this.setSuperItemType(SuperItemType.WHEAT);
                    break;
            }
        } else if (args.length == 2) {
            int type = args[0];
            switch (type) {
                case 0:
                    this.setSuperItemType(SuperItemType.RYE);
                    break;
                case 1:
                    this.setSuperItemType(SuperItemType.BARLEY);
                    break;
                case 2:
                    this.setSuperItemType(SuperItemType.WHEAT);
                    break;
                case 3:
                    this.setSuperItemType(SuperItemType.RICE);
                    break;
                default:
                    this.setSuperItemType(SuperItemType.WHEAT);
                    break;
            }
            ttemp = (double) args[1] / 10.0;

        } else {
            this.setSuperItemType(SuperItemType.WHEAT);
        }

        this.Px = 10;
        this.Py = 10;
        this.Pz = 10;
        this.temp = ttemp;
        this.humid = 0.5;
        this.biomeId = 100;
        this.x = 0.33;
        this.y = 0.33;
        this.z = 0.33;
        this.quality = 1.0;
        this.rarity = 1;
        this.name = Component.text("debug");
        return this.getSuperItem();

    }

    @Deprecated
    private void calcPs() {
        this.x = calcTemp('x');
        this.y = calcTemp('y');
        this.z = calcTemp('z');
    }

    @Deprecated
    private double calcTemp(char param) {
        // wheatのパラメータを計算。要バランス調整。
        int initValue;
        double rxy = Math.abs(((this.Px + this.Py) / 64.0) % this.biomeId) / (5.0 * this.biomeId);
        double ryz = Math.abs(((this.Py + this.Pz) / 64.0) % this.biomeId) / (5.0 * this.biomeId);
        double rzx = Math.abs(((this.Pz + this.Px) / 64.0) % this.biomeId) / (5.0 * this.biomeId);
        if (param == 'x') {
            initValue = 9;
            rxy *= -1;
            ryz = 0;
        } else if (param == 'y') {
            initValue = 5;
            ryz *= -1;
            rzx = 0;
        } else if (param == 'z') {
            initValue = 1;
            rzx *= -1;
            rxy = 0;
        } else {
            return -1;
        }

        double rad = (this.temp + ((double) initValue / 12)) * Math.PI * 2;
        double r = rxy + ryz + rzx;
        return ((1 + Math.sin(rad)) / 3 + r) * this.quality;

    }
}

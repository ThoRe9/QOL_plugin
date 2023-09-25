package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import net.okuri.qol.qolCraft.calcuration.RandFromXYZ;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SuperWheat extends SuperResource{
    private final ItemStack wheat = new ItemStack(Material.WHEAT);
    private int x;
    private int y;
    private int z;
    private String name;
    private double temp;
    private double humid;
    private int biomeID;
    private double quality;
    private int rarity;
    private SuperItemType superItemType;
    private double px;
    private double py;
    private double pz;
    public SuperWheat(){}
    public SuperWheat(SuperItemType type){
        if (type == SuperItemType.RYE || type == SuperItemType.BARLEY || type == SuperItemType.WHEAT || type == SuperItemType.RICE) {
            this.superItemType = type;
        } else {
            this.superItemType = SuperItemType.WHEAT;
        }
    }

    
    public SuperWheat(int x, int y, int z, String name, double temp, double humid, int biomeID, double quality) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.temp = temp;
        this.humid = humid;
        this.biomeID = biomeID;
        this.quality = quality;


        if (temp <= 0){
            this.superItemType = SuperItemType.RYE;
        } else if (temp <= 0.5) {
            this.superItemType = SuperItemType.BARLEY;
        } else if (temp <= 1.0) {
            this.superItemType = SuperItemType.WHEAT;
        } else{
            this.superItemType = SuperItemType.RICE;
        }
    }
    
    public ItemStack getSuperItem() {
        // パラメータ計算
        RandFromXYZ rand = new RandFromXYZ();
        rand.setVariable(this.x, this.y, this.z, this.biomeID, 0.05);
        rand.calcuration();
        ArrayList<Double> correction = rand.getAns();
        CirculeDistribution calc = new CirculeDistribution();
        calc.setVariable(this.temp + 0.75, 0.5, 1, 3);
        calc.setCorrection(correction);
        calc.calcuration();
        ArrayList<Double> ans = calc.getAns();
        this.px = ans.get(0);
        this.py = ans.get(1);
        this.pz = ans.get(2);
        this.rarity = SuperItem.getRarity(px,py,pz);

        // PersistentDataContainer にデータを保存
        ItemMeta meta = wheat.getItemMeta();

        PDCC.set(meta, PDCKey.TYPE, this.superItemType.toString());
        PDCC.set(meta, PDCKey.X, this.px);
        PDCC.set(meta, PDCKey.Y, this.py);
        PDCC.set(meta, PDCKey.Z, this.pz);
        PDCC.set(meta, PDCKey.NAME, this.name);
        PDCC.set(meta, PDCKey.TEMP, this.temp);
        PDCC.set(meta, PDCKey.BIOME_ID, this.biomeID);
        PDCC.set(meta, PDCKey.QUALITY, this.quality);
        PDCC.set(meta, PDCKey.RARITY, this.rarity);
        PDCC.set(meta, PDCKey.HUMID, this.humid);

        // 名前を設定
        Component display;
        if (this.superItemType == SuperItemType.RYE) {
            display = Component.text("rye").color(NamedTextColor.GOLD);
        } else if (this.superItemType == SuperItemType.BARLEY) {
            display = Component.text("barley").color(NamedTextColor.GOLD);
        } else if (this.superItemType == SuperItemType.WHEAT) {
            display = Component.text("wheat").color(NamedTextColor.GOLD);
        } else {
            display = Component.text("rice").color(NamedTextColor.RED);
        }
        meta.displayName(display);

        // 説明を設定
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addInfoLore("This is a super wheat!");
        loreGenerator.addInfoLore("This is made by " + this.name);
        loreGenerator.addParametersLore("x", this.px*10);
        loreGenerator.addParametersLore("y", this.py*10);
        loreGenerator.addParametersLore("z", this.pz*10);
        loreGenerator.addParametersLore("quality", this.quality);
        meta.lore(loreGenerator.generateLore());

        wheat.setItemMeta(meta);
        Bukkit.getServer().getLogger().info("SuperWheat: " + px + ", " + py + ", " + pz);
        
        return wheat;
    }

    @Override
    public void setResValiables(int x, int y, int z, double temp, double humid, int biomeId, double quality) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.temp = temp;
        this.humid = humid;
        this.biomeID = biomeId;
        this.quality = quality;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        double ttemp = 0.5;
        if (args.length == 1) {
            int type = args[0];
            switch (type) {
                case 0:
                    this.superItemType = SuperItemType.RYE;
                    break;
                case 1:
                    this.superItemType = SuperItemType.BARLEY;
                    break;
                case 2:
                    this.superItemType = SuperItemType.WHEAT;
                    break;
                case 3:
                    this.superItemType = SuperItemType.RICE;
                    break;
                default:
                    this.superItemType = SuperItemType.WHEAT;
                    break;
            }
        } else if( args.length == 2){
            int type = args[0];
            switch (type) {
                case 0:
                    this.superItemType = SuperItemType.RYE;
                    break;
                case 1:
                    this.superItemType = SuperItemType.BARLEY;
                    break;
                case 2:
                    this.superItemType = SuperItemType.WHEAT;
                    break;
                case 3:
                    this.superItemType = SuperItemType.RICE;
                    break;
                default:
                    this.superItemType = SuperItemType.WHEAT;
                    break;
            }
            ttemp = (double) args[1]/10.0;

        }else{
            this.superItemType = SuperItemType.WHEAT;
        }

        this.x = 10;
        this.y = 10;
        this.z = 10;
        this.temp = ttemp;
        this.humid = 0.5;
        this.biomeID = 100;
        this.px = 0.33;
        this.py = 0.33;
        this.pz = 0.33;
        this.quality = 1.0;
        this.rarity = 1;
        this.name = "debug";
        return this.getSuperItem();

    }
    @Deprecated
    private void calcPs() {
        this.px = calcTemp('x');
        this.py = calcTemp('y');
        this.pz = calcTemp('z');
    }

    @Deprecated
    private double calcTemp(char param){
        // wheatのパラメータを計算。要バランス調整。
        int initValue;
        double rxy = Math.abs(((this.x + this.y)/64.0) % this.biomeID) / (5.0*this.biomeID);
        double ryz = Math.abs(((this.y + this.z)/64.0) % this.biomeID) / (5.0*this.biomeID);
        double rzx = Math.abs(((this.z + this.x)/64.0) % this.biomeID) / (5.0*this.biomeID);
        if (param== 'x') {
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

        Bukkit.getServer().getLogger().info("SuperWheatRXYZ: " + rxy + ", " + ryz + ", " + rzx);

        double rad = (this.temp + ((double) initValue / 12)) * Math.PI * 2;
        double r  = rxy + ryz + rzx;
        return ((1 + Math.sin(rad))/3 + r)*this.quality;

    }
}

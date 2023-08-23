package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SuperWheat extends SuperItem{
    private final ItemStack wheat = new ItemStack(Material.WHEAT);
    private final int x;
    private final int y;
    private final int z;
    private final String name;
    private final double temp;
    private final int biomeID;
    private final double quality;
    private final SuperItemType superItemType;
    private double px;
    private double py;
    private double pz;
    public static NamespacedKey xkey = new NamespacedKey("qol", "super_wheat_data_x");
    public static NamespacedKey ykey = new NamespacedKey("qol", "super_wheat_data_y");
    public static NamespacedKey zkey = new NamespacedKey("qol", "super_wheat_data_z");
    public static NamespacedKey namekey = new NamespacedKey("qol", "super_wheat_name");
    
    public SuperWheat(int x, int y, int z, String name, double temp, int biomeID, double quality) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.temp = temp;
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
        // PersistentDataContainer にデータを保存
        this.calcPs();
        NamespacedKey typekey = SuperItemType.typeKey;
        ItemMeta meta = wheat.getItemMeta();

        meta.getPersistentDataContainer().set(typekey, PersistentDataType.STRING, this.superItemType.getStringType());
        meta.getPersistentDataContainer().set(xkey, PersistentDataType.DOUBLE, this.px);
        meta.getPersistentDataContainer().set(ykey, PersistentDataType.DOUBLE, this.py);
        meta.getPersistentDataContainer().set(zkey, PersistentDataType.DOUBLE, this.pz);
        meta.getPersistentDataContainer().set(namekey, PersistentDataType.STRING, this.name);

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
    
    private void calcPs() {
        this.px = calcTemp('x');
        this.py = calcTemp('y');
        this.pz = calcTemp('z');
    }

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

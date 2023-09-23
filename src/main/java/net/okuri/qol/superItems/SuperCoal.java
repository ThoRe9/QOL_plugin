package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

public class SuperCoal extends SuperResource {
    private SuperItemType superItemType = SuperItemType.COAL;
    private ItemStack itemStack = new ItemStack(Material.COAL, 1);
    // Px Py Pz は座標情報を記憶する。
    private int Px;
    private int Py;
    private int Pz;
    private int sum;
    // qualityは、全体の効果時間への倍率。
    private double quality;
    // pxは掘ったy座標に依存している。(マイクラの生成確立に反比例する)
    private double x =0;
    // pyは掘ったx,z座標に依存(乱数)
    private double y =0;
    // pzは掘ったx,z座標に依存(乱数)
    private double z =0;
    // tempは生産地の気温
    private double temp;
    // humidは生産地の湿度
    private double humid;
    // biomeIDは生産地のバイオームID
    private int biomeID;
    private int rarity;
    public SuperCoal(){
    }
    public ItemStack getSuperItem() {
        this.calc();
        // PersistentDataContainer にデータを保存
        ItemMeta meta = itemStack.getItemMeta();

        PDCC.setSuperItem(meta, this.superItemType, this.x, this.y, this.z, this.quality, this.rarity,  this.temp, this.humid );

        //名前を変更
        Component display;
        display = Component.text("Super Coal").color(NamedTextColor.DARK_GRAY);
        meta.displayName(display);

        //Loreを変更
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity, "JUST A COAL");
        meta.lore(loreGenerator.generateLore());

        this.itemStack.setItemMeta(meta);
        Bukkit.getLogger().info((String)PDCC.get(this.itemStack, PDCKey.TYPE));
        return this.itemStack;

    }
    @Override
    public ItemStack getDebugItem(int... args) {
        this.setResValiables(90, 0, 0, 0.5, 0.5, 0, 1.0);
        return this.getSuperItem();
    }

    @Override
    public void setResValiables(int Px, int Py, int Pz, double temp, double humid, int biomeId, double quality) {
        this.Px = Px;
        this.Py = Py;
        this.Pz = Pz;
        this.temp = temp;
        this.humid = humid;
        this.biomeID = biomeId;
        this.quality = quality;
    }

    private void calc(){
        this.x = (double) Math.abs(90 - this.Py) / 270;
        CirculeDistribution cd = new CirculeDistribution();
        cd.setVariable(this.temp + 0.75, 1, 1.0-this.x, 2);
        ArrayList<Double> correction = new ArrayList<>();
        correction.add(Math.pow(this.humid, 3));
        correction.add(Math.pow(this.temp, 3));
        cd.setCorrection(correction);
        cd.calcuration();
        ArrayList<Double> ans = cd.getAns();
        this.x = this.x * this.quality;
        this.y = ans.get(0) * this.quality;
        this.z = ans.get(1) * this.quality;
        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);
    }
}

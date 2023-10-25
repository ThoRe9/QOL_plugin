package net.okuri.qol.superItems.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.qolCraft.calcuration.CirculeDistribution;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SuperCoal extends SuperResource {

    public SuperCoal() {
        super(Component.text("Super Coal").color(NamedTextColor.GREEN), "This is a coal!", Material.COAL, Material.COAL_ORE, SuperItemType.COAL, 3);
    }

    @Override
    public SuperItemStack getSuperItem() {
        this.calc();
        SuperItemStack itemStack = new SuperItemStack(this.getSuperItemType());
        // PersistentDataContainer にデータを保存
        ItemMeta meta = itemStack.getItemMeta();

        PDCC.setSuperItem(meta, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);

        //名前を変更
        Component display;
        display = Component.text("Super Coal").color(NamedTextColor.DARK_GRAY);
        meta.displayName(display);

        //Loreを変更
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity, "JUST A COAL");
        meta.lore(loreGenerator.generateLore());

        itemStack.setItemMeta(meta);
        return itemStack;

    }
    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.setResVariables(90, 0, 0, 0.5, 0.5, 0, 1.0, (Player) Bukkit.getOfflinePlayer("okuri0131"));
        return this.getSuperItem();
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

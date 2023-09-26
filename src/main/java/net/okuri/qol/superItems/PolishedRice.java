package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PolishedRice implements SuperCraftable {
    private ItemStack polishedRice;
    private ItemStack rice;
    private SuperItemType type = SuperItemType.POLISHED_RICE;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double ricePolishingRatio = 1.0;
    private int rarity = 0;
    private double quality = 0;
    private double temp = 0;
    private double humid = 0;

    public PolishedRice(){
        setting();
    }

    public PolishedRice(ItemStack polishedRice){
        // typeがPolishedRiceやRiceじゃないならエラーを吐く
        SuperItemType type = SuperItemType.valueOf(PDCC.get(polishedRice.getItemMeta(), PDCKey.TYPE));
        if (type == SuperItemType.RICE){
            this.rice = polishedRice;
        } else if (type == SuperItemType.POLISHED_RICE){
            this.polishedRice = polishedRice;
        } else {
            throw new IllegalArgumentException("typeがPolishedRiceやRiceじゃない");
        }
        setting();
    }
    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.rice = matrix[0];
        setting();
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = new ItemStack(Material.PUMPKIN_SEEDS);
        ItemMeta meta = result.getItemMeta();
        PDCC.setSuperItem(meta, this.type, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);

        meta.displayName(Component.text("Polished Rice").color(NamedTextColor.GOLD));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Polished Rice");
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Rice Polishing Ratio" , this.ricePolishingRatio, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        this.polishedRice = new Rice().getDebugItem();
        setting();
        return getSuperItem();
    }
    private void setting(){
        //riceが設定されている場合、x,y,z,rarity,quality, temp. humidを設定する
        if(this.rice != null){
            ItemMeta meta = this.rice.getItemMeta();
            this.x = PDCC.get(meta, PDCKey.X);
            this.y = PDCC.get(meta, PDCKey.Y);
            this.z = PDCC.get(meta, PDCKey.Z);
            this.rarity = PDCC.get(meta, PDCKey.RARITY);
            this.quality = PDCC.get(meta, PDCKey.QUALITY);
            this.temp = PDCC.get(meta, PDCKey.TEMP);
            this.humid = PDCC.get(meta, PDCKey.HUMID);
        }
        //polishedRiceが設定されている場合、上記に加えてricePolishingRatioを設定する
        if(this.polishedRice != null) {
            ItemMeta meta = this.polishedRice.getItemMeta();
            this.x = PDCC.get(meta, PDCKey.X);
            this.y = PDCC.get(meta, PDCKey.Y);
            this.z = PDCC.get(meta, PDCKey.Z);
            this.rarity = PDCC.get(meta, PDCKey.RARITY);
            this.quality = PDCC.get(meta, PDCKey.QUALITY);
            this.temp = PDCC.get(meta, PDCKey.TEMP);
            this.humid = PDCC.get(meta, PDCKey.HUMID);
            this.ricePolishingRatio = PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO);
            // rPRが 0.7なら0.6に、0.6なら0.5に、0.5なら0.4に、0.4なら0.4にする。
            if(this.ricePolishingRatio > 0.7) {
                this.ricePolishingRatio = 0.7;
            }else if(this.ricePolishingRatio == 0.7){
                this.ricePolishingRatio = 0.6;
            }else if(this.ricePolishingRatio == 0.6){
                this.ricePolishingRatio = 0.5;
            }else if(this.ricePolishingRatio == 0.5){
                this.ricePolishingRatio = 0.4;
            }else{
                this.ricePolishingRatio = 0.4;
                return;
            }
            // x,y,zのうち、maxとminを求める
            double max = Math.max(Math.max(this.x, this.y), this.z);
            double min = Math.min(Math.min(this.x, this.y), this.z);
            // maxのパラメータを　-0.05する
            // minのパラメータを +0.10する
            // 以下処理
            if(max == this.x){
                this.x -= 0.05;
            }else if(max == this.y){
                this.y -= 0.05;
            }else if(max == this.z){
                this.z -= 0.05;
            }
            if(min == this.x){
                this.x += 0.1;
            }else if(min == this.y){
                this.y += 0.1;
            }else if(min == this.z){
                this.z += 0.1;
            }

        }
    }
}

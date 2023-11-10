package net.okuri.qol.superItems.factory.ingredient;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.resources.Rice;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PolishedRice extends SuperItem implements SuperCraftable {
    private ItemStack polishedRice;
    private ItemStack rice;
    private final SuperItemType type = SuperItemType.POLISHED_RICE;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double ricePolishingRatio = 1.0;
    private int rarity = 0;
    private double quality = 0;
    private double temp = 0;
    private double humid = 0;

    public PolishedRice() {
        super(SuperItemType.POLISHED_RICE);
        setting();
    }

    public PolishedRice(SuperItemStack polishedRice) {
        super(SuperItemType.POLISHED_RICE, polishedRice);
        // typeがPolishedRiceやRiceじゃないならエラーを吐く
        SuperItemType type = SuperItemType.valueOf(PDCC.get(polishedRice.getItemMeta(), PDCKey.TYPE));
        if (type == SuperItemType.RICE) {
            this.rice = polishedRice;
        } else if (type == SuperItemType.POLISHED_RICE) {
            this.polishedRice = polishedRice;
        } else {
            throw new IllegalArgumentException("typeがPolishedRiceやRiceじゃない");
        }
        setting();
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        SuperItemStack rice = matrix[0];
        SuperItemType type = SuperItemType.valueOf(PDCC.get(rice.getItemMeta(), PDCKey.TYPE));
        if (Objects.equals(id, "polished_rice2")) {
            this.rice = rice;
            this.polishedRice = null;
            this.ricePolishingRatio = 1.0;
        } else if (Objects.equals(id, "polished_rice")) {
            this.polishedRice = rice;
            this.rice = null;
        } else {
            throw new IllegalArgumentException("無効なレシピID");
        }
        setting();

    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType());
        ItemMeta meta = result.getItemMeta();
        PDCC.setSuperItem(meta, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);

        meta.displayName(Component.text("Polished Rice").color(NamedTextColor.GOLD));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Polished Rice");
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio, true);
        meta.lore(lore.generateLore());
        meta.setCustomModelData(this.type.getCustomModelData());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.rice = new Rice().getDebugItem();
        this.polishedRice = null;
        setting();
        return getSuperItem();
    }

    private void setting() {
        //riceが設定されている場合、x,y,z,rarity,quality, temp. humidを設定する
        if (this.rice != null) {
            ItemMeta meta = this.rice.getItemMeta();
            this.x = PDCC.get(meta, PDCKey.X);
            this.y = PDCC.get(meta, PDCKey.Y);
            this.z = PDCC.get(meta, PDCKey.Z);
            this.rarity = PDCC.get(meta, PDCKey.RARITY);
            this.quality = PDCC.get(meta, PDCKey.QUALITY);
            this.temp = PDCC.get(meta, PDCKey.TEMP);
            this.humid = PDCC.get(meta, PDCKey.HUMID);
            this.ricePolishingRatio = 0.7;
        }
        //polishedRiceが設定されている場合、上記に加えてricePolishingRatioを設定する
        if (this.polishedRice != null) {
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
            if (this.ricePolishingRatio > 0.7) {
                this.ricePolishingRatio = 0.7;
            } else if (this.ricePolishingRatio == 0.7) {
                this.ricePolishingRatio = 0.6;
            } else if (this.ricePolishingRatio == 0.6) {
                this.ricePolishingRatio = 0.5;
            } else if (this.ricePolishingRatio == 0.5) {
                this.ricePolishingRatio = 0.4;
            } else {
                this.ricePolishingRatio = 0.4;
                return;
            }
            // x,y,zのうち、maxとminを求める
            double max = Math.max(Math.max(this.x, this.y), this.z);
            double min = Math.min(Math.min(this.x, this.y), this.z);
            // maxのパラメータを　-|temp-1|*0.07する
            // minのパラメータを +temp*(humid+0.2)*0.2する
            // 以下処理
            double a = Math.abs(this.temp - 1) * 0.07;
            double b = this.temp * (this.humid + 0.2) * 0.2;
            if (max == this.x) {
                this.x -= a;
            } else if (max == this.y) {
                this.y -= a;
            } else if (max == this.z) {
                this.z -= a;
            }
            if (min == this.x) {
                this.x += b;
            } else if (min == this.y) {
                this.y += b;
            } else if (min == this.z) {
                this.z += b;
            }
        }
    }
}

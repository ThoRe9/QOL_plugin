package net.okuri.qol.superItems.factory.drinks.whisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class Highball extends SuperItem implements SuperCraftable {
    private SuperItemStack whisky = null;
    private SuperItemStack[] matrix = null;
    private double strength = 1.0;
    private double x;
    private double y;
    private double z;
    private double divline;
    private double quality;
    private int rarity;
    private double temp;
    private double humid;
    private int maturation;
    private double alcoholPer = 0.0;
    private final double alcoholAmount = 150.0;

    public Highball() {
        super(SuperItemType.HIGHBALL);
    }

    public Highball(SuperItemStack highball) {
        super(SuperItemType.HIGHBALL);
        if (!(highball.getSuperItemType() == SuperItemType.HIGHBALL)) {
            throw new IllegalArgumentException("HighballのコンストラクタにはSuperItemType.HIGHBALLを渡してください");
        }
        ItemMeta meta = highball.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.divline = PDCC.get(meta, PDCKey.DIVLINE);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.rarity = PDCC.get(meta, PDCKey.RARITY);
        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);
        this.maturation = PDCC.get(meta, PDCKey.MATURATION);
        this.strength = PDCC.get(meta, PDCKey.SODA_STRENGTH);
        this.alcoholPer = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);

    }
    @Override
    public SuperItemStack getSuperItem() {
        // HighballはWhiskyのFAST_DIGGING, SPEED, NIGHT_VISIONのdurationにstrengthをかけた効果を持つ
        SuperItemStack highball = new SuperItemStack(SuperItemType.HIGHBALL, 3);
        PotionMeta meta = (PotionMeta) highball.getItemMeta();
        PotionMeta whiskyMeta = (PotionMeta) this.whisky.getItemMeta();
        meta.setCustomModelData(this.getSuperItemType().getCustomModelData());
        PDCC.setLiquor(meta, this.alcoholAmount, this.alcoholPer, this.x, this.y, this.z, this.divline, this.quality, this.rarity, this.temp, this.humid, this.maturation);
        for (PotionEffect effect : whiskyMeta.getCustomEffects()) {
            PotionEffect newEffect = new PotionEffect(effect.getType(), (int) Math.round(effect.getDuration() * this.strength), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles(), effect.hasIcon());
            meta.addCustomEffect(newEffect, true);
        }
        meta.setColor(Color.fromRGB(220, 210, 150));

        // displaynameの設定
        meta.displayName(Component.text("Highball").color(NamedTextColor.GOLD));

        // LOREの設定
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Whisky with soda!");
        lore.addParametersLore("Strength", (this.strength - 1) * 10);
        lore.addParametersLore("Alcohol: ", this.alcoholPer, true);
        lore.addParametersLore("Amount: ", this.alcoholAmount, true);
        meta.lore(lore.generateLore());
        highball.setItemMeta(meta);
        return highball;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        SuperItemStack whisky = new Whisky().getDebugItem();
        this.whisky = whisky;
        this.strength = 1.0;
        this.alcoholPer = 0.1;
        this.x = 0.33;
        this.y = 0.33;
        this.z = 0.33;
        this.divline = 0.33;
        this.quality = 0.33;
        this.rarity = 1;
        this.temp = 0.33;
        this.humid = 0.33;
        this.maturation = 0;
        return getSuperItem();
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.matrix = matrix;
        // 4にはwhisky 6,7,8にはsodaがある
        setting(matrix[4], new SuperItemStack[]{matrix[6], matrix[7], matrix[8]});
    }

    private void setting(SuperItemStack whisky, SuperItemStack[] sodas) {
        this.whisky = whisky;
        ItemMeta whiskyMeta = whisky.getItemMeta();
        // sodaのstrengthの平均値をstrengthにする
        double sum = 0.0;
        for (SuperItemStack soda : sodas) {
            sum += (double) PDCC.get(soda, PDCKey.SODA_STRENGTH);
        }
        this.strength = (sum / 3.0);
        this.alcoholPer = (double) PDCC.get(whiskyMeta, PDCKey.ALCOHOL_PERCENTAGE) / 5;
        this.x = PDCC.get(whiskyMeta, PDCKey.X);
        this.y = PDCC.get(whiskyMeta, PDCKey.Y);
        this.z = PDCC.get(whiskyMeta, PDCKey.Z);
        this.divline = PDCC.get(whiskyMeta, PDCKey.DIVLINE);
        this.quality = PDCC.get(whiskyMeta, PDCKey.QUALITY);
        this.rarity = PDCC.get(whiskyMeta, PDCKey.RARITY);
        this.temp = PDCC.get(whiskyMeta, PDCKey.TEMP);
        this.humid = PDCC.get(whiskyMeta, PDCKey.HUMID);
        this.maturation = PDCC.get(whiskyMeta, PDCKey.MATURATION);

    }
}

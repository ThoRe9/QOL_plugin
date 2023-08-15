package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

public class Highball extends DrinkCraftable{
    private ItemStack whisky = null;
    private ItemStack[] matrix = null;
    private double strength = 1.0;

    @Override
    public ItemStack getSuperItem() {
        // HighballはWhiskyのFAST_DIGGING, SPEED, NIGHT_VISIONのdurationにstrengthをかけた効果を持つ
        ItemStack highball = new ItemStack(Material.POTION, 3);
        PotionMeta meta = (PotionMeta)highball.getItemMeta();
        PotionMeta whiskyMeta = (PotionMeta) this.whisky.getItemMeta();
        for (PotionEffect effect : whiskyMeta.getCustomEffects()) {
            PotionEffect newEffect = new PotionEffect(effect.getType(), (int)Math.round(effect.getDuration() * this.strength), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles(), effect.hasIcon());
            meta.addCustomEffect(newEffect, true);
        }
        meta.setColor(Color.fromRGB(220, 210, 150));

        // displaynameの設定
        meta.displayName(Component.text("Highball").color(NamedTextColor.GOLD));

        // LOREの設定
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Whisky with soda!");
        lore.addParametersLore("Strength", (this.strength-1)*10);
        meta.lore(lore.generateLore());
        highball.setItemMeta(meta);
        return highball;
    }

    public Highball(ItemStack[] matrix) {
        this.matrix = matrix;
        // 4にはwhisky 6,7,8にはsodaがある
        setting(matrix[4], new ItemStack[]{matrix[6], matrix[7], matrix[8]});
    }
    public Highball(){

    }

    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        // 4にはwhisky 6,7,8にはsodaがある
        setting(matrix[4], new ItemStack[]{matrix[6], matrix[7], matrix[8]});
    }

    private void setting(ItemStack whisky, ItemStack[] sodas) {
        this.whisky = whisky;
        // sodaのstrengthの平均値をstrengthにする
        double sum = 0.0;
        for (ItemStack soda : sodas) {
            sum += soda.getItemMeta().getPersistentDataContainer().get(Soda.strengthkey, PersistentDataType.DOUBLE);
        }
        this.strength = (sum / 3.0);

    }
}

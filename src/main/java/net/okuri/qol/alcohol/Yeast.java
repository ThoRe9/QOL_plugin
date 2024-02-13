package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.loreGenerator.FermentationLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Yeast extends SuperItem {
    /**
     * ferementationRate 1日で何日分の発酵をするか
     * alcoholRate Tasteパラメータが1減った時のアルコール量の変化量
     */
    private double fermentationRate = 1.0;
    private double alcoholRate = 0.05;

    public Yeast() {
        super(SuperItemType.YEAST);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = new SuperItemStack(this.getSuperItemType());
        stack.setDisplayName(Component.text("酵母", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        ItemMeta meta = stack.getItemMeta();

        PDCC.set(meta, PDCKey.FERMENTATION_RATE, fermentationRate);
        PDCC.set(meta, PDCKey.FERMENTATION_ALC_RATE, alcoholRate);

        FermentationLore lore = new FermentationLore(fermentationRate, alcoholRate);
        LoreGenerator gen = new LoreGenerator();
        gen.addLore(lore);
        gen.setLore(meta);

        stack.setItemMeta(meta);

        return stack;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return getSuperItem();
    }

}

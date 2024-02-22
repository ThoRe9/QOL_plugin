package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class YeastGotcha extends SuperItem {
    public YeastGotcha() {
        super(SuperItemType.YEAST_GOTCHA);
    }

    public static void get(Player player) {
        Yeast yeast = new Yeast();

        Random random = new Random();
        int rarity;
        double num = Math.abs(random.nextGaussian(1, 0.3));
        if (num < 1) {
            rarity = 1;
        } else if (num < 1.2) {
            rarity = 2;
        } else if (num < 1.35) {
            rarity = 3;
        } else if (num < 1.475) {
            rarity = 4;
        } else {
            rarity = 5;
        }
        yeast.setFermentationRate(num);

        double num2 = Math.abs(random.nextGaussian(0, 0.1));
        int rarity2;
        if (num2 < 0.065) {
            rarity2 = 1;
        } else if (num2 < 0.115) {
            rarity2 = 2;
        } else if (num2 < 0.155) {
            rarity2 = 3;
        } else if (num2 < 0.2) {
            rarity2 = 4;
        } else {
            rarity2 = 5;
        }
        yeast.setAlcoholRate(num2);

        player.getInventory().addItem(yeast.getSuperItem());
        ChatGenerator chat = new ChatGenerator();
        chat.addSuccess("You got a Yeast!").addSuccess("Rarity: " + StringUtils.repeat('☆', (rarity + rarity2) / 2)).sendMessage(player);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        stack.setDisplayName(Component.text("酵母菌ガチャ").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.LIGHT_PURPLE));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("右クリックでガチャを引く!");
        ItemMeta meta = stack.getItemMeta();
        meta.lore(lore.generate());
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }

}

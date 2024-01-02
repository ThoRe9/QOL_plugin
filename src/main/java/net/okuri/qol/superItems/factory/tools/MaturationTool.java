package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaturationTool extends SuperItem {
    private int maturationDate;

    public MaturationTool(int day) {
        super(SuperItemType.MATURATION_TOOL);
        this.maturationDate = day;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.MATURATION_TOOL);
        item.setDisplayName(Component.text("Maturation Tool").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("You can advance maturation for 1 day by right click!");
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        PDCC.set(meta, PDCKey.MATURATION_TOOL_AMOUNT, this.maturationDate);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }
}

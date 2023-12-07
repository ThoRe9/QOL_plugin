package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FarmerToolAdapter extends SuperItem implements SuperCraftable {
    public FarmerToolAdapter() {
        super(SuperItemType.FARMER_TOOL);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.FARMER_TOOL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Farmer Tool").color(NamedTextColor.GREEN));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("This tool is for farmer!.");
        lore.addInfoLore("Craft with hoe and get Super Resources from crops!");
        meta.lore(lore.generateLore());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return getSuperItem();
    }
}

package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.inventory.meta.ItemMeta;

public class MinerToolAdapter extends SuperItem implements SuperCraftable {
    public MinerToolAdapter() {
        super(SuperItemType.MINER_TOOL);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.MINER_TOOL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Miner Tool").color(NamedTextColor.GREEN));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("This tool is for miner!.");
        lore.addInfo("Craft with pickaxe and get Super Resources from ores!");
        meta.lore(lore.generate());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return getSuperItem();
    }
}

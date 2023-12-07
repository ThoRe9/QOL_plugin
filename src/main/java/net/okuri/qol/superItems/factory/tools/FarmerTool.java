package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FarmerTool implements SuperCraftable {
    private SuperItemStack tool;

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.tool = matrix[0];
    }

    @Override
    public SuperItemStack getSuperItem() {
        ItemMeta meta = tool.getItemMeta();
        Component toolName = meta.displayName();
        if (toolName == null) {
            toolName = Component.text("tool");
        }
        meta.displayName(Component.text("Farmer's ").color(NamedTextColor.GREEN).append(toolName));
        PDCC.set(meta, PDCKey.FARMER_TOOL, true);
        tool.setItemMeta(meta);
        return tool;
    }
}

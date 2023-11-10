package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnvGetter extends SuperItem implements SuperCraftable {

    public EnvGetter() {
        super(SuperItemType.ENV_TOOL);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.ENV_TOOL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Environment Getter").color(NamedTextColor.GREEN));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("This tool can be used to get the environment of a block.");
        lore.addInfoLore("Right click a block to get the environment.");
        meta.lore(lore.generateLore());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
    }
}

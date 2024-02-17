package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.inventory.meta.ItemMeta;

public class Glass extends SuperItem implements SuperCraftable {
    private double volume = 0;

    public Glass() {
        super(SuperItemType.GLASS);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        // idに数字が入っているのでそれを1000で割ってamountに代入
        this.volume = Double.parseDouble(id) / 1000;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        ItemMeta meta = stack.getItemMeta();
        PDCC.set(meta, PDCKey.GLASS_VOLUME, this.volume);
        stack.setItemMeta(meta);
        stack.setDisplayName(Component.text("グラス(" + String.format("%.1f", this.volume * 1000) + "ml)", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        return stack;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.volume = 500;
        return this.getSuperItem();
    }
}

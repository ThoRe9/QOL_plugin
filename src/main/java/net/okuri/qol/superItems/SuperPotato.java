package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SuperPotato extends SuperResource {
    public SuperPotato() {
        super(Component.text("SuperPotato").color(NamedTextColor.GREEN), "this is a super potato!", Material.POTATO, Material.POTATOES, SuperItemType.POTATO, 3);
    }

    @Override
    public ItemStack getSuperItem() {
        return super.getSuperItem();
    }

    // TODO 壊れてる
    @Override
    public ItemStack getDebugItem(int... args) {
        return super.getDebugItem(args);
    }
}

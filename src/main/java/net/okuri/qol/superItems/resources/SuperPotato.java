package net.okuri.qol.superItems.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class SuperPotato extends SuperResource {
    public SuperPotato() {
        super(Component.text("SuperPotato").color(NamedTextColor.GREEN), "this is a super potato!", Material.POTATO, Material.POTATOES, SuperItemType.POTATO, 3);
    }

    @Override
    public SuperItemStack getSuperItem() {
        return super.getSuperItem();
    }

    // TODO 壊れてる
    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(args);
    }
}

package net.okuri.qol.superItems.factory.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.Material;

public class SuperPotato extends SuperResource {
    public SuperPotato() {
        super(Component.text("SuperPotato").color(NamedTextColor.GREEN), "this is a super potato!", Material.POTATOES, SuperItemType.POTATO, 3);
    }

    @Override
    public SuperResourceStack getSuperItem() {
        return super.getSuperItem();
    }

    // TODO 壊れてる
    @Override
    public SuperResourceStack getDebugItem(int... args) {
        return super.getDebugItem(args);
    }
}

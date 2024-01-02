package net.okuri.qol.superItems.factory.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;

public class ResourceGetter extends SuperItem {
    public ResourceGetter() {
        super(SuperItemType.RESOURCE_GETTER);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.RESOURCE_GETTER);
        item.setDisplayName(Component.text("Resource Getter").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("You can get a resource instantly!");
        item.setLore(lore);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }
}

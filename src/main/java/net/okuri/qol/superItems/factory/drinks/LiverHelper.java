package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

public class LiverHelper extends SuperItem implements SuperCraftable {
    public LiverHelper() {
        super(SuperItemType.LIVER_HELPER);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(SuperItemType.LIVER_HELPER);
        item.setDisplayName(Component.text("Liver Helper").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setColor(Color.OLIVE);
        meta.setBasePotionData(new PotionData(org.bukkit.potion.PotionType.INSTANT_HEAL));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("I will help your liver.");
        lore.addInfo("You can drink more alcohol!");
        lore.addInfo("(Your alcLv will be reduced by 10%)");
        meta.lore(lore.generate());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return this.getSuperItem();
    }
}

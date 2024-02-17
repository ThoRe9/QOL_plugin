package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

public class BarleyJuice extends LiquorResource {
    public BarleyJuice() {
        super("大麦ジュース", Material.BEDROCK, SuperItemType.BARLEY_JUICE, 0.00, BarleyTaste.instance, 1150);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        meta.setColor(Color.OLIVE);
        stack.setItemMeta(meta);
        return stack;
    }
}

package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.GrapeSweetness;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

public class GrapeJuice extends LiquorResource {
    public GrapeJuice() {
        super("発酵用ぶどう", Material.BEDROCK, SuperItemType.GRAPE_JUICE, 0.00, GrapeSweetness.instance, 1112350);
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

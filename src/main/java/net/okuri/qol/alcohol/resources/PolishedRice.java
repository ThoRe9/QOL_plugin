package net.okuri.qol.alcohol.resources;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.taste.RiceSweetness;
import net.okuri.qol.alcohol.taste.RiceTaste;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class PolishedRice extends LiquorResource {
    private double ricePolishingRate;

    public PolishedRice() {
        super("Polished Rice", Material.BEDROCK, SuperItemType.POLISHED_RICE, 0, RiceTaste.instance, 120);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        ItemMeta meta = stack.getItemMeta();
        lore.setRicePolishingRate(this.ricePolishingRate);
        LoreGenerator gen = new LoreGenerator();
        gen.addLore(lore);
        gen.setLore(meta);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRate);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        super.setMatrix(matrix, id);
        if (id.equals("polished_rice")) {
            this.ricePolishingRate = 1;
        } else {
            this.ricePolishingRate = (double) PDCC.get(matrix[0].getItemMeta(), PDCKey.RICE_POLISHING_RATIO);
        }
        double recentRate = this.ricePolishingRate * 0.8;
        double decline = super.tastes.get(RiceTaste.instance) * 0.2;
        super.tastes.put(RiceTaste.instance, super.tastes.get(RiceTaste.instance) - decline);
        this.ricePolishingRate = recentRate;
        if (super.tastes.get(RiceSweetness.instance) != null) {
            super.tastes.put(RiceSweetness.instance, super.tastes.get(RiceSweetness.instance) + decline * 0.1);
        } else {
            super.tastes.put(RiceSweetness.instance, decline * 0.1);
        }
    }
}

package net.okuri.qol.alcohol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.loreGenerator.FermentationLore;
import net.okuri.qol.loreGenerator.LiquorIngredientLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.meta.ItemMeta;

public class FermentationIngredient extends LiquorIngredient {
    private SuperItemStack yeast;
    private double fermentationRate = 1.0;
    private double alcoholRate = 0.01;

    public FermentationIngredient() {
        super(SuperItemType.FERMENTATION_INGREDIENT);
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack stack = super.getSuperItem();
        stack.setDisplayName(Component.text("酵母入り Liquor Ingredient", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        ItemMeta meta = stack.getItemMeta();
        PDCC.set(meta, PDCKey.FERMENTATION_RATE, fermentationRate);
        PDCC.set(meta, PDCKey.FERMENTATION_ALC_RATE, alcoholRate);

        LiquorIngredientLore lore = new LiquorIngredientLore(super.getLiquorAmount(), super.getAlcoholAmount(), super.getDelicacy(), super.getFermentationDegree(), super.getEffectRate());
        for (Taste taste : super.getTastes().keySet()) {
            lore.addTaste(taste, super.getTastes().get(taste));
        }
        FermentationLore fLore = new FermentationLore(fermentationRate, alcoholRate);
        LoreGenerator gen = new LoreGenerator();
        gen.addLore(lore);
        gen.addLore(fLore);
        gen.setLore(meta);

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        for (SuperItemStack stack : matrix) {
            if (stack.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.YEAST))) {
                setYeast(stack);
            } else if (stack.getSuperItemData().isSimilar(new SuperItemData(SuperItemType.LIQUOR_INGREDIENT))) {
                super.setMatrix(new SuperItemStack[]{stack}, id);
            }
        }
    }

    private void setYeast(SuperItemStack yeast) {
        this.yeast = yeast;
        this.fermentationRate = (double) PDCC.get(yeast.getItemMeta(), PDCKey.FERMENTATION_RATE);
        this.alcoholRate = (double) PDCC.get(yeast.getItemMeta(), PDCKey.FERMENTATION_ALC_RATE);
    }
}

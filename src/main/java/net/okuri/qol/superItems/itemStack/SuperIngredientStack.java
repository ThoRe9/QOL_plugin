package net.okuri.qol.superItems.itemStack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SuperIngredientStack extends SuperXYZStack {
    public SuperIngredientStack(ItemStack stack) {
        super(stack);
    }

    public SuperIngredientStack(SuperItemStack stack) {
        super(stack);
    }

    public SuperIngredientStack(Material material, int amount, boolean hasTSC) {
        super(material, amount, hasTSC);
    }
}

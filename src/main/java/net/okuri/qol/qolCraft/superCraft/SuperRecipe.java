package net.okuri.qol.qolCraft.superCraft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface SuperRecipe extends Recipe {
    boolean checkSuperRecipe(ItemStack[] matrix);
    String getId();
}

package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;

public interface SuperRecipe extends Recipe {
    boolean checkSuperRecipe(SuperItemStack[] matrix);

    String getId();

    ArrayList<SuperItemStack> getReturnItems();
}

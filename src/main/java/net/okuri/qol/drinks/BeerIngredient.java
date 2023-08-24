package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class BeerIngredient extends SuperCraftable {
    protected ItemStack[] matrix;
    private double x;
    private double y;
    private double z;



    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        setting(matrix[1]);
    }
    @Override
    public ItemStack getSuperItem() {
        NamespacedKey drinkableKey = new NamespacedKey("qol", "qol_consumable");

        ItemStack result = new ItemStack(Material.POTION, 1);
        PotionMeta resultMeta = (PotionMeta) result.getItemMeta();
        resultMeta.displayName(Component.text("Beer Ingredient").color(NamedTextColor.GOLD));
        resultMeta.setColor(Color.WHITE);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Beer Ingredient");
        lore.addImportantLore("You can't drink this!");
        lore.addParametersLore("x", this.x*10);
        lore.addParametersLore("y", this.y*10);
        lore.addParametersLore("z", this.z*10);
        resultMeta.lore(lore.generateLore());

        resultMeta.getPersistentDataContainer().set(SuperWheat.xkey, PersistentDataType.DOUBLE, this.x);
        resultMeta.getPersistentDataContainer().set(SuperWheat.ykey, PersistentDataType.DOUBLE, this.y);
        resultMeta.getPersistentDataContainer().set(SuperWheat.zkey, PersistentDataType.DOUBLE, this.z);
        resultMeta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, SuperItemType.BEER_INGREDIENT.toString());
        // consumable = false
        resultMeta.getPersistentDataContainer().set(drinkableKey, PersistentDataType.BOOLEAN, false);
        result.setItemMeta(resultMeta);
        return result;
    }

    private void setting(ItemStack barley){
        this.x = barley.getItemMeta().getPersistentDataContainer().get(SuperWheat.xkey, PersistentDataType.DOUBLE);
        this.y = barley.getItemMeta().getPersistentDataContainer().get(SuperWheat.ykey, PersistentDataType.DOUBLE);
        this.z = barley.getItemMeta().getPersistentDataContainer().get(SuperWheat.zkey, PersistentDataType.DOUBLE);
    }

}

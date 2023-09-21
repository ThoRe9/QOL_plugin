package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

public class BeerIngredient implements SuperCraftable {
    protected ItemStack[] matrix;
    private double x;
    private double y;
    private double z;
    private double quality;
    private double rarity;
    private double temp;
    private double humid;

    @Override
    public void setMatrix(ItemStack[] matrix) {
        this.matrix = matrix;
        setting(matrix[1]);
    }
    @Override
    public ItemStack getSuperItem() {
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

        PDCC.setSuperItem(resultMeta, SuperItemType.BEER_INGREDIENT, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        // consumable = false
        PDCC.set(resultMeta, PDCKey.CONSUMABLE, false);
        result.setItemMeta(resultMeta);
        return result;
    }
    @Override
    public ItemStack getDebugItem(int... args) {
        this.x = 0.33;
        this.y = 0.33;
        this.z = 0.33;
        return this.getSuperItem();
    }

    private void setting(ItemStack barley){
        this.x = PDCC.get(barley, PDCKey.X);
        this.y = PDCC.get(barley, PDCKey.Y);
        this.z = PDCC.get(barley, PDCKey.Z);
        this.quality = PDCC.get(barley, PDCKey.QUALITY);
        this.rarity = PDCC.get(barley, PDCKey.RARITY);
        this.temp = PDCC.get(barley, PDCKey.TEMP);
        this.humid = PDCC.get(barley, PDCKey.HUMID);

    }

}

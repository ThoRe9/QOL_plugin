package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;

import net.okuri.qol.superItems.SuperItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class WhiskyWithIce extends SuperItem {
    private ItemStack whisky = null;


    @Override
    public ItemStack getSuperItem() {
        ItemStack WWI = new ItemStack(Material.POTION, 3);
        PotionMeta WWImeta = (PotionMeta) WWI.getItemMeta();
        PotionMeta Wmeta = (PotionMeta) this.whisky.getItemMeta();
        WWImeta.displayName(Component.text("Whisky with Ice").color(NamedTextColor.GOLD));
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addInfoLore("Whisky on the rocks!");
        WWImeta.lore(loreGenerator.generateLore());
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(0), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(1), true);
        WWImeta.addCustomEffect(Wmeta.getCustomEffects().get(2), true);
        WWImeta.setColor(Color.fromBGR(10, 70, 170));
        WWI.setItemMeta(WWImeta);

        return WWI;
    }

    public WhiskyWithIce(ItemStack whisky) {
        this.whisky = whisky;
    }
}

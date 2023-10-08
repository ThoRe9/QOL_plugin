package net.okuri.qol.superItems.foods;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Food {
    private int foodLevel;
    private Float foodSaturation;
    private Sound sound;

    public void whenEat(Player player, ItemStack food) {
        // ここに食べたときの処理を書く
        setParams(player.getInventory().getItemInMainHand());
        player.setFoodLevel(player.getFoodLevel() + foodLevel);
        player.setSaturation(player.getSaturation() + foodSaturation);
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    private void setParams(ItemStack food) {
        this.foodLevel = PDCC.get(food, PDCKey.FOOD_LEVEL);
        this.foodSaturation = PDCC.get(food, PDCKey.FOOD_SATURATION);
        this.sound = PDCC.get(food, PDCKey.FOOD_SOUND);

    }
}

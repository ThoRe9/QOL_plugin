package net.okuri.qol.listener;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemStack;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Food {
    private int foodLevel;
    private Float foodSaturation;
    private Sound sound;

    public void whenEat(Player player, SuperItemStack food) {
        // ここに食べたときの処理を書く
        setParams(new SuperItemStack(player.getInventory().getItemInMainHand()));
        player.setFoodLevel(player.getFoodLevel() + foodLevel);
        player.setSaturation(player.getSaturation() + foodSaturation);
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    private void setParams(SuperItemStack food) {
        this.foodLevel = PDCC.get(food, PDCKey.FOOD_LEVEL);
        this.foodSaturation = PDCC.get(food, PDCKey.FOOD_SATURATION);
        this.sound = PDCC.get(food, PDCKey.FOOD_SOUND);

    }
}

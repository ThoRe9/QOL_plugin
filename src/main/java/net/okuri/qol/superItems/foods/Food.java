package net.okuri.qol.superItems.foods;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Food {
    private int foodLevel;
    private Float foodSaturation;
    private Sound sound;
    public static NamespacedKey foodLevelKey = new NamespacedKey("qol", "food_level");
    public static NamespacedKey foodSaturationKey = new NamespacedKey("qol", "food_saturation");
    public static NamespacedKey FoodSoundKey = new NamespacedKey("qol", "food_sound");
    public static NamespacedKey eatableKey = new NamespacedKey("qol", "eatable");

    public void whenEat(Player player, ItemStack food){
        // ここに食べたときの処理を書く
        setParams(player.getInventory().getItemInMainHand());
        player.setFoodLevel(player.getFoodLevel() + foodLevel);
        player.setSaturation(player.getSaturation() + foodSaturation);
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }
    private void setParams(ItemStack food){
        this.foodLevel = food.getItemMeta().getPersistentDataContainer().get(foodLevelKey, PersistentDataType.INTEGER);
        this.foodSaturation = food.getItemMeta().getPersistentDataContainer().get(foodSaturationKey, PersistentDataType.FLOAT);
        this.sound = Sound.valueOf(food.getItemMeta().getPersistentDataContainer().get(FoodSoundKey, PersistentDataType.STRING));

    }
}

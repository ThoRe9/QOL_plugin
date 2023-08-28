package net.okuri.qol.drinks.maturation;

import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.block.Barrel;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Maturation {
    private LocalDateTime start;
    private Barrel barrel;
    private ItemStack ingredient;
    // ここにMaturation可能なアイテムを追加
    private static ArrayList<SuperItemType> maturationableItems = new ArrayList<>(Arrays.asList(
            SuperItemType.WHISKY_INGREDIENT,
            SuperItemType.BEER_INGREDIENT
    ));

    public Maturation(LocalDateTime start, Barrel barrel){
        this.start = start;
        this.barrel = barrel;
        this.ingredient = barrel.getInventory().getItem(0);
    }

    public ItemStack getResult(){
        ItemStack result = null;
        SuperItemType type = null;
        ItemMeta meta = ingredient.getItemMeta();
        if (meta.getPersistentDataContainer().has(SuperItemType.typeKey, PersistentDataType.STRING)){
            type = SuperItemType.valueOf(meta.getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING));
        }
        // ここにもMaturation可能なアイテムを追加
        switch (type){
            case WHISKY_INGREDIENT:
                result = getWhisky();
                break;
            case BEER_INGREDIENT:
                result = getBeer();
                break;
            default:
                break;
        }
        return result;

    }

    public static boolean isMaturationable(SuperItemType superItemType){
        return maturationableItems.contains(superItemType);
    }

    private ItemStack getWhisky(){
        Whisky whisky = new Whisky(this.ingredient, this.start);
        whisky.setTemperature(this.barrel.getWorld().getTemperature(this.barrel.getX(), this.barrel.getY(), this.barrel.getZ()));
        whisky.setHumidity(this.barrel.getWorld().getHumidity(this.barrel.getX(), this.barrel.getY(), this.barrel.getZ()));
        //Bukkit.getServer().getLogger().info(whisky.toString());
        return whisky.getSuperItem();
    }
    private ItemStack getBeer(){
        Beer beer = new Beer(this.ingredient, this.start);
        beer.setTemperature(this.barrel.getWorld().getTemperature(this.barrel.getX(), this.barrel.getY(), this.barrel.getZ()));
        Bukkit.getServer().getLogger().info(beer.toString());
        return beer.getSuperItem();
    }


}

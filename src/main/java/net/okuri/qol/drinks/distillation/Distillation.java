package net.okuri.qol.drinks.distillation;

import net.okuri.qol.drinks.Maturation.Whisky;
import net.okuri.qol.drinks.WhiskyIngredient;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Distillation {
    public static NamespacedKey distillationKey = new NamespacedKey("qol", "qol_distillation");
    private ArrayList<SuperItemType> distillationableItems = new ArrayList<>();
    private ItemStack ingredient;
    private Block furnace;
    public Distillation(ItemStack ingredient, Block furnace){
        this.ingredient = ingredient;
        this.furnace = furnace;
        // ここにDistillation可能なアイテムを追加
        this.distillationableItems.add(SuperItemType.WHISKY_INGREDIENT);
        this.distillationableItems.add(SuperItemType.UNDISTILLED_WHISKY_INGREDIENT);
    }
    public boolean isDistillationable(){
        PersistentDataContainer pdc = this.ingredient.getItemMeta().getPersistentDataContainer();
        if (!pdc.has(new NamespacedKey("qol","super_item_type"), PersistentDataType.STRING)){
            return false;
        }
        return this.distillationableItems.contains(SuperItemType.valueOf(pdc.get(new NamespacedKey("qol","super_item_type"), PersistentDataType.STRING)));
    }
    public void distillationEvent(){
        // TODO Distillationの処理
        Furnace fData = (Furnace) this.furnace.getState();
        //
    }

    public ItemStack getDistillationResult(){
        // TODO Distillationの結果を返す
        if (!this.isDistillationable()){
            return null;
        }
        switch (SuperItemType.valueOf(this.ingredient.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("qol","super_item_type"), PersistentDataType.STRING))){
            //ここに蒸留アイテムの処理を追加していく!
            case WHISKY_INGREDIENT:
            case UNDISTILLED_WHISKY_INGREDIENT:
                WhiskyIngredient wi = new WhiskyIngredient(this.ingredient);
                if (wi.distilled()){
                    return wi.getSuperItem();
                } else{
                    return this.ingredient;
                }
            default:
                return null;
        }
    }

}

package net.okuri.qol.superItems.factory.foods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.meta.ItemMeta;

public class Bread extends SuperItem implements SuperCraftable {
    private final SuperItemStack result = null;
    private SuperItemStack[] matrix = null;
    private final SuperItemType superItemType = SuperItemType.BREAD;
    private double par = 0.0;
    private int foodLevel = 5;
    private float foodSaturation = 6.0f;
    private final Sound sound = Sound.ENTITY_GENERIC_EAT;
    protected Component display ;
    protected PDCKey wheatkey;
    public static NamespacedKey parkey = new NamespacedKey("qol", "bread_par");

    public Bread(){
        super(SuperItemType.BREAD);
        this.display = Component.text("Bread").color(NamedTextColor.DARK_GREEN);
        this.wheatkey = PDCKey.Y;
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.matrix = matrix;
        // settingにmatrixの0~2をわたす
        setting(new SuperItemStack[]{matrix[3], matrix[4], matrix[5]});
    }

    private void setting(SuperItemStack[] wheats) {
        // wheatのyパラメータの平均値を引き継ぐ。
        this.foodLevel = 5;
        this.foodSaturation = 6.0f;
        double sum = 0;
        for (SuperItemStack wheat : wheats) {
            double p = PDCC.get(wheat, wheatkey);
            sum += p;
        }
        this.par = sum / wheats.length;
        this.foodLevel = (int) Math.floor(this.foodLevel * (1 + this.par));
        this.foodSaturation = (float) (this.foodSaturation * (1 + this.par));
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack bread = new SuperItemStack(this.getSuperItemType(), 1);
        ItemMeta meta = bread.getItemMeta();
        meta.displayName(display);
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("Bread!");
        lore.setFoodLevel(foodLevel);
        lore.setSaturation(foodSaturation);
        lore.addParametersLore("Parameter", par);
        meta.lore(lore.generate());
        PDCC.set(meta, PDCKey.X, this.par);
        PDCC.set(meta, PDCKey.TYPE, this.superItemType.toString());
        PDCC.set(meta, PDCKey.FOOD_LEVEL, this.foodLevel);
        PDCC.set(meta, PDCKey.FOOD_SATURATION, this.foodSaturation);
        PDCC.set(meta, PDCKey.FOOD_SOUND, this.sound.toString());
        PDCC.set(meta, PDCKey.EATABLE, true);
        bread.setItemMeta(meta);
        return bread;
    }
    @Override
    public SuperItemStack getDebugItem(int... args) {
        return getSuperItem();
    }
}

package net.okuri.qol.superItems.factory.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.factory.ingredient.Koji;
import net.okuri.qol.superItems.factory.ingredient.PolishedRice;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SakeIngredient extends SuperItem implements SuperCraftable {
    private SuperItemStack koji;
    private SuperItemStack rice;
    private SuperItemStack barley;
    private SuperItemStack potato;
    private double ricePolishingRatio;
    private final int maxDuration = 72000;
    private int resistanceAmp;
    private int resistanceDuration;
    private int fireResistAmp;
    private int fireResistDuration;
    private int regenAmp;
    private int regenDuration;
    private double x;
    private double y;
    private double z;
    private double smellRichness;
    private double tasteRichness;
    private double compatibility;
    private double quality;
    private int rarity;
    private double temp;
    private double humid;
    private SuperItemType ingredientType;

    public SakeIngredient() {
        super(SuperItemType.SAKE_INGREDIENT);
    }

    public SakeIngredient(SuperItemStack item) {
        super(SuperItemType.SAKE_INGREDIENT, item);
        this.x = PDCC.get(item.getItemMeta(), PDCKey.X);
        this.y = PDCC.get(item.getItemMeta(), PDCKey.Y);
        this.z = PDCC.get(item.getItemMeta(), PDCKey.Z);
        this.smellRichness = PDCC.get(item.getItemMeta(), PDCKey.SMELL_RICHNESS);
        this.tasteRichness = PDCC.get(item.getItemMeta(), PDCKey.TASTE_RICHNESS);
        this.compatibility = PDCC.get(item.getItemMeta(), PDCKey.COMPATIBILITY);
        this.quality = PDCC.get(item.getItemMeta(), PDCKey.QUALITY);
        this.temp = PDCC.get(item.getItemMeta(), PDCKey.TEMP);
        this.humid = PDCC.get(item.getItemMeta(), PDCKey.HUMID);
        this.ricePolishingRatio = PDCC.get(item.getItemMeta(), PDCKey.RICE_POLISHING_RATIO);
        this.ingredientType = SuperItemType.valueOf(PDCC.get(item.getItemMeta(), PDCKey.INGREDIENT_TYPE));

    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.koji = matrix[4];
        SuperItemStack ingredient = new SuperItemStack(matrix[1]);
        SuperItemType type = SuperItemType.valueOf(PDCC.get(ingredient.getItemMeta(), PDCKey.TYPE));
        if (type == SuperItemType.POLISHED_RICE) {
            this.rice = ingredient;
        } else if (type == SuperItemType.BARLEY) {
            this.barley = ingredient;
        } else if (type == SuperItemType.POTATO) {
            this.potato = ingredient;
        }
        this.ingredientType = type;
        setting();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(SuperItemType.SAKE_INGREDIENT);
        PotionMeta meta = (PotionMeta) result.getItemMeta();

        PDCC.setSuperItem(meta, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.TASTE_RICHNESS, this.tasteRichness);
        PDCC.set(meta, PDCKey.SMELL_RICHNESS, this.smellRichness);
        PDCC.set(meta, PDCKey.COMPATIBILITY, this.compatibility);
        PDCC.set(meta, PDCKey.INGREDIENT_TYPE, this.ingredientType.toString());
        PDCC.set(meta, PDCKey.CONSUMABLE, false);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);

        meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, this.resistanceDuration, this.resistanceAmp), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, this.fireResistDuration, this.fireResistAmp), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, this.regenDuration, this.regenAmp), true);

        meta.displayName(Component.text("Sake Ingredient").color(NamedTextColor.GOLD));
        meta.setColor(Color.WHITE);
        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("Ingredient for Sake");
        lore.addInfo("Ingredient Type : " + this.ingredientType.toString());
        lore.setParams(this.x, this.y, this.z);
        lore.setSubParams(this.tasteRichness, this.smellRichness, this.compatibility, this.quality);
        lore.setRarity(this.rarity);

        if (ingredientType == SuperItemType.POLISHED_RICE) {
            lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio);
        }
        meta.lore(lore.generate());
        meta.setCustomModelData(super.getSuperItemType().getCustomModelData());
        result.setItemMeta(meta);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.koji = new Koji().getDebugItem(args);
        this.rice = new PolishedRice().getDebugItem(args);
        this.ingredientType = SuperItemType.POLISHED_RICE;
        setting();
        return this.getSuperItem();
    }

    private void setting() {
        ItemMeta meta;
        ItemMeta kojiMeta = koji.getItemMeta();
        if (ingredientType == SuperItemType.POLISHED_RICE) {
            meta = rice.getItemMeta();
            double p = PDCC.get(kojiMeta, PDCKey.COMPATIBILITY);
            this.compatibility = -Math.pow(p, 2) + 2 * p;
            this.ricePolishingRatio = ((double) PDCC.get(kojiMeta, PDCKey.RICE_POLISHING_RATIO) + (double) PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO)) / 2.0;
        } else if (ingredientType == SuperItemType.BARLEY) {
            meta = barley.getItemMeta();
            double p = PDCC.get(kojiMeta, PDCKey.COMPATIBILITY);
            this.compatibility = 4 * (-Math.pow(p, 2) + p);
        } else if (ingredientType == SuperItemType.POTATO) {
            meta = potato.getItemMeta();
            double p = PDCC.get(kojiMeta, PDCKey.COMPATIBILITY);
            this.compatibility = -Math.pow(p, 2) + 1;
        } else {
            return;
        }

        this.smellRichness = PDCC.get(kojiMeta, PDCKey.COMPATIBILITY);
        this.tasteRichness = PDCC.get(kojiMeta, PDCKey.TASTE_RICHNESS);
        this.compatibility = PDCC.get(kojiMeta, PDCKey.COMPATIBILITY);
        this.quality = (double) PDCC.get(kojiMeta, PDCKey.QUALITY) * (double) PDCC.get(meta, PDCKey.QUALITY);

        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);

        this.x = (double) PDCC.get(meta, PDCKey.X) * this.compatibility * this.quality;
        this.y = (double) PDCC.get(meta, PDCKey.Y) * this.compatibility * this.quality;
        this.z = (double) PDCC.get(meta, PDCKey.Z) * this.compatibility * this.quality;
        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);

        this.resistanceAmp = (int) (this.x * this.tasteRichness * 2.5);
        this.resistanceDuration = (int) (this.x * this.smellRichness * this.maxDuration);
        this.fireResistAmp = (int) (this.y * this.tasteRichness * 2.5);
        this.fireResistDuration = (int) (this.y * this.smellRichness * this.maxDuration);
        this.regenAmp = (int) (this.z * this.tasteRichness * 2.5);
        this.regenDuration = (int) (this.z * this.smellRichness * this.maxDuration);
    }
}


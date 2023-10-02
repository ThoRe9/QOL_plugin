package net.okuri.qol.superItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.maturation.Maturable;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sake implements Maturable {
    private SuperItemType type = SuperItemType.SAKE;
    private ItemStack ingredient;
    private double ricePolishingRatio;
    private double days;
    private int registanceAmp;
    private int registanceDuration;
    private int fireResistAmp;
    private int fireResistDuration;
    private int regenAmp;
    private int regenDuration;
    private double x;
    private double y;
    private double z;
    private double smellRichness;
    private double tasteRichness;
    private double compatibilty;
    private double quality;
    private int rarity;
    private double temp;
    private double humid;
    private double amount;
    private double alcPer;
    private SakeType sakeType;
    private TasteType tasteType;
    private AlcType alcType;
    private final double maxAmount = 3000.0;
    private final int maxDuration = 72000;
    private SuperItemType ingredientType = SuperItemType.POLISHED_RICE;

    @Override
    public void setMaturationVariable(ArrayList<ItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        this.ingredient = ingredients.get(0);
        Duration dur = Duration.between(start,end);
        this.days = dur.toDays();
        this.temp = temp;
        this.humid = humid;
        setting();
    }

    @Override
    public ItemStack getSuperItem() {
        ItemStack result = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta)result.getItemMeta();
        meta.setColor(Color.WHITE);

        meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, this.registanceDuration, this.registanceAmp), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, this.fireResistDuration, this.fireResistAmp), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, this.regenDuration, this.regenAmp), true);

        PDCC.setSuperItem(meta, this.type, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.TASTE_RICHNESS, this.tasteRichness);
        PDCC.set(meta, PDCKey.SMELL_RICHNESS, this.smellRichness);
        PDCC.set(meta, PDCKey.COMPATIBILITY, this.compatibilty);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);
        PDCC.set(meta, PDCKey.QUALITY, this.quality);
        PDCC.set(meta, PDCKey.ALCOHOL_PERCENTAGE, this.alcPer);
        PDCC.set(meta, PDCKey.ALCOHOL, true);
        PDCC.set(meta, PDCKey.ALCOHOL_AMOUNT, this.amount);
        PDCC.set(meta, PDCKey.RARITY, this.rarity);
        PDCC.set(meta, PDCKey.INGREDIENT_TYPE, this.ingredientType.toString());
        PDCC.set(meta, PDCKey.CONSUMABLE, false);

        meta.displayName(Component.text(this.sakeType.kanji + AlcType.getStringType(this.alcPer, this.tasteRichness) +"Sake").color(NamedTextColor.GOLD));
        meta.setCustomModelData(this.type.getCustomModelData());

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Sake!! BIG BOTTLE!!");
        lore.addInfoLore(this.sakeType.kanji + " " + this.tasteType.kanji + " " + this.alcType.name);
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Taste Richness", this.tasteRichness);
        lore.addParametersLore("Smell Richness", this.smellRichness);
        lore.addParametersLore("Compatibility", this.compatibilty);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio, true);
        lore.addParametersLore("Maturation Days", this.days, true);
        lore.addParametersLore("Alcohol Percentage", this.alcPer, true);
        lore.addParametersLore("Amount", this.amount, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        this.ingredient = new SakeIngredient().getDebugItem(args);
        this.days = 1.0;
        this.temp = 0.0;
        this.humid = 0.0;
        setting();
        return getSuperItem();
    }

    private void setting(){
        ItemStack item = this.ingredient;
        PotionMeta meta = (PotionMeta)this.ingredient.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.tasteRichness = (double)PDCC.get(meta, PDCKey.TASTE_RICHNESS) * calcTasteMod(this.days);
        this.smellRichness = (double)PDCC.get(meta, PDCKey.SMELL_RICHNESS) * calcSmellMod(this.days);
        this.compatibilty = PDCC.get(meta, PDCKey.COMPATIBILITY);
        this.ricePolishingRatio = PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.alcPer = calcAlcPer(this.days);
        this.amount = this.maxAmount * calcAmountMod(this.days);
        this.sakeType = SakeType.getType(this.ricePolishingRatio);
        this.tasteType = TasteType.getType(this.tasteRichness, this.smellRichness);
        this.alcType = AlcType.getType(this.alcPer);

        this.registanceAmp = (int)(this.x * this.tasteRichness * 2.5);
        this.registanceDuration = (int)(this.x * this.smellRichness  * this.maxDuration);
        this.fireResistAmp = (int)(this.y * this.tasteRichness  * 2.5);
        this.fireResistDuration = (int)(this.y * this.smellRichness  * this.maxDuration);
        this.regenAmp = (int)(this.z * this.tasteRichness  * 2.5);
        this.regenDuration = (int)(this.z * this.smellRichness  * this.maxDuration);

        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);
    }
    private double calcAlcPer(double days){
        return 0.22 - 0.1/(days+0.5);
    }
    private double calcSmellMod(double days){
        return 2.0 - 20.0/(days+20.0);
    }
    private double calcAmountMod(double days){
        return 0.1 + 20.0/(days+22.0);
    }
    private double calcTasteMod(double days){
        return 0.7 + 10.0/(days+33);
    }
    public enum SakeType {
        NORMAL("NORMAL", 1, 0.60, "普通"),
        GINJO("GINJO", 0.60, 0.50, "吟醸"),
        DAIGINJO("DAIGINJO", 0.50, 0.0, "大吟醸");
        public final String name;
        public final double maxRicePolishingRatio;
        public final double minRicePolishingRatio;
        public final String kanji;
        SakeType(String name, double maxRicePolishingRatio, double minRicePolishingRatio, String kanji){
            this.name = name;
            this.maxRicePolishingRatio = maxRicePolishingRatio;
            this.minRicePolishingRatio = minRicePolishingRatio;
            this.kanji = kanji;
        }
        public static SakeType getType(double ricePolishingRatio){
            for (SakeType s : values()){
                if (s.minRicePolishingRatio <= ricePolishingRatio && s.maxRicePolishingRatio >= ricePolishingRatio){
                    return s;
                }
            }
            return null;
        }
    }
    public enum TasteType{
        KUNSHU("KUNSHU", "葷酒"),
        JUKUSHU("JUKUSHU", "熟酒"),
        SOUSHU("SOUSHU", "爽酒"),
        JUNSHU("JUNSHU", "醇酒");
        public final String name;
        public final String kanji;
        TasteType(String name, String kanji){
            this.name = name;
            this.kanji = kanji;
        }
        public static TasteType getType(double taste, double smell){
            TasteType ans = null;
            if ( taste > 0.5){
                if (smell > 0.5){
                    ans = JUKUSHU;
                } else{
                    ans = JUNSHU;
                }
            } else{
                if(smell > 0.5){
                    ans = KUNSHU;
                } else{
                    ans = SOUSHU;
                }
            }
            return ans;
        }
    }
    public enum AlcType {
        DRY("DRY"),
        SWEET("SWEET");
        public final String name;

        AlcType(String name) {
            this.name = name;
        }

        public static AlcType getType(double alc) {
            if (alc > 0.15) {
                return DRY;
            } else {
                return SWEET;
            }
        }

        public static String getStringType(double alc, double taste) {
            AlcType atype = getType(alc);
            String ans;
            if (taste > 0.5) {
                ans = "濃醇";
            } else {
                ans = "端麗";
            }
            if (atype == DRY) {
                ans = ans.concat("辛口");
            } else {
                ans = ans.concat("甘口");
            }
            return ans;
        }
    }

}

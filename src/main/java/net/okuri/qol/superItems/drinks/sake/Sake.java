package net.okuri.qol.superItems.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Sake extends SuperItem {
    protected int count = 1;
    protected SuperItemStack ingredient;
    protected double ricePolishingRatio = 1.0;
    protected double days;
    protected int resistanceAmp;
    protected int resistanceDuration;
    protected int fireResistAmp;
    protected int fireResistDuration;
    protected int regenAmp;
    protected int regenDuration;
    protected double x;
    protected double y;
    protected double z;
    protected double smellRichness;
    protected double tasteRichness;
    protected double compatibility;
    protected double quality;
    protected int rarity;
    protected double temp;
    protected double humid;
    protected double amount;
    protected double alcPer;
    protected SakeType sakeType;
    protected TasteType tasteType;
    protected AlcType alcType;
    protected double maxAmount;
    // 1mlあたりの持続時間(100mlあたり20minと計算)
    protected final int baseDuration = 20 * 60 * 10 / 100;
    protected double sumDuration;
    protected SuperItemType ingredientType;

    public Sake(SuperItemType type, SuperItemStack stack) {
        super(type, stack);
        // superItemType で得られるclassがSakeを継承していない場合はエラーを吐く
        if (!Sake.class.isAssignableFrom(SuperItemType.getSuperItemClass(stack.getSuperItemType()).getClass())) {
            throw new IllegalArgumentException("superItemType must be Sake or its subclass");
        }
        initialize(stack);
    }

    public Sake(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがSakeを継承していない場合はエラーを吐く
    }

    public Sake() {
        super(SuperItemType.SAKE_1GO);
    }
    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType(), this.count);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.setColor(Color.WHITE);

        if (this.resistanceDuration > 0) {
            meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, this.resistanceDuration, this.resistanceAmp), true);
        }
        if (this.fireResistDuration > 0) {
            meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, this.fireResistDuration, this.fireResistAmp), true);
        }
        if (this.regenDuration > 0) {
            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, this.regenDuration, this.regenAmp), true);
        }

        PDCC.setSuperItem(meta, this.x, this.y, this.z, this.quality, this.rarity, this.temp, this.humid);
        PDCC.set(meta, PDCKey.TASTE_RICHNESS, this.tasteRichness);
        PDCC.set(meta, PDCKey.SMELL_RICHNESS, this.smellRichness);
        PDCC.set(meta, PDCKey.COMPATIBILITY, this.compatibility);
        PDCC.set(meta, PDCKey.RICE_POLISHING_RATIO, this.ricePolishingRatio);
        PDCC.set(meta, PDCKey.QUALITY, this.quality);
        PDCC.set(meta, PDCKey.ALCOHOL_PERCENTAGE, this.alcPer);
        PDCC.set(meta, PDCKey.ALCOHOL, true);
        PDCC.set(meta, PDCKey.ALCOHOL_AMOUNT, this.amount);
        PDCC.set(meta, PDCKey.RARITY, this.rarity);
        PDCC.set(meta, PDCKey.INGREDIENT_TYPE, this.ingredientType.toString());
        PDCC.set(meta, PDCKey.CONSUMABLE, false);
        PDCC.set(meta, PDCKey.MATURATION, this.days);
        PDCC.set(meta, PDCKey.SAKE_TYPE, this.sakeType.name);
        PDCC.set(meta, PDCKey.SAKE_TASTE_TYPE, this.tasteType.name);
        PDCC.set(meta, PDCKey.SAKE_ALC_TYPE, this.alcType.name);


        meta.displayName(Component.text(this.sakeType.kanji + AlcType.getStringType(this.alcPer, this.tasteRichness) + "Sake").color(NamedTextColor.GOLD));

        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("JAPANESE Sake!!");
        lore.addInfoLore(this.sakeType.kanji + " " + this.tasteType.kanji + " " + this.alcType.name);
        lore.setSuperItemLore(this.x, this.y, this.z, this.quality, this.rarity);
        lore.addParametersLore("Taste Richness", this.tasteRichness);
        lore.addParametersLore("Smell Richness", this.smellRichness);
        lore.addParametersLore("Compatibility", this.compatibility);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio, true);
        lore.addParametersLore("Maturation Days", this.days, true);
        lore.addParametersLore("Alcohol Percentage", this.alcPer, true);
        lore.addParametersLore("Amount", this.amount, true);
        meta.lore(lore.generateLore());

        result.setItemMeta(meta);
        return result;
    }

    protected void initialize(SuperItemStack item) {
        ItemMeta meta = item.getItemMeta();
        this.ricePolishingRatio = PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO);
        this.tasteRichness = PDCC.get(meta, PDCKey.TASTE_RICHNESS);
        this.smellRichness = PDCC.get(meta, PDCKey.SMELL_RICHNESS);
        this.compatibility = PDCC.get(meta, PDCKey.COMPATIBILITY);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.alcPer = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);
        this.amount = PDCC.get(meta, PDCKey.ALCOHOL_AMOUNT);
        this.rarity = PDCC.get(meta, PDCKey.RARITY);
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.temp = PDCC.get(meta, PDCKey.TEMP);
        this.humid = PDCC.get(meta, PDCKey.HUMID);
        this.days = PDCC.get(meta, PDCKey.MATURATION);
        this.sakeType = SakeType.valueOf(PDCC.get(meta, PDCKey.SAKE_TYPE));
        this.tasteType = TasteType.valueOf(PDCC.get(meta, PDCKey.SAKE_TASTE_TYPE));
        this.alcType = AlcType.valueOf(PDCC.get(meta, PDCKey.SAKE_ALC_TYPE));
        this.ingredientType = SuperItemType.valueOf(PDCC.get(meta, PDCKey.INGREDIENT_TYPE));
        if (this.ingredientType != SuperItemType.POLISHED_RICE) {
            this.x = this.x * 0.01;
            this.y = this.y * 0.01;
            this.z = this.z * 0.01;
        }
        this.setting();

    }

    protected void initialize() {
        SuperItemStack item = this.ingredient;
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        this.x = PDCC.get(meta, PDCKey.X);
        this.y = PDCC.get(meta, PDCKey.Y);
        this.z = PDCC.get(meta, PDCKey.Z);
        this.tasteRichness = (double) PDCC.get(meta, PDCKey.TASTE_RICHNESS) * calcTasteMod(this.days);
        this.smellRichness = (double) PDCC.get(meta, PDCKey.SMELL_RICHNESS) * calcSmellMod(this.days);
        this.compatibility = PDCC.get(meta, PDCKey.COMPATIBILITY);
        this.ricePolishingRatio = PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO);
        this.quality = PDCC.get(meta, PDCKey.QUALITY);
        this.alcPer = calcAlcPer(this.days);
        this.amount = this.maxAmount * calcAmountMod(this.days);
        this.sakeType = SakeType.getType(this.ricePolishingRatio);
        this.tasteType = TasteType.getType(this.tasteRichness, this.smellRichness);
        this.alcType = AlcType.getType(this.alcPer);
        this.ingredientType = SuperItemType.valueOf(PDCC.get(meta, PDCKey.INGREDIENT_TYPE));
        if (this.ingredientType != SuperItemType.POLISHED_RICE) {
            this.x = this.x * 0.01;
            this.y = this.y * 0.01;
            this.z = this.z * 0.01;
        }
        setting();
        this.rarity = SuperItem.getRarity(this.x, this.y, this.z);
    }

    protected void setting() {
        this.sumDuration = this.amount * this.baseDuration;
        this.resistanceAmp = (int) (this.x * this.tasteRichness * 2.5);
        this.resistanceDuration = (int) (this.x * this.smellRichness * this.sumDuration);
        this.fireResistAmp = (int) (this.y * this.tasteRichness * 2.5);
        this.fireResistDuration = (int) (this.y * this.smellRichness * this.sumDuration);
        this.regenAmp = (int) (this.z * this.tasteRichness * 2.5);
        this.regenDuration = (int) (this.z * this.smellRichness * this.sumDuration);


    }

    private double calcAlcPer(double days) {
        return 0.22 - 0.1 / (days + 0.5);
    }

    private double calcSmellMod(double days) {
        return 2.0 - 20.0 / (days + 20.0);
    }

    private double calcAmountMod(double days) {
        return 0.1 + 20.0 / (days + 22.0);
    }

    private double calcTasteMod(double days) {
        return 0.7 + 10.0 / (days + 33);
    }

    public enum SakeType {
        NORMAL("NORMAL", 1, 0.60, "普通"),
        GINJO("GINJO", 0.60, 0.50, "吟醸"),
        DAIGINJO("DAIGINJO", 0.50, 0.0, "大吟醸");
        public final String name;
        public final double maxRicePolishingRatio;
        public final double minRicePolishingRatio;
        public final String kanji;

        SakeType(String name, double maxRicePolishingRatio, double minRicePolishingRatio, String kanji) {
            this.name = name;
            this.maxRicePolishingRatio = maxRicePolishingRatio;
            this.minRicePolishingRatio = minRicePolishingRatio;
            this.kanji = kanji;
        }

        public static SakeType getType(double ricePolishingRatio) {
            for (SakeType s : values()) {
                if (s.minRicePolishingRatio <= ricePolishingRatio && s.maxRicePolishingRatio >= ricePolishingRatio) {
                    return s;
                }
            }
            return null;
        }
    }

    public enum TasteType {
        KUNSHU("KUNSHU", "葷酒"),
        JUKUSHU("JUKUSHU", "熟酒"),
        SOUSHU("SOUSHU", "爽酒"),
        JUNSHU("JUNSHU", "醇酒");
        public final String name;
        public final String kanji;

        TasteType(String name, String kanji) {
            this.name = name;
            this.kanji = kanji;
        }

        public static TasteType getType(double taste, double smell) {
            TasteType ans;
            if (taste > 0.5) {
                if (smell > 0.5) {
                    ans = JUKUSHU;
                } else {
                    ans = JUNSHU;
                }
            } else {
                if (smell > 0.5) {
                    ans = KUNSHU;
                } else {
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

package net.okuri.qol.superItems.factory.drinks.sake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shochu extends Sake implements Distillable, Distributable {
    public Shochu() {
        super(SuperItemType.SHOCHU);
    }

    public Shochu(SuperItemType type, SuperItemStack stack) {
        super(type, stack);
        // superItemType で得られるclassがShochuを継承していない場合はエラーを吐く
        initialize(stack);
    }

    public Shochu(SuperItemType superItemType) {
        super(superItemType);
        // superItemType で得られるclassがShochuを継承していない場合はエラーを吐く
    }

    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
        super.ingredient = item;
        super.initialize();
        super.sakeType = null;
        super.tasteType = null;
        super.alcType = null;
        super.alcPer = calcAlcPer(1 - Math.abs(1 - Math.abs(temp)));
        double amountMod = Math.pow(Math.abs(1.0 - temp) * (1.0 - humid), 0.5);
        super.maxAmount = 1800.0;
        super.amount = super.maxAmount * amountMod;
        // パラメータの増減量の設定：
        // 増 : + 他パラメータから引いた分 * 0.9 * (1+compatibility)
        // 減 : - そのパラメータ * 0.2 * (1+compatibility)
        // ingredientTypeがpolished_riceの場合はx, potatoなら y, barleyなら zを増やす。
        // 以下処理
        double dx = 0.0;
        double dy = 0.0;
        double dz = 0.0;
        if (ingredientType == SuperItemType.POLISHED_RICE) {
            dy = -super.y * 0.2 * (1 + super.compatibility);
            dz = -super.z * 0.2 * (1 + super.compatibility);
            dx = -(dy + dz) * 0.9 * (1 + super.compatibility);
        } else if (ingredientType == SuperItemType.POTATO) {
            dx = -super.x * 0.2 * (1 + super.compatibility);
            dz = -super.z * 0.2 * (1 + super.compatibility);
            dy = -(dx + dz) * 0.9 * (1 + super.compatibility);
        } else if (ingredientType == SuperItemType.BARLEY) {
            dx = -super.x * 0.2 * (1 + super.compatibility);
            dy = -super.y * 0.2 * (1 + super.compatibility);
            dz = -(dx + dy) * 0.9 * (1 + super.compatibility);
        } else {
            throw new IllegalArgumentException("This item is not SakeIngredient");
        }
        super.x = super.x + dx;
        super.y = super.y + dy;
        super.z = super.z + dz;

        this.setting();
    }

    private double calcAlcPer(double var) {
        return 0.20 + var * 0.10;
    }

    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType(), this.count);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.setCustomModelData(this.getSuperItemType().getCustomModelData());
        meta.setColor(Color.WHITE);
        String superName = "";
        if (super.compatibility >= 0.9) superName = "本格";
        meta.displayName(Component.text(superName + this.getIngredientName() + "焼酎").color(NamedTextColor.GOLD));

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
        PDCC.set(meta, PDCKey.LIQUOR_AMOUNT, this.amount);
        PDCC.set(meta, PDCKey.RARITY, this.rarity);
        PDCC.set(meta, PDCKey.INGREDIENT_TYPE, this.ingredientType.toString());
        PDCC.set(meta, PDCKey.MATURATION, this.days);

        LoreGenerator lore = new LoreGenerator();
        lore.addInfo("JAPANESE Shochu!!");
        lore.addInfo("in a big bottle!!");
        lore.addInfo("Made from" + this.getIngredientName());
        lore.setParams(this.x, this.y, this.z);
        lore.setSubParams(this.tasteRichness, this.smellRichness, this.compatibility, this.quality);
        lore.setAlcParams(this.alcPer, this.amount);
        lore.setRarity(this.rarity);
        lore.setMaturationDays((int) this.days);
        lore.addParametersLore("Rice Polishing Ratio", this.ricePolishingRatio);
        meta.lore(lore.generate());

        result.setItemMeta(meta);
        return result;
    }

    protected String getIngredientName() {
        if (this.ingredientType == SuperItemType.POLISHED_RICE) {
            return "米";
        } else if (this.ingredientType == SuperItemType.POTATO) {
            return "芋";
        } else if (this.ingredientType == SuperItemType.BARLEY) {
            return "麦";
        } else {
            return "不明";
        }
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        setDistillationVariable(new SakeIngredient().getDebugItem(args), 0.0, 0.0);
        return getSuperItem();
    }

    @Override
    protected void setting() {
        super.setting();
        // ingredientTypeがpolished_riceの場合はresistance
        // ingredientTypeがpotatoの場合はfire_resistance
        // ingredientTypeがbarleyの場合はregeneration
        // 以外の効果を消す。以下処理
        if (super.ingredientType == SuperItemType.POLISHED_RICE) {
            super.fireResistAmp = 0;
            super.fireResistDuration = 0;
            super.regenAmp = 0;
            super.regenDuration = 0;
        } else if (super.ingredientType == SuperItemType.POTATO) {
            super.resistanceAmp = 0;
            super.resistanceDuration = 0;
            super.regenAmp = 0;
            super.regenDuration = 0;
        } else if (super.ingredientType == SuperItemType.BARLEY) {
            super.resistanceAmp = 0;
            super.resistanceDuration = 0;
            super.fireResistAmp = 0;
            super.fireResistDuration = 0;
        } else {
            throw new IllegalArgumentException("This item is not SakeIngredient");
        }
    }
    @Override
    public void initialize(SuperItemStack item) {
        ItemMeta meta = item.getItemMeta();
        super.ricePolishingRatio = PDCC.get(meta, PDCKey.RICE_POLISHING_RATIO);
        super.tasteRichness = PDCC.get(meta, PDCKey.TASTE_RICHNESS);
        super.smellRichness = PDCC.get(meta, PDCKey.SMELL_RICHNESS);
        super.compatibility = PDCC.get(meta, PDCKey.COMPATIBILITY);
        super.quality = PDCC.get(meta, PDCKey.QUALITY);
        super.alcPer = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);
        super.amount = PDCC.get(meta, PDCKey.LIQUOR_AMOUNT);
        super.rarity = PDCC.get(meta, PDCKey.RARITY);
        super.x = PDCC.get(meta, PDCKey.X);
        super.y = PDCC.get(meta, PDCKey.Y);
        super.z = PDCC.get(meta, PDCKey.Z);
        super.temp = PDCC.get(meta, PDCKey.TEMP);
        super.humid = PDCC.get(meta, PDCKey.HUMID);
        super.days = PDCC.get(meta, PDCKey.MATURATION);
        super.ingredientType = SuperItemType.valueOf(PDCC.get(meta, PDCKey.INGREDIENT_TYPE));
        this.setting();
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCount;
        return !(amount - decline < 0);
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {
        double amount = super.amount;
        double decline = smallBottleAmount * smallBottleCounts;
        super.amount = amount - decline;
        this.setting();
    }
    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        SuperItemStack bigBottle = matrix[0];
        this.initialize(bigBottle);
        this.setting();
    }

    @Override
    public void setAmount(SuperItemStack stack) {
        this.initialize(stack);
    }
}

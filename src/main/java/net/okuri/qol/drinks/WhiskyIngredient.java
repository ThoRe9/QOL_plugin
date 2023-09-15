package net.okuri.qol.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

// WhiskyIngredientは、Whiskyの材料となるアイテムです。これをMaturingBarrelに入れることで、Whiskyを作ることができます。
public class WhiskyIngredient extends SuperCraftable {

    public static NamespacedKey xKey = new NamespacedKey("qol", "qol_x");
    public static NamespacedKey yKey = new NamespacedKey("qol", "qol_y");
    public static NamespacedKey zKey = new NamespacedKey("qol", "qol_z");
    public static NamespacedKey divLineKey = new NamespacedKey("qol", "qol_divline");
    public static NamespacedKey qualityKey = new NamespacedKey("qol", "qol_quality");
    public static NamespacedKey rarityKey = new NamespacedKey("qol", "qol_rarity");
    public static NamespacedKey distilledKey = new NamespacedKey("qol", "qol_distilled");
    private SuperItemType superItemType = SuperItemType.WHISKY_INGREDIENT;
    private ItemStack itemStack = null;
    protected ItemStack[] matrix = null;
    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;
    private int hasteLevel = 0;
    private int hasteDuration = 0;
    private int speedLevel = 0;
    private int speedDuration = 0;
    private int nightVisionLevel = 0;
    private int nightVisionDuration = 0;
    private double durationAmplifier = 1.0;
    private double divLine = 0.0;
    private double quality = 0.0;
    private int distilled = 0;
    // rarityはmaxDurationからどれだけ離れているかを表す。1.0でmaxDurationと同じ、20.0でmaxDurationの2倍の効果時間。
    private double rarity = 0.0;
    private double alcPer = 0.10;
    // maxDurationは効果全ての効果時間の総和の基準値。超えたり超えなかったりする。
    private final int maxDuration = 6000;

    public WhiskyIngredient(){
    }

    public WhiskyIngredient(ItemStack whisky_ingredient){
        this.superItemType = SuperItemType.WHISKY_INGREDIENT;
        this.itemStack = whisky_ingredient;
        ItemMeta meta = whisky_ingredient.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) meta;
        this.hasteLevel = potionMeta.getCustomEffects().get(0).getAmplifier();
        this.hasteDuration = potionMeta.getCustomEffects().get(0).getDuration();
        this.speedLevel = potionMeta.getCustomEffects().get(1).getAmplifier();
        this.speedDuration = potionMeta.getCustomEffects().get(1).getDuration();
        this.nightVisionLevel = potionMeta.getCustomEffects().get(2).getAmplifier();
        this.nightVisionDuration = potionMeta.getCustomEffects().get(2).getDuration();
        this.x = potionMeta.getPersistentDataContainer().get(xKey, PersistentDataType.DOUBLE);
        this.y = potionMeta.getPersistentDataContainer().get(yKey, PersistentDataType.DOUBLE);
        this.z = potionMeta.getPersistentDataContainer().get(zKey, PersistentDataType.DOUBLE);
        this.divLine = potionMeta.getPersistentDataContainer().get(divLineKey, PersistentDataType.DOUBLE);
        this.quality = potionMeta.getPersistentDataContainer().get(qualityKey, PersistentDataType.DOUBLE);
        this.rarity = potionMeta.getPersistentDataContainer().get(rarityKey, PersistentDataType.DOUBLE);
        this.distilled = potionMeta.getPersistentDataContainer().get(distilledKey, PersistentDataType.INTEGER);
        setType();
    }
    public WhiskyIngredient(ItemStack[] matrix){
        setting(matrix[1],matrix[7]);
    }

    public WhiskyIngredient(ItemStack barley, ItemStack coal){
        setting(barley, coal);
    }

    public WhiskyIngredient(double x, double y, double z, double coalRarity, double quality) {
        this.superItemType = SuperItemType.UNDISTILLED_WHISKY_INGREDIENT;
        this.itemStack = new ItemStack(Material.POTION);
        this.quality = quality;
        this.divLine = 1.25 - coalRarity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.hasteLevel = this.calcLevel(x);
        this.hasteDuration = this.calcDuration(x);
        this.speedLevel = this.calcLevel(y);
        this.speedDuration = this.calcDuration(y);
        this.nightVisionLevel = this.calcLevel(z);
        this.nightVisionDuration = this.calcDuration(z);
        this.rarity = calcRarity(x,y,z, quality);
        setType();
    }

    @Override
    public void setMatrix(ItemStack[] matrix){
        this.matrix = matrix;
        setting(matrix[1], matrix[7]);
    }

    private void setting(ItemStack barley, ItemStack coal){
        this.superItemType = SuperItemType.UNDISTILLED_WHISKY_INGREDIENT;
        this.itemStack = new ItemStack(Material.POTION);
        this.distilled = 0;
        //barleyかチェック
        ItemMeta barleyMeta = barley.getItemMeta();
        ItemMeta coalMeta = coal.getItemMeta();
        PersistentDataContainer barleypdc = barleyMeta.getPersistentDataContainer();
        PersistentDataContainer coalpdc = coalMeta.getPersistentDataContainer();

        //以下フィールドに値をセット
        if (barleypdc.has(SuperItemType.typeKey, PersistentDataType.STRING)){
            if (!Objects.equals(barleypdc.get(SuperItemType.typeKey, PersistentDataType.STRING), SuperItemType.BARLEY.getStringType())){
                throw new IllegalArgumentException("barley is not barley");
            }

        } else{throw new IllegalArgumentException("barley is not barley");}
        if (coalpdc.has(SuperItemType.typeKey, PersistentDataType.STRING)){
            if (!Objects.equals(coalpdc.get(SuperItemType.typeKey, PersistentDataType.STRING), SuperItemType.COAL.getStringType())){
                throw new IllegalArgumentException("coal is not coal");
            }
        } else{throw new IllegalArgumentException("coal is not coal");}

        if (coalpdc.has(SuperCoal.raritykey, PersistentDataType.DOUBLE)){
            double rarity = coalpdc.get(SuperCoal.raritykey, PersistentDataType.DOUBLE);
            this.divLine = 1.25 - rarity;
        } else{throw new IllegalArgumentException("coal is not super");}

        if (coalpdc.has(SuperCoal.qualitykey, PersistentDataType.DOUBLE)){
            this.quality = coalpdc.get(SuperCoal.qualitykey, PersistentDataType.DOUBLE);
        } else{throw new IllegalArgumentException("coal is not super");}

        if (barleypdc.has(SuperWheat.xkey, PersistentDataType.DOUBLE)){
            double x = barleypdc.get(SuperWheat.xkey, PersistentDataType.DOUBLE);
            this.x = x;
            this.hasteLevel = this.calcLevel(x);
            this.hasteDuration = this.calcDuration(x);

            if (barleypdc.has(SuperWheat.ykey, PersistentDataType.DOUBLE)){
                double y = barleypdc.get(SuperWheat.ykey, PersistentDataType.DOUBLE);
                this.y = y;
                this.speedLevel = this.calcLevel(y);
                this.speedDuration = this.calcDuration(y);

                if (barleypdc.has(SuperWheat.zkey, PersistentDataType.DOUBLE)){
                    double z = barleypdc.get(SuperWheat.zkey, PersistentDataType.DOUBLE);
                    this.z = z;
                    this.nightVisionLevel = this.calcLevel(z);
                    this.nightVisionDuration = this.calcDuration(z);

                    this.rarity = calcRarity(x,y,z, this.quality);
                } else{throw new IllegalArgumentException("barley is not super");}

            } else{throw new IllegalArgumentException("barley is not super");}



        } else{throw new IllegalArgumentException("barley is not super");}
        setType();
    }

    @Override
    public ItemStack getSuperItem() {
        setType();
        NamespacedKey typeKey = SuperItemType.typeKey;
        NamespacedKey drinkableKey = new NamespacedKey("qol", "qol_consumable");
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        meta.setColor(org.bukkit.Color.fromRGB(255, 255, 255));

        meta.getPersistentDataContainer().set(drinkableKey, PersistentDataType.BOOLEAN, false);
        meta.getPersistentDataContainer().set(xKey, PersistentDataType.DOUBLE, this.x);
        meta.getPersistentDataContainer().set(yKey, PersistentDataType.DOUBLE, this.y);
        meta.getPersistentDataContainer().set(zKey, PersistentDataType.DOUBLE, this.z);
        meta.getPersistentDataContainer().set(divLineKey, PersistentDataType.DOUBLE, this.divLine);
        meta.getPersistentDataContainer().set(qualityKey, PersistentDataType.DOUBLE, this.quality);
        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.DOUBLE, this.rarity);
        meta.getPersistentDataContainer().set(distilledKey, PersistentDataType.INTEGER, this.distilled);
        meta.getPersistentDataContainer().set(Alcohol.alcPerKey, PersistentDataType.DOUBLE, this.alcPer);

        meta.displayName(Component.text("Whisky Ingredient").color(NamedTextColor.GOLD));
        LoreGenerator lore = new LoreGenerator();
        lore.addImportantLore("You cannot drink this!");
        lore.addParametersLore("X", this.x*10);
        lore.addParametersLore("Y", this.y*10);
        lore.addParametersLore("Z", this.z*10);
        lore.addParametersLore("D", this.divLine*10);
        lore.addParametersLore("Distilled", this.distilled);
        lore.addParametersLore("AlcoholLevel", this.alcPer, true);
        if (this.distilled == 0){
            lore.addImportantLore("You need to distill this!");
        }
        lore.addRarityLore((int)(this.rarity));
        meta.lore(lore.generateLore());

        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, this.superItemType.getStringType());
        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FAST_DIGGING, (int)Math.floor(this.hasteDuration / this.durationAmplifier), this.hasteLevel + this.distilled - 1), true);
        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, (int)Math.floor(this.speedDuration / this.durationAmplifier), this.speedLevel + this.distilled -1), true);
        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION, (int)Math.floor(this.nightVisionDuration / this.durationAmplifier), this.nightVisionLevel + this.distilled-1), true);
        this.itemStack.setItemMeta(meta);

        return this.itemStack;
    }
    @Override
    public ItemStack getDebugItem(int... args){
        this.x = 0.33;
        this.y = 0.33;
        this.z = 0.33;
        this.distilled = args[0];
        this.divLine = 10.0;
        this.quality = 1.0;
        this.rarity = 0.0;
        this.hasteLevel = 1;
        this.hasteDuration = 100;
        this.speedLevel = 1;
        this.speedDuration = 100;
        this.nightVisionLevel = 1;
        this.nightVisionDuration = 100;
        return this.getSuperItem();
    }



    public boolean distilled(){
        if (this.distilled >= 3){
            return false;
        }
        this.distilled += 1;
        this.x = this.x * 0.90;
        this.y = this.y * 0.90;
        this.z = this.z * 0.90;
        this.durationAmplifier = this.distilled * this.distilled;
        // distilledの値に応じてalcPerを変更
        switch (this.distilled){
            case 1:
                this.alcPer = 0.40;
                break;
            case 2:
                this.alcPer = 0.50;
                break;
            case 3:
                this.alcPer = 0.60;
                break;
        }

        return true;
    }

    private int calcLevel(double barley){
        return (int) Math.floor(barley / this.divLine + 1);
    }
    private int calcDuration(double barley){
        return (int) Math.floor((barley % this.divLine) * this.maxDuration * quality);

    }

    private double calcRarity(double x, double y, double z, double quality){
        return ((x+y+z)*quality - 1.0) * 10.0;
    }

    private void setType(){
        if (this.distilled > 0){
            this.superItemType = SuperItemType.WHISKY_INGREDIENT;
        } else{
            this.superItemType = SuperItemType.UNDISTILLED_WHISKY_INGREDIENT;
        }

    }
}

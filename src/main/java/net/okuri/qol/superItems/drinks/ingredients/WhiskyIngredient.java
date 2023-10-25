package net.okuri.qol.superItems.drinks.ingredients;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

// WhiskyIngredientは、Whiskyの材料となるアイテムです。これをMaturingBarrelに入れることで、Whiskyを作ることができます。
public class WhiskyIngredient extends SuperItem implements SuperCraftable, Distillable {
    protected SuperItemStack[] matrix = null;
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
    private int rarity = 0;
    private final double temp = 0.0;
    private final double humid = 0.0;
    private double alcPer = 0.10;
    private final double alcAmount = 5000.0;
    // maxDurationは効果全ての効果時間の総和の基準値。超えたり超えなかったりする。
    private final int maxDuration = 24000;

    public WhiskyIngredient(){
        super(SuperItemType.WHISKY_INGREDIENT);
    }
    public WhiskyIngredient(int distilled){
        super(SuperItemType.WHISKY_INGREDIENT);
        this.distilled = distilled;
    }

    public WhiskyIngredient(SuperItemStack whisky_ingredient) {
        super(SuperItemType.WHISKY_INGREDIENT, whisky_ingredient);
        setting(whisky_ingredient);
    }

    public WhiskyIngredient(SuperItemStack[] matrix) {
        super(SuperItemType.WHISKY_INGREDIENT);
        setting(matrix[1],matrix[7]);
    }

    public WhiskyIngredient(SuperItemStack barley, SuperItemStack coal) {
        super(SuperItemType.WHISKY_INGREDIENT);
        setting(barley, coal);
    }

    public WhiskyIngredient(double x, double y, double z, double coalRarity, double quality) {
        super(SuperItemType.WHISKY_INGREDIENT);
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
        this.rarity = SuperItem.getRarity(x,y,z);
        setType();
    }
    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.matrix = matrix;
        setting(matrix[1], matrix[7]);
    }
    @Override
    public SuperItemStack getSuperItem() {
        setType();
        SuperItemStack itemStack = new SuperItemStack(this.getSuperItemType());
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        meta.setColor(org.bukkit.Color.fromRGB(255, 255, 255));

        PDCC.set(meta, PDCKey.CONSUMABLE, false);
        PDCC.setLiquor(meta, this.alcAmount, this.alcPer, this.x, this.y, this.z, this.divLine, this.quality, this.rarity, this.temp, this.humid, 0);
        PDCC.set(meta, PDCKey.DISTILLATION, this.distilled);

        //　各Durationが負のとき、バグ予防
        if (this.hasteDuration < 0){
            this.hasteDuration = 0;
        }
        if (this.speedDuration < 0){
            this.speedDuration = 0;
        }
        if (this.nightVisionDuration < 0){
            this.nightVisionDuration = 0;
        }

        meta.displayName(Component.text("Whisky Ingredient").color(NamedTextColor.GOLD));
        LoreGenerator lore = new LoreGenerator();
        lore.addImportantLore("You cannot drink this!");
        lore.addParametersLore("X", this.x);
        lore.addParametersLore("Y", this.y);
        lore.addParametersLore("Z", this.z);
        lore.addParametersLore("D", this.divLine);
        lore.addParametersLore("Distilled", this.distilled);
        lore.addParametersLore("AlcoholLevel", this.alcPer, true);
        if (this.distilled == 0){
            lore.addImportantLore("You need to distill this!");
        }
        lore.addRarityLore(this.rarity);
        meta.lore(lore.generateLore());

        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FAST_DIGGING, (int)Math.floor(this.hasteDuration / this.durationAmplifier), this.hasteLevel + this.distilled - 1), true);
        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, (int)Math.floor(this.speedDuration / this.durationAmplifier), this.speedLevel + this.distilled -1), true);
        meta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION, (int)Math.floor(this.nightVisionDuration / this.durationAmplifier), this.nightVisionLevel + this.distilled-1), true);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.x = 0.33;
        this.y = 0.33;
        this.z = 0.33;
        this.divLine = 1.0;
        this.quality = 1.0;
        this.rarity = 0;
        this.hasteLevel = 1;
        this.hasteDuration = 100;
        this.speedLevel = 1;
        this.speedDuration = 100;
        this.nightVisionLevel = 1;
        this.nightVisionDuration = 100;
        return this.getSuperItem();
    }
    @Override
    public void setDistillationVariable(SuperItemStack item, double temp, double humid) {
        setting(item);
        if (this.distilled >= 3){
            return;
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
    }
    private void setting(ItemStack barley, ItemStack coal){
        this.distilled = 0;
        //barleyかチェック
        ItemMeta barleyMeta = barley.getItemMeta();
        ItemMeta coalMeta = coal.getItemMeta();

        SuperItemType barleyType = SuperItemType.valueOf(PDCC.get(barleyMeta, PDCKey.TYPE));
        SuperItemType coalType = SuperItemType.valueOf(PDCC.get(coalMeta, PDCKey.TYPE));
        Double coalRarity = PDCC.get(coalMeta, PDCKey.X);
        Double coalQuality = PDCC.get(coalMeta, PDCKey.QUALITY);
        Double barleyX = PDCC.get(barleyMeta, PDCKey.X);
        Double barleyY = PDCC.get(barleyMeta, PDCKey.Y);
        Double barleyZ = PDCC.get(barleyMeta, PDCKey.Z);

        if (barleyType != SuperItemType.BARLEY){
            throw new IllegalArgumentException("barley is not barley");
        }
        if (coalType != SuperItemType.COAL){
            throw new IllegalArgumentException("coal is not coal");
        }
        if (coalRarity == null){
            throw new IllegalArgumentException("coal is not super");
        }
        if (coalQuality == null){
            throw new IllegalArgumentException("coal is not super");
        }
        if (barleyX == null){
            throw new IllegalArgumentException("barley is not super");
        }
        if (barleyY == null){
            throw new IllegalArgumentException("barley is not super");
        }
        if (barleyZ == null){
            throw new IllegalArgumentException("barley is not super");
        }
        this.divLine = 1.25 - coalRarity;
        this.quality = coalQuality;
        this.x = barleyX;
        this.y = barleyY;
        this.z = barleyZ;
        this.hasteLevel = this.calcLevel(barleyX);
        this.hasteDuration = this.calcDuration(barleyX);
        this.speedLevel = this.calcLevel(barleyY);
        this.speedDuration = this.calcDuration(barleyY);
        this.nightVisionLevel = this.calcLevel(barleyZ);
        this.nightVisionDuration = this.calcDuration(barleyZ);
        this.rarity = SuperItem.getRarity(barleyX,barleyY,barleyZ);
        setType();
    }

    private void setting(SuperItemStack whisky_ingredient) {
        ItemMeta meta = whisky_ingredient.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) meta;
        this.distilled = PDCC.get(potionMeta, PDCKey.DISTILLATION);
        this.hasteLevel = potionMeta.getCustomEffects().get(0).getAmplifier() - this.distilled + 1;
        this.hasteDuration = potionMeta.getCustomEffects().get(0).getDuration();
        this.speedLevel = potionMeta.getCustomEffects().get(1).getAmplifier() - this.distilled + 1;
        this.speedDuration = potionMeta.getCustomEffects().get(1).getDuration();
        this.nightVisionLevel = potionMeta.getCustomEffects().get(2).getAmplifier() - this.distilled + 1;
        this.nightVisionDuration = potionMeta.getCustomEffects().get(2).getDuration();
        this.x = PDCC.get(potionMeta, PDCKey.X);
        this.y = PDCC.get(potionMeta, PDCKey.Y);
        this.z = PDCC.get(potionMeta, PDCKey.Z);
        this.divLine = PDCC.get(potionMeta, PDCKey.DIVLINE);
        this.quality = PDCC.get(potionMeta, PDCKey.QUALITY);
        this.rarity = PDCC.get(potionMeta, PDCKey.RARITY);
        setType();
    }
    private int calcLevel(double barley){
        return (int) Math.floor(barley / this.divLine + (this.distilled == 0 ? 1 : distilled) - 1);
    }
    private int calcDuration(double barley){
        return (int) Math.floor((barley % this.divLine) * this.maxDuration * quality);
    }
    private void setType(){
        if (this.distilled > 0){
            super.setSuperItemType(SuperItemType.WHISKY_INGREDIENT);
        } else{
            this.setSuperItemType(SuperItemType.UNDISTILLED_WHISKY_INGREDIENT);
        }
    }
}

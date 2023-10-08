package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Whisky implements Maturable, Distributable {
    private final SuperItemType superItemType = SuperItemType.WHISKY;
    private LocalDateTime start;
    private double x;
    private double y;
    private double z;
    private double divLine;
    private int days;
    private double temperature;
    private double humidity;
    private int rarity;
    private double quality;
    private double hasteLevel;
    private double hasteDuration;
    private double speedLevel;
    private double speedDuration;
    private double nightVisionLevel;
    private double nightVisionDuration;
    private double amountAmplifier;
    private double alcoholLevel;
    private double amplifier;
    private double alcoholPer = 0.40;
    private final double amount = 750.0;

    @Override
    public void setMaturationVariable(ArrayList<ItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        this.setFromWhiskyIngredient(ingredients.get(0));
        this.start = start;
        this.temperature = temp;
        this.humidity = humid;
        this.amplifier = this.getMaturationLevel(start, end);
    }
    @Override
    public ItemStack getSuperItem() {
        // パラメータ計算
        this.setAmplifier();
        this.hasteDuration = this.hasteDuration*this.amplifier*(1+Math.sin(2*Math.PI*this.temperature));
        this.speedDuration = this.speedDuration*this.amplifier*(1+Math.sin(2*Math.PI*(this.temperature+0.67)));
        this.nightVisionDuration = this.nightVisionDuration*this.amplifier*(1+Math.sin(2*Math.PI*(this.temperature+1.33)));

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

        // アイテム生成
        ItemStack whisky = new ItemStack(Material.POTION, 1);
        PotionMeta whiskyMeta = (PotionMeta) whisky.getItemMeta();
        whiskyMeta.setCustomModelData(this.superItemType.getCustomModelData());
        whiskyMeta.displayName(Component.text("Whisky").color(NamedTextColor.GOLD));
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addParametersLore("Age: ", this.days/70.0);
        loreGenerator.addImportantLore("VERY STRONG ORIGINAL WHISKY");
        loreGenerator.addImportantLore("TOO STRONG TO DRINK!!");
        loreGenerator.addParametersLore("Alcohol: ", this.alcoholPer, true);
        whiskyMeta.lore(loreGenerator.generateLore());

        // 日数に応じて色を変える
        whiskyMeta.setColor(Color.fromBGR(0, 80/(1+this.days), 255/(1+this.days)));

        // ポーション効果を付与
        whiskyMeta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (int) this.hasteDuration, (int) this.hasteLevel), true);
        whiskyMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, (int) this.speedDuration, (int) this.speedLevel), true);
        whiskyMeta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, (int) this.nightVisionDuration, (int) this.nightVisionLevel), true);

        // PersistentDataContainerにデータを保存
        // 仮で飲めるようにしておいた
        PDCC.set(whiskyMeta, PDCKey.CONSUMABLE, true);
        PDCC.setLiquor(whiskyMeta, this.superItemType, this.amount, this.alcoholPer, this.x, this.y, this.z, this.divLine, this.quality, this.rarity, this.temperature, this.humidity, this.days);
        whisky.setItemMeta(whiskyMeta);
        return whisky;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        this.start = LocalDateTime.now().minusDays(10);
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.divLine = 0.0;
        this.rarity = 1;
        this.quality = 1.0;
        this.temperature = 1.0;
        this.humidity = 1.0;
        this.hasteLevel = 1.0;
        this.hasteDuration = 100.0;
        this.speedLevel = 1.0;
        this.speedDuration = 100.0;
        this.nightVisionLevel = 1.0;
        this.nightVisionDuration = 100.0;
        return this.getSuperItem();
    }

    private double getMaturationLevel(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        double days = duration.toDays();
        this.days = (int) days;
        return Math.pow(days/7.0, 0.3-0.1*Math.cos(2*Math.PI*this.humidity));
    }

    private void setAmplifier(){
        this.amplifier = this.getMaturationLevel(this.start, LocalDateTime.now());
    }
    public Whisky() {}

    public Whisky(double hasteLevel, double hasteDuration, double speedLevel, double speedDuration, double nightVisionLevel, double nightVisionDuration) {
        this.hasteLevel = hasteLevel;
        this.hasteDuration = hasteDuration;
        this.speedLevel = speedLevel;
        this.speedDuration = speedDuration;
        this.nightVisionLevel = nightVisionLevel;
        this.nightVisionDuration = nightVisionDuration;
        this.amplifier = 1.0;
        this.amountAmplifier = 1.0;
        this.temperature = 0.0;
    }
    public Whisky(ItemStack whiskyIngredient, LocalDateTime start) {
        this.setFromWhiskyIngredient(whiskyIngredient);
        this.start = start;
    }
    private void setFromWhiskyIngredient(ItemStack whiskyIngredient){
        PotionMeta whiskyMeta = (PotionMeta) whiskyIngredient.getItemMeta();
        PersistentDataContainer pdc = whiskyMeta.getPersistentDataContainer();
        List<PotionEffect> effects = whiskyMeta.getCustomEffects();
        // effectsにFAST_DIGGING, SPEED, NIGHT_VISIONの効果が入っているか確認。入っていたらそれぞれのレベルと時間を取得
        for (PotionEffect effect : effects) {
            if (effect.getType().equals(PotionEffectType.FAST_DIGGING)) {
                this.hasteLevel = effect.getAmplifier();
                this.hasteDuration = effect.getDuration();
            } else if (effect.getType().equals(PotionEffectType.SPEED)) {
                this.speedLevel = effect.getAmplifier();
                this.speedDuration = effect.getDuration();
            } else if (effect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                this.nightVisionLevel = effect.getAmplifier();
                this.nightVisionDuration = effect.getDuration();
            }
        }
        // PersistentDataContainerからいろいろ取得
        this.alcoholPer = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.ALCOHOL_PERCENTAGE);
        this.x = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.X);
        this.y = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.Y);
        this.z = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.Z);
        this.divLine = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.DIVLINE);
        this.rarity = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.RARITY);
        this.quality = PDCC.get(whiskyIngredient.getItemMeta(), PDCKey.QUALITY);

    }


    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public boolean isDistributable(double smallBottleAmount, int smallBottleCount) {
        return false;
    }

    @Override
    public void distribute(double smallBottleAmount, int smallBottleCounts) {

    }

    @Override
    public SuperItemType getType() {
        return null;
    }

    @Override
    public void setMatrix(ItemStack[] matrix, String id) {

    }
}

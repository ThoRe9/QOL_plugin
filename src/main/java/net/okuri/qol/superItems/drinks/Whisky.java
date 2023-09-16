package net.okuri.qol.superItems.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperItem;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Whisky implements SuperItem {
    private final SuperItemType superItemType = SuperItemType.WHISKY;
    private ItemStack[] matrix;
    private LocalDateTime start;
    private int days;
    private double temperature;
    private double humidity;
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
    public static NamespacedKey daysKey = new NamespacedKey("qol", "qol_days");
    @Override
    public ItemStack getSuperItem() {
        // パラメータ計算
        this.setAmplifier();
        this.hasteDuration = this.hasteDuration*this.amplifier*(1+Math.sin(2*Math.PI*this.temperature));
        this.speedDuration = this.speedDuration*this.amplifier*(1+Math.sin(2*Math.PI*(this.temperature+0.67)));
        this.nightVisionDuration = this.nightVisionDuration*this.amplifier*(1+Math.sin(2*Math.PI*(this.temperature+1.33)));

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
        PersistentDataContainer pdc = whiskyMeta.getPersistentDataContainer();
        pdc.set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.getStringType());
        pdc.set(daysKey, PersistentDataType.INTEGER, this.days);
        pdc.set(new NamespacedKey("qol", "qol_consumable"), PersistentDataType.BOOLEAN, false);
        pdc.set(Alcohol.alcPerKey, PersistentDataType.DOUBLE, this.alcoholPer);
        whisky.setItemMeta(whiskyMeta);
        return whisky;
    }

    @Override
    public ItemStack getDebugItem(int... args) {
        this.start = LocalDateTime.now().minusDays(10);
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
        // PersistentDataContainerからalcohol_percentを取得
        this.alcoholPer = pdc.get(Alcohol.alcPerKey, PersistentDataType.DOUBLE);
        this.start = start;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}

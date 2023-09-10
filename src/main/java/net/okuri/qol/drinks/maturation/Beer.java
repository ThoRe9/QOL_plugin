package net.okuri.qol.drinks.maturation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.Alcohol;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Beer {
    private LocalDateTime start;
    private ItemStack ingredient;
    private double temperature;
    private SuperItemType superItemType;
    private double x;
    private double y;
    private double z;
    private int hasteLV;
    private int hasteDuration;
    private int speedLV;
    private int speedDuration;
    private int nightVisionLV;
    private int nightVisionDuration;
    private int maxDuration = 300;
    private double timeParam = 0.0;
    private double tempParam = 0.0;
    private double durationAmp = 0.0;
    private double divLine = 0.0;

    public Beer(){
    }
    public Beer(ItemStack ingredient, LocalDateTime start){
        this.ingredient = ingredient;
        this.x = ingredient.getItemMeta().getPersistentDataContainer().get(SuperWheat.xkey, PersistentDataType.DOUBLE);
        this.y = ingredient.getItemMeta().getPersistentDataContainer().get(SuperWheat.ykey, PersistentDataType.DOUBLE);
        this.z = ingredient.getItemMeta().getPersistentDataContainer().get(SuperWheat.zkey, PersistentDataType.DOUBLE);
        this.start = start;
    }

    public ItemStack getSuperItem(){
        ItemStack result = new ItemStack(Material.POTION, 3);
        PotionMeta meta =(PotionMeta) result.getItemMeta();
        LoreGenerator lore = new LoreGenerator();
        // パラメータ計算
        calcParam();
        meta.setCustomModelData(this.superItemType.getCustomModelData());
        // Ale Beer or Lager Beer
        if (this.superItemType == SuperItemType.ALE_BEER){
            meta.displayName(Component.text("Ale Beer").color(NamedTextColor.GOLD));
            meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, this.hasteDuration, this.hasteLV), true);
            meta.setColor(org.bukkit.Color.fromRGB(246, 245, 19));
            lore.addInfoLore("Bitter and quite ale Beer!!");
        } else {
            meta.displayName(Component.text("Lager Beer").color(NamedTextColor.GOLD));
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, this.speedDuration, this.speedLV), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, this.nightVisionDuration, this.nightVisionLV), true);
            meta.setColor(org.bukkit.Color.fromRGB(93, 52, 26));
            lore.addInfoLore("Heavy stouts lager Beer!!");
        }
        lore.addParametersLore("Age: ", this.timeParam);
        lore.addParametersLore("Alcohol: ", 0.05, true);
        lore.addParametersLore("Amount: ", 300.0, true);
        meta.lore(lore.generateLore());
        // PersistentDataContainerにデータを保存
        meta.getPersistentDataContainer().set(SuperItemType.typeKey, PersistentDataType.STRING, this.superItemType.toString());
        // debug
        meta.getPersistentDataContainer().set(SuperWheat.xkey, PersistentDataType.DOUBLE, this.x);
        meta.getPersistentDataContainer().set(SuperWheat.ykey, PersistentDataType.DOUBLE, this.y);
        meta.getPersistentDataContainer().set(SuperWheat.zkey, PersistentDataType.DOUBLE, this.z);
        meta.getPersistentDataContainer().set(new NamespacedKey("qol", "divline"), PersistentDataType.DOUBLE, this.divLine);
        meta.getPersistentDataContainer().set(new NamespacedKey("qol", "durationamp"), PersistentDataType.DOUBLE, this.durationAmp);
        meta.getPersistentDataContainer().set(new NamespacedKey("qol", "timeparam"), PersistentDataType.DOUBLE, this.timeParam);
        meta.getPersistentDataContainer().set(new NamespacedKey("qol", "tempparam"), PersistentDataType.DOUBLE, this.tempParam);
        meta.getPersistentDataContainer().set(Alcohol.alcPerKey, PersistentDataType.DOUBLE, 0.05);
        meta.getPersistentDataContainer().set(Alcohol.alcAmountKey, PersistentDataType.DOUBLE, 300.0);
        meta.getPersistentDataContainer().set(Alcohol.alcKey, PersistentDataType.BOOLEAN, true);
        result.setItemMeta(meta);
        return result;
    }

    private void calcParam(){
        // 温度によって種類を変える
        double tempLine = 0.0;
        double dayLine = 0.0;
        if (this.temperature < 0.5) {
            tempLine = 0.25;
            dayLine = 7;
            this.superItemType = SuperItemType.LAGER_BEER;
            this.maxDuration = 6000;
        } else {
            tempLine = 0.75;
            dayLine = 1;
            this.superItemType = SuperItemType.ALE_BEER;
            this.maxDuration = 12000;
        }
        //temp系
        this.tempParam = -10*Math.pow(this.temperature-tempLine,2) + 1.2;
        //time系
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(this.start, now);
        double days = duration.toDays();
        this.timeParam = days/dayLine;
        this.durationAmp = 1.0/(1.0+Math.exp(-10*(this.timeParam-0.5)));
        this.divLine = Math.pow(2,0.5*(1-this.timeParam));
        // xyzに反映
        this.x = this.x * this.tempParam;
        this.y = this.y * this.tempParam;
        this.z = this.z * this.tempParam;
        calcEffect();
    }

    private void calcEffect(){
        // ポーション効果を付与
        this.hasteLV = (int) (this.x / this.divLine);
        this.hasteDuration = (int) ((this.x % this.divLine)*this.maxDuration*this.durationAmp);
        this.speedLV = (int) (this.y / this.divLine);
        this.speedDuration = (int) ((this.y % this.divLine)*this.maxDuration*this.durationAmp);
        this.nightVisionLV = (int) (this.z / this.divLine);
        this.nightVisionDuration = (int) ((this.z % this.divLine)*this.maxDuration*this.durationAmp);
    }

    public void setTemperature(double temperature){
        this.temperature = temperature;
    }

}

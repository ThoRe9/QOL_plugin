package net.okuri.qol.superItems.factory.drinks.whisky;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Beer extends SuperItem implements Maturable {
    private LocalDateTime start;
    private SuperItemStack ingredient;
    private double temperature;
    private double humid;
    private double x;
    private double y;
    private double z;
    private int days;
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
    public Beer() {
        super(SuperItemType.BEER);
    }

    public Beer(SuperItemType type) {
        super(type);
        // typeで得られるclassが、このクラスのclassを継承しているか確認
        if (!this.getClass().isAssignableFrom(SuperItemType.getSuperItemClass(type).getClass())) {
            throw new IllegalArgumentException("type is not Beer");
        }
    }

    public Beer(SuperItemType type, SuperItemStack ingredient, LocalDateTime start) {
        super(type);
        // typeで得られるclassが、このクラスのclassを継承しているか確認
        if (!this.getClass().isAssignableFrom(SuperItemType.getSuperItemClass(type).getClass())) {
            throw new IllegalArgumentException("type is not Beer");
        }
        this.ingredient = ingredient;
        this.x = PDCC.get(ingredient.getItemMeta(), PDCKey.X);
        this.y = PDCC.get(ingredient.getItemMeta(), PDCKey.Y);
        this.z = PDCC.get(ingredient.getItemMeta(), PDCKey.Z);
        this.start = start;
    }

    public Beer(SuperItemType type, SuperItemStack beer) {
        super(type, beer);
        // typeで得られるclassが、このクラスのclassを継承しているか確認
        if (!this.getClass().isAssignableFrom(SuperItemType.getSuperItemClass(beer.getSuperItemType()).getClass())) {
            throw new IllegalArgumentException("type is not Beer");
        }
        this.x = PDCC.get(beer.getItemMeta(), PDCKey.X);
        this.y = PDCC.get(beer.getItemMeta(), PDCKey.Y);
        this.z = PDCC.get(beer.getItemMeta(), PDCKey.Z);
        this.start = PDCC.get(beer.getItemMeta(), PDCKey.MATURATION_START);
        this.days = PDCC.get(beer.getItemMeta(), PDCKey.MATURATION);
        this.temperature = PDCC.get(beer.getItemMeta(), PDCKey.TEMP);
        this.humid = PDCC.get(beer.getItemMeta(), PDCKey.HUMID);
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        this.ingredient = ingredients.get(0);
        this.x = PDCC.get(ingredient.getItemMeta(), PDCKey.X);
        this.y = PDCC.get(ingredient.getItemMeta(), PDCKey.Y);
        this.z = PDCC.get(ingredient.getItemMeta(), PDCKey.Z);
        this.start = start;
        this.temperature = temp;
        this.humid = humid;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType(), 3);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        LoreGenerator lore = new LoreGenerator();
        // パラメータ計算
        calcParam();
        //　各Durationが負のとき、バグ予防
        if (this.hasteDuration < 0) {
            this.hasteDuration = 0;
        }
        if (this.speedDuration < 0) {
            this.speedDuration = 0;
        }
        if (this.nightVisionDuration < 0) {
            this.nightVisionDuration = 0;
        }

        // Ale Beer or Lager Beer
        if (this.getSuperItemType() == SuperItemType.ALE_BEER) {
            meta.displayName(Component.text("Ale Beer").color(NamedTextColor.GOLD));
            meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, this.hasteDuration, this.hasteLV), true);
            meta.setColor(org.bukkit.Color.fromRGB(246, 245, 19));
            lore.addInfoLore("Bitter and quite ale Beer!!");
        } else if (this.getSuperItemType() == SuperItemType.LAGER_BEER) {
            meta.displayName(Component.text("Lager Beer").color(NamedTextColor.GOLD));
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, this.speedDuration, this.speedLV), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, this.nightVisionDuration, this.nightVisionLV), true);
            meta.setColor(org.bukkit.Color.fromRGB(93, 52, 26));
            lore.addInfoLore("Heavy stouts lager Beer!!");
        } else {
            meta.displayName(Component.text("Beer").color(NamedTextColor.GOLD));
            lore.addInfoLore("Beer!!");
        }
        lore.addParametersLore("Age: ", this.timeParam);
        lore.addParametersLore("Alcohol: ", 0.05, true);
        lore.addParametersLore("Amount: ", 300.0, true);
        meta.lore(lore.generateLore());
        // PersistentDataContainerにデータを保存
        PDCC.setLiquor(meta, 300.0, 0.05, this.x, this.y, this.z, this.divLine, 1.0, 2, this.temperature, this.humid, this.days);
        PDCC.set(meta, PDCKey.MATURATION_START, this.start);
        PDCC.set(meta, PDCKey.MATURATION_END, LocalDateTime.now());
        PDCC.set(meta, PDCKey.MATURATION, Duration.between(this.start, LocalDateTime.now()).toDays());
        result.setItemMeta(meta);
        return result;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.divLine = 1.0;
        this.durationAmp = 1.0;
        this.timeParam = 0.0;
        this.tempParam = 0.0;
        if (args[0] == 0) {
            // Ale Beer
            this.temperature = 0.25;
        } else {
            // Lager Beer
            this.temperature = 0.75;
        }
        this.start = LocalDateTime.now();
        return this.getSuperItem();
    }

    private void calcParam() {
        // 温度によって種類を変える
        double tempLine = 0.0;
        double dayLine = 0.0;
        if (this.temperature < 0.5) {
            tempLine = 0.25;
            dayLine = 7;
            this.setSuperItemType(SuperItemType.ALE_BEER);
            this.maxDuration = 6000;
        } else {
            tempLine = 0.75;
            dayLine = 1;
            this.setSuperItemType(SuperItemType.LAGER_BEER);
            this.maxDuration = 12000;
        }
        //temp系
        this.tempParam = -10 * Math.pow(this.temperature - tempLine, 2) + 1.2;
        //time系
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(this.start, now);
        double days = duration.toDays();
        this.days = (int) days;
        this.timeParam = days / dayLine;
        this.durationAmp = 1.0 / (1.0 + Math.exp(-10 * (this.timeParam - 0.5)));
        this.divLine = Math.pow(2, 0.5 * (1 - this.timeParam));
        // xyzに反映
        this.x = this.x * this.tempParam;
        this.y = this.y * this.tempParam;
        this.z = this.z * this.tempParam;
        calcEffect();
    }

    private void calcEffect() {
        // ポーション効果を付与
        this.hasteLV = (int) (this.x / this.divLine);
        this.hasteDuration = (int) ((this.x % this.divLine) * this.maxDuration * this.durationAmp);
        this.speedLV = (int) (this.y / this.divLine);
        this.speedDuration = (int) ((this.y % this.divLine) * this.maxDuration * this.durationAmp);
        this.nightVisionLV = (int) (this.z / this.divLine);
        this.nightVisionDuration = (int) ((this.z % this.divLine) * this.maxDuration * this.durationAmp);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}

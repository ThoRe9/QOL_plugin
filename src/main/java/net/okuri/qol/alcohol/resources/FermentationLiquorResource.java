package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FermentationLiquorResource extends LiquorResource implements Maturable {
    private final Map<Taste, Double> resourceTaste = new HashMap<>();
    private final Map<Taste, Double> productTaste = new HashMap<>();

    /**
     * 酒類の原料となるアイテムを生成するためのベースとなるクラスです。
     *
     * @param name          表示名
     * @param blockMaterial ブロックのマテリアル
     * @param superItemType スーパーアイテムタイプ
     * @param probability   生成確率(0.0~1.0)
     * @param baseTaste     基本の味
     * @param seed
     */
    public FermentationLiquorResource(String name, Material blockMaterial, SuperItemType superItemType, double probability, Taste baseTaste, int seed) {
        super(name, blockMaterial, superItemType, probability, baseTaste, seed);
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        super.setMatrix(new SuperItemStack[]{ingredients.get(0)}, "fermentation");
        double days = Duration.between(start, end).toDays();
        double reduced = 0;
        for (Map.Entry<Taste, Double> entry : this.resourceTaste.entrySet()) {
            Taste key = entry.getKey();
            if (super.tastes.get(key) != null) {
                double d = super.tastes.get(key) * growFunc(super.tastes.get(key), entry.getValue());
                super.tastes.put(key, super.tastes.get(key) - d);
                reduced += d;
            }
        }
        for (Map.Entry<Taste, Double> entry : this.productTaste.entrySet()) {
            Taste key = entry.getKey();
            if (super.tastes.get(key) != null) {
                super.tastes.put(key, super.tastes.get(key) + reduced * entry.getValue());
            } else {
                super.tastes.put(key, reduced * entry.getValue());
            }
        }
    }

    /**
     * a : 無限の時の値
     * b : 1になるx
     * c : 速度
     */
    private double growFunc(double x, double b) {
        double a = 2;
        double c = 0.7;
        return ((((-1) * a * b * (a - 1)) / (Math.pow(x, c) + b * (a - 1))) + a) * 0.5;
    }

    /**
     * Maturationすると減るTasteの値を追加します
     *
     * @param taste Tasteの種類
     * @param rate  0.5になる日数
     */
    public void addResourceTaste(Taste taste, double rate) {
        this.resourceTaste.put(taste, rate);
    }

    /**
     * 減ったTasteに対応して増えるTasteを指定します
     *
     * @param taste Tasteの種類
     * @param rate  Tasteが1減った時にいくら増えるか
     */
    public void addProductTaste(Taste taste, double rate) {
        this.productTaste.put(taste, rate);
    }
}

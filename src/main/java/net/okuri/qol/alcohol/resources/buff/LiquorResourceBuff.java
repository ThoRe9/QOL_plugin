package net.okuri.qol.alcohol.resources.buff;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.okuri.qol.alcohol.resources.LiquorResource;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class LiquorResourceBuff {
    private final String name;
    private final TextColor color;
    private final ArrayList<Integer> biomeList = new ArrayList<>();
    private final ArrayList<SuperItemType> resourceList = new ArrayList<>();
    // 以下バフ内容を表すフィールド
    private final Taste taste;
    private final double tasteRate;
    private Component header = Component.text("");
    // 以下基準を表すフィールド
    private int maxY = 10000;
    private int minY = -10000;
    private double maxTemp = 10;
    private double minTemp = -10;
    private double maxHumid = 10;
    private double minHumid = -10;
    private double bestTemp;
    private double bestHumid;
    // 以下計算結果を表すフィールド

    public LiquorResourceBuff(String name, TextColor color, Taste taste, double tasteRate) {
        this.name = name;
        this.color = color;
        this.taste = taste;
        this.tasteRate = tasteRate;
    }

    public boolean check(SuperItemType type, int posX, int posY, int posZ, double temp, double humid, int biomeId, Player producer) {
        if (posY > maxY) {
            return false;
        }
        if (posY < minY) {
            return false;
        }
        if (temp > maxTemp) {
            return false;
        }
        if (temp < minTemp) {
            return false;
        }
        if (humid > maxHumid) {
            return false;
        }
        if (humid < minHumid) {
            return false;
        }
        if (!biomeList.isEmpty()) {
            if (!biomeList.contains(biomeId)) {
                return false;
            }
        }
        if (!resourceList.isEmpty()) {
            if (!resourceList.contains(type)) {
                return false;
            }
        }
        return true;
    }

    public void setBuff(LiquorResource resource) {
        double value = 0;
        for (Map.Entry<Taste, Double> entry : resource.getTastes().entrySet()) {
            value += entry.getValue() * this.tasteRate;
        }
        resource.getTastes().put(this.taste, value);
        resource.addHeader(this.header);
    }

    public void setHeader(Component header) {
        this.header = header;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxHumid(double maxHumid) {
        this.maxHumid = maxHumid;
    }

    public void setMinHumid(double minHumid) {
        this.minHumid = minHumid;
    }
}

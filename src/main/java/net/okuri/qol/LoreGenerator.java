package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class LoreGenerator {
    private final TextColor baseColor = NamedTextColor.GRAY;
    private final TextColor subColor = NamedTextColor.DARK_GREEN;
    private final TextColor aColor = NamedTextColor.RED;
    private final TextColor bColor = NamedTextColor.AQUA;
    private final TextColor cColor = NamedTextColor.YELLOW;
    private final TextColor dColor = NamedTextColor.GREEN;
    private final TextColor importantColor = NamedTextColor.DARK_RED;

    private final ArrayList<String> lore = new ArrayList<>();
    private final ArrayList<String> importantLore = new ArrayList<>();
    private final ArrayList<String> parametersLabel = new ArrayList<>();
    private final ArrayList<Double> parametersValue = new ArrayList<>();
    private double x;
    private double y;
    private double z;
    private double taste;
    private double smell;
    private double compatibility;
    private double quality;
    private double alcLV;
    private double alcAmount;
    private int rarity;
    private int maturationDays;
    private float saturation;
    private int foodLevel;

    public ArrayList<Component> generate() {
        ArrayList<Component> components = new ArrayList<>();
        if (x != 0 || y != 0 || z != 0) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Parameters").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("============").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (x != 0) {
            components.add(Component.text("X").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (x * 100))).color(aColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (y != 0) {
            components.add(Component.text("Y").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (y * 100))).color(bColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (z != 0) {
            components.add(Component.text("Z").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (z * 100))).color(cColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (taste != 0 || smell != 0 || compatibility != 0 || quality != 0) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("SubParameters").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (taste != 0) {
            components.add(Component.text("Taste       ").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (taste * 100))).color(aColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (smell != 0) {
            components.add(Component.text("Smell       ").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (smell * 100))).color(bColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (compatibility != 0) {
            components.add(Component.text("Compatibility").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (compatibility * 100))).color(cColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (quality != 0) {
            components.add(Component.text("Quality     ").color(dColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(getParameterMeter((int) (quality * 10))).color(dColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (alcLV != 0 || alcAmount != 0 || maturationDays != 0) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Alcohol").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (alcLV != 0) {
            components.add(Component.text("Alcohol Level ").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text((int) (alcLV * 100)).color(aColor))).decoration(TextDecoration.ITALIC, false).append(Component.text("%").color(baseColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (alcAmount != 0) {
            components.add(Component.text("Alcohol Amount ").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text((int) alcAmount).color(bColor))).decoration(TextDecoration.ITALIC, false).append(Component.text("ml").color(baseColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (maturationDays != 0) {
            components.add(Component.text("Maturation Days").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(maturationDays).color(cColor))).decoration(TextDecoration.ITALIC, false).append(Component.text("days").color(baseColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (saturation != 0 || foodLevel != 0) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Food").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==================").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (foodLevel != 0) {
            components.add(Component.text("Food Level").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(foodLevel).color(bColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (saturation != 0) {
            components.add(Component.text("Saturation").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(saturation).color(aColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (rarity != 0 || !lore.isEmpty() || !importantLore.isEmpty() || !parametersLabel.isEmpty() || !parametersValue.isEmpty()) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Other").color(baseColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (rarity != 0) {
            components.add(Component.text("Rarity").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(StringUtils.repeat("★", rarity)).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))));
        }
        if (!parametersLabel.isEmpty() && !parametersValue.isEmpty()) {
            Iterator<String> labelIterator = parametersLabel.iterator();
            Iterator<Double> valueIterator = parametersValue.iterator();
            while (labelIterator.hasNext() && valueIterator.hasNext()) {
                String label = labelIterator.next();
                double value = valueIterator.next();
                components.add(Component.text(label).color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text(value).color(baseColor).decoration(TextDecoration.ITALIC, false))));
            }
        }
        if (!lore.isEmpty()) {
            Iterator<String> iterator = lore.iterator();
            while (iterator.hasNext()) {
                components.add(Component.text(iterator.next()).color(baseColor).decoration(TextDecoration.ITALIC, false));
            }
        }
        if (!importantLore.isEmpty()) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Important").color(importantColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
            Iterator<String> iterator = importantLore.iterator();
            while (iterator.hasNext()) {
                components.add(Component.text(iterator.next()).color(importantColor).decoration(TextDecoration.ITALIC, false));
            }
        }
        return components;
    }

    private String getParameterMeter(int value) {
        String meter = "";
        for (int i = 0; i < value; i++) {
            if (i % 10 == 0) {
                meter += " ";
            }
            meter += "|";
        }
        return meter;
    }

    public LoreGenerator setX(double x) {
        this.x = x;
        return this;
    }

    public LoreGenerator setY(double y) {
        this.y = y;
        return this;
    }

    public LoreGenerator setZ(double z) {
        this.z = z;
        return this;
    }

    public LoreGenerator setTaste(double taste) {
        this.taste = taste;
        return this;
    }

    public LoreGenerator setSmell(double smell) {
        this.smell = smell;
        return this;
    }

    public LoreGenerator setCompatibility(double compatibility) {
        this.compatibility = compatibility;
        return this;
    }

    public LoreGenerator setQuality(double quality) {
        this.quality = quality;
        return this;
    }

    public LoreGenerator setAlcLV(double alcLV) {
        this.alcLV = alcLV;
        return this;
    }

    public LoreGenerator setAlcAmount(double alcAmount) {
        this.alcAmount = alcAmount;
        return this;
    }

    public LoreGenerator setMaturationDays(int maturationDays) {
        this.maturationDays = maturationDays;
        return this;
    }

    public LoreGenerator setRarity(int rarity) {
        this.rarity = rarity;
        return this;
    }

    public LoreGenerator setSaturation(float saturation) {
        this.saturation = saturation;
        return this;
    }

    public LoreGenerator setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
        return this;
    }

    public LoreGenerator setParams(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public LoreGenerator setSubParams(double taste, double smell, double compatibility, double quality) {
        this.taste = taste;
        this.smell = smell;
        this.compatibility = compatibility;
        this.quality = quality;
        return this;
    }

    public LoreGenerator setAlcParams(double alcLV, double alcAmount) {
        this.alcLV = alcLV;
        this.alcAmount = alcAmount;
        return this;
    }

    public LoreGenerator addInfo(String... info) {
        lore.addAll(Arrays.asList(info));
        return this;
    }

    public LoreGenerator addImportantInfo(String... info) {
        importantLore.addAll(Arrays.asList(info));
        return this;
    }

    public LoreGenerator addParametersLore(String label, double value) {
        parametersLabel.add(label);
        parametersValue.add(value);
        return this;
    }
}

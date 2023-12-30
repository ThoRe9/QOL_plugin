package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.superItems.factory.adapter.AdapterID;
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
    private final ArrayList<AdapterID> adapterIDS = new ArrayList<>();
    private double xAddition;
    private double xAmplifier = 1;
    private double yAddition;
    private double yAmplifier = 1;
    private double zAddition;
    private double zAmplifier = 1;
    private double tasteAddition;
    private double tasteAmplifier = 1;
    private double smellAddition;
    private double smellAmplifier = 1;
    private double compatibilityAddition;
    private double compatibilityAmplifier = 1;
    private double amountAddition;
    private double amountAmplifier = 1;
    private double alcPercentAddition;
    private double alcPercentAmplifier = 1;

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
        if (xAddition != 0 || xAmplifier != 1 || yAddition != 0 || yAmplifier != 1 || zAddition != 0 || zAmplifier != 1 || tasteAddition != 0 || tasteAmplifier != 1 || smellAddition != 0 || smellAmplifier != 1 || compatibilityAddition != 0 || compatibilityAmplifier != 1 || amountAddition != 0 || amountAmplifier != 1 || alcPercentAddition != 0 || alcPercentAmplifier != 1) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Adapter").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
        }
        if (xAddition != 0) {
            components.add(Component.text("X").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(xAddition >= 0 ? "+" : "").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(xAddition).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (xAmplifier != 1) {
            components.add(Component.text("X").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(xAmplifier).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (yAddition != 0) {
            components.add(Component.text("Y").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(yAddition >= 0 ? "+" : "").color(bColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(yAddition).color(bColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (yAmplifier != 1) {
            components.add(Component.text("Y").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(bColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(yAmplifier).color(bColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (zAddition != 0) {
            components.add(Component.text("Z").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(zAddition >= 0 ? "+" : "").color(cColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(zAddition).color(cColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (zAmplifier != 1) {
            components.add(Component.text("Z").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(cColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(zAmplifier).color(cColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (tasteAddition != 0) {
            components.add(Component.text("Taste").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(tasteAddition >= 0 ? "+" : "").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(tasteAddition).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (tasteAmplifier != 1) {
            components.add(Component.text("Taste").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(tasteAmplifier).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (smellAddition != 0) {
            components.add(Component.text("Smell").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(smellAddition >= 0 ? "+" : "").color(bColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(smellAddition).color(bColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (smellAmplifier != 1) {
            components.add(Component.text("Smell").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(bColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(smellAmplifier).color(bColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (compatibilityAddition != 0) {
            components.add(Component.text("Compatibility").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(compatibilityAddition >= 0 ? "+" : "").color(cColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(compatibilityAddition).color(cColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (compatibilityAmplifier != 1) {
            components.add(Component.text("Compatibility").color(cColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(cColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(compatibilityAmplifier).color(cColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (amountAddition != 0) {
            components.add(Component.text("Amount").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(amountAddition >= 0 ? "+" : "").color(dColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(amountAddition).color(dColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (amountAmplifier != 1) {
            components.add(Component.text("Amount").color(aColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(dColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(amountAmplifier).color(dColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (alcPercentAddition != 0) {
            components.add(Component.text("Alcohol Percent").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(alcPercentAddition >= 0 ? "+" : "").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(alcPercentAddition).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (alcPercentAmplifier != 1) {
            components.add(Component.text("Alcohol Percent").color(bColor).decoration(TextDecoration.ITALIC, false).append(Component.text(" : ").color(baseColor).decoration(TextDecoration.ITALIC, false)).append(Component.text("×").color(aColor).decoration(TextDecoration.ITALIC, false)).append(Component.text(alcPercentAmplifier).color(aColor).decoration(TextDecoration.ITALIC, false)));
        }
        if (!adapterIDS.isEmpty()) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Adapter").color(subColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
            for (AdapterID adapterID : adapterIDS) {
                components.add(Component.text(adapterID.getName()).color(dColor).decoration(TextDecoration.ITALIC, false));
            }
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
            for (String s : lore) {
                components.add(Component.text(s).color(baseColor).decoration(TextDecoration.ITALIC, false));
            }
        }
        if (!importantLore.isEmpty()) {
            components.add(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false).append(Component.text("Important").color(importantColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD).append(Component.text("==========").color(baseColor).decoration(TextDecoration.ITALIC, false))));
            for (String s : importantLore) {
                components.add(Component.text(s).color(importantColor).decoration(TextDecoration.ITALIC, false));
            }
        }
        return components;
    }

    private String getParameterMeter(int value) {
        StringBuilder meter = new StringBuilder();
        for (int i = 0; i < value; i++) {
            if (i % 10 == 0) {
                meter.append(" ");
            }
            meter.append("|");
        }
        return meter.toString();
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

    public LoreGenerator setxAddition(double xAddition) {
        this.xAddition = xAddition;
        return this;
    }

    public LoreGenerator setxAmplifier(double xAmplifier) {
        this.xAmplifier = xAmplifier;
        return this;
    }

    public LoreGenerator setyAddition(double yAddition) {
        this.yAddition = yAddition;
        return this;
    }

    public LoreGenerator setyAmplifier(double yAmplifier) {
        this.yAmplifier = yAmplifier;
        return this;
    }

    public LoreGenerator setzAddition(double zAddition) {
        this.zAddition = zAddition;
        return this;
    }

    public LoreGenerator setzAmplifier(double zAmplifier) {
        this.zAmplifier = zAmplifier;
        return this;
    }

    public LoreGenerator setTasteAddition(double tasteAddition) {
        this.tasteAddition = tasteAddition;
        return this;
    }

    public LoreGenerator setTasteAmplifier(double tasteAmplifier) {
        this.tasteAmplifier = tasteAmplifier;
        return this;
    }

    public LoreGenerator setSmellAddition(double smellAddition) {
        this.smellAddition = smellAddition;
        return this;
    }

    public LoreGenerator setSmellAmplifier(double smellAmplifier) {
        this.smellAmplifier = smellAmplifier;
        return this;
    }

    public LoreGenerator setCompatibilityAddition(double compatibilityAddition) {
        this.compatibilityAddition = compatibilityAddition;
        return this;
    }

    public LoreGenerator setCompatibilityAmplifier(double compatibilityAmplifier) {
        this.compatibilityAmplifier = compatibilityAmplifier;
        return this;
    }

    public LoreGenerator setAmountAddition(double amountAddition) {
        this.amountAddition = amountAddition;
        return this;
    }

    public LoreGenerator setAmountAmplifier(double amountAmplifier) {
        this.amountAmplifier = amountAmplifier;
        return this;
    }

    public LoreGenerator setAlcPercentAddition(double alcPercentAddition) {
        this.alcPercentAddition = alcPercentAddition;
        return this;
    }

    public LoreGenerator setAlcPercentAmplifier(double alcPercentAmplifier) {
        this.alcPercentAmplifier = alcPercentAmplifier;
        return this;
    }

    public LoreGenerator addAdapterID(AdapterID adapterID) {
        this.adapterIDS.add(adapterID);
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

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
    private final ArrayList<String> info = new ArrayList<>();
    private final ArrayList<String> rarity = new ArrayList<>();
    private final ArrayList<String> parameters = new ArrayList<>();
    private final ArrayList<String> important = new ArrayList<>();
    private final ArrayList<NamedTextColor> parameterColors = new ArrayList<>(Arrays.asList(
            NamedTextColor.RED,
            NamedTextColor.YELLOW,
            NamedTextColor.GREEN,
            NamedTextColor.AQUA,
            NamedTextColor.BLUE,
            NamedTextColor.LIGHT_PURPLE,
            NamedTextColor.WHITE,
            NamedTextColor.GRAY,
            NamedTextColor.DARK_RED,
            NamedTextColor.GOLD,
            NamedTextColor.DARK_GREEN,
            NamedTextColor.DARK_AQUA,
            NamedTextColor.DARK_BLUE,
            NamedTextColor.DARK_PURPLE,
            NamedTextColor.DARK_GRAY
    ));

    private final ArrayList<Component> presetLore = new ArrayList<>();
    private final TextColor xColor = TextColor.color(112, 145, 173);
    private final TextColor yColor = TextColor.color(107, 87, 117);
    private final TextColor zColor = TextColor.color(232, 169, 133);
    private final TextColor tasteColor = TextColor.color(230, 216, 190);
    private final TextColor smellColor = TextColor.color(235, 228, 213);
    private final TextColor compatibilityColor = TextColor.color(171, 181, 194);
    private final TextColor amountColor = TextColor.color(154, 189, 173);
    private final TextColor alcLVColor = TextColor.color(230, 230, 194);
    private final TextColor qualityColor = TextColor.color(255, 255, 255);


    public ArrayList<Component> generateLore() {
        ArrayList<Component> lore = new ArrayList<>();
        // important, parameters, rarity, info の順に追加
        if (!this.important.isEmpty()) {
            for (String s : this.important) {
                lore.add(Component.text(s).color(NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true));
            }
        }
        if (!this.parameters.isEmpty()) {
            Iterator<NamedTextColor> colorIterator = parameterColors.iterator();
            for (String s : this.parameters) {
                if (!colorIterator.hasNext()) {
                    colorIterator = parameterColors.iterator();
                }
                lore.add(Component.text(s).color(colorIterator.next()));
            }
        }
        if (!this.presetLore.isEmpty()) {
            lore.addAll(this.presetLore);
        }
        if (!this.rarity.isEmpty()) {
            for (String s : this.rarity) {
                lore.add(Component.text(s).color(TextColor.color(243, 214, 147)).decoration(TextDecoration.ITALIC, true));
            }
        }
        if (!this.info.isEmpty()) {
            for (String s : this.info) {
                lore.add(Component.text(s).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, true));
            }
        }
        return lore;
    }
    public LoreGenerator addInfoLore(String lore) {
        this.info.add(lore);
        return this;
    }
    public LoreGenerator addRarityLore(int rarity) {
        this.rarity.add("Rarity: " + StringUtils.repeat('★', rarity));
        return this;
    }

    public LoreGenerator addParametersLore(String parameterName, double parameter) {
        this.parameters.add(parameterName + ": " + StringUtils.repeat('|', (int)(parameter * 100)));
        return this;
    }
    public LoreGenerator addParametersLore(String parameterName, double parameter, boolean isNumber) {
        if (isNumber) {
            this.parameters.add(parameterName + ": " + parameter);
        } else {
            this.parameters.add(parameterName + ": " + StringUtils.repeat('|', (int)(parameter * 10)));
        }
        return this;
    }

    public LoreGenerator addImportantLore(String lore) {
        this.important.add(lore);
        return this;
    }
    public LoreGenerator addFoodLevelLore(int foodLevel) {
        this.parameters.add("Food Level: " + StringUtils.repeat("🍈" , foodLevel/2) + StringUtils.repeat("🍉", foodLevel%2));
        return this;
    }
    public LoreGenerator addFoodSaturationLore(float foodSaturation) {
        this.parameters.add("Food Saturation: " + StringUtils.repeat('|', (int)(foodSaturation * 10)));
        return this;
    }

    public LoreGenerator setSuperItemLore(double x, double y, double z, double quarity, int rarity, String... args){
        this.addParametersLore("x", x);
        this.addParametersLore("y", y);
        this.addParametersLore("z", z);
        this.addParametersLore("quality", quarity);
        this.addRarityLore(rarity);
        for (String s : args) {
            this.addInfoLore(s);
        }
        return this;

    }

    public LoreGenerator setLiquorLore(double x, double y, double z, double taste, double smell, double compatibility, double amount, double alcLV, double quality, int rarity) {
        presetLore.clear();
        presetLore.add(Component.text("x: ").color(xColor).append(Component.text(StringUtils.repeat('|', (int) (x * 100))).color(xColor)));
        presetLore.add(Component.text("y: ").color(yColor).append(Component.text(StringUtils.repeat('|', (int) (y * 100))).color(yColor)));
        presetLore.add(Component.text("z: ").color(zColor).append(Component.text(StringUtils.repeat('|', (int) (z * 100))).color(zColor)));
        presetLore.add(Component.text("Taste: ").color(tasteColor).append(Component.text(StringUtils.repeat('|', (int) (taste * 100))).color(tasteColor)));
        presetLore.add(Component.text("Smell: ").color(smellColor).append(Component.text(StringUtils.repeat('|', (int) (smell * 100))).color(smellColor)));
        presetLore.add(Component.text("Compatibility: ").color(compatibilityColor).append(Component.text(StringUtils.repeat('|', (int) (compatibility * 100))).color(compatibilityColor)));
        presetLore.add(Component.text("Amount: ").color(amountColor).append(Component.text((int) amount).color(amountColor)).append(Component.text("ml").color(amountColor)));
        presetLore.add(Component.text("Alcohol Level: ").color(alcLVColor).append(Component.text((int) (alcLV * 100)).color(alcLVColor)).append(Component.text("%").color(alcLVColor)));
        presetLore.add(Component.text("Quality: ").color(qualityColor).append(Component.text(StringUtils.repeat('|', (int) (quality * 100))).color(qualityColor)));
        this.addRarityLore(rarity);
        return this;
    }

    public LoreGenerator setIngredientLore(double x, double y, double z, double quality, int rarity) {
        presetLore.clear();
        presetLore.add(Component.text("x: ").color(xColor).append(Component.text(StringUtils.repeat('|', (int) (x * 100))).color(xColor)));
        presetLore.add(Component.text("y: ").color(yColor).append(Component.text(StringUtils.repeat('|', (int) (y * 100))).color(yColor)));
        presetLore.add(Component.text("z: ").color(zColor).append(Component.text(StringUtils.repeat('|', (int) (z * 100))).color(zColor)));
        presetLore.add(Component.text("Quality: ").color(qualityColor).append(Component.text(StringUtils.repeat('|', (int) (quality * 100))).color(qualityColor)));
        this.addRarityLore(rarity);
        return this;
    }

    public LoreGenerator setIngredientLore(double x, double u, double z, double taste, double smell, double compatibility, double quality, int rarity) {
        presetLore.clear();
        presetLore.add(Component.text("x: ").color(xColor).append(Component.text(StringUtils.repeat('|', (int) (x * 100))).color(xColor)));
        presetLore.add(Component.text("u: ").color(yColor).append(Component.text(StringUtils.repeat('|', (int) (u * 100))).color(yColor)));
        presetLore.add(Component.text("z: ").color(zColor).append(Component.text(StringUtils.repeat('|', (int) (z * 100))).color(zColor)));
        presetLore.add(Component.text("Taste: ").color(tasteColor).append(Component.text(StringUtils.repeat('|', (int) (taste * 100))).color(tasteColor)));
        presetLore.add(Component.text("Smell: ").color(smellColor).append(Component.text(StringUtils.repeat('|', (int) (smell * 100))).color(smellColor)));
        presetLore.add(Component.text("Compatibility: ").color(compatibilityColor).append(Component.text(StringUtils.repeat('|', (int) (compatibility * 100))).color(compatibilityColor)));
        presetLore.add(Component.text("Quality: ").color(qualityColor).append(Component.text(StringUtils.repeat('|', (int) (quality * 100))).color(qualityColor)));
        this.addRarityLore(rarity);
        return this;
    }
}

package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.util.StringUtil;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class LoreGenerator {
    private ArrayList<String> info = new ArrayList<>();
    private ArrayList<String> rarity = new ArrayList<>();
    private ArrayList<String> parameters = new ArrayList<>();
    private ArrayList<String> important = new ArrayList<>();
    private final ArrayList<NamedTextColor> parameterColors = new ArrayList<>(Arrays.asList(
            NamedTextColor.RED,
            NamedTextColor.YELLOW,
            NamedTextColor.GREEN,
            NamedTextColor.AQUA,
            NamedTextColor.BLUE,
            NamedTextColor.LIGHT_PURPLE
    ));


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
        if (!this.rarity.isEmpty()) {
            for (String s : this.rarity) {
                lore.add(Component.text(s).color(NamedTextColor.GOLD));
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
        this.parameters.add(parameterName + ": " + StringUtils.repeat('|', (int)(parameter * 10)));
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
}

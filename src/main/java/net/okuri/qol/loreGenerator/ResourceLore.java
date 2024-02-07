package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.alcohol.taste.Taste;

import java.util.HashMap;
import java.util.Map;

public class ResourceLore extends Lore {
    /**
     * Resource用のLoreを生成するためのベースとなるクラスです。
     */
    Map<Taste, Double> tasteParams = new HashMap<>();

    public ResourceLore() {
        super(0, "Resource", NamedTextColor.GREEN);
    }

    @Override
    public void generateLore() {
        addLore(title);
        for (Map.Entry<Taste, Double> entry : tasteParams.entrySet()) {
            addParamBarLore(entry.getKey().getDisplayName(), entry.getKey().getColor(), entry.getValue());
        }
    }

    public ResourceLore addTaste(Taste taste, double param) {
        tasteParams.put(taste, param);
        return this;
    }
}
